package ren.solid.ganhuoio.utils;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import cn.bmob.v3.listener.SaveListener;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.event.CollectChangeEvent;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.library.rx.RxBus;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:10:02
 */
public class DialogUtils {

    public static void showActionPopWindow(final Context context, final View view, final CollectTable bean) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater()
                .inflate(R.menu.menu_action, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_collect:
                        doCollect(bean, context, view);
                        break;
                    case R.id.item_share_sina:
                        SinaWeiBoShareUtil share = new SinaWeiBoShareUtil(context);
                        share.setTextObj(bean.getDesc() + "\n" + "【干货IO下载地址:\n" +
                                "http://android.myapp.com/myapp/detail.htm?apkName=ren.solid.ganhuoio】");
                        share.setWebpageObj("来自干货IO的分享", bean.getUrl(), bean.getDesc());
                        share.sendMultiMessage();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private static void doCollect(CollectTable bean, Context context, final View view) {
        if (AuthorityUtils.isLogin()) {
            bean.setUsername(AuthorityUtils.getUserName());
            bean.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    SnackBarUtils.makeShort(view, "收藏成功").info();
                    RxBus.getInstance().post(new CollectChangeEvent());
                }

                @Override
                public void onFailure(int i, String s) {
                    if (i == 401) {
                        SnackBarUtils.makeShort(view, "你已经收藏过了").info();
                    } else {
                        SnackBarUtils.makeShort(view, "收藏失败").info();
                    }
                }
            });
        } else {
            SnackBarUtils.makeShort(view, context.getResources().getString(R.string.no_login)).warning();
        }
    }
}
