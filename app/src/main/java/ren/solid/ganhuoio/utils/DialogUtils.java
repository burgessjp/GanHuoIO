package ren.solid.ganhuoio.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.bomb.CollectTable;
import ren.solid.ganhuoio.common.event.CollectChangeEvent;
import ren.solid.ganhuoio.module.mine.LoginActivity;
import ren.solid.library.rx.RxBus;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:10:02
 */
public class DialogUtils {

    public static void showActionDialog(final Context context, final View itemView, final CollectTable bean) {
        new MaterialDialog.Builder(context)
                .items(R.array.action)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        CharSequence[] action = context.getResources().getTextArray(R.array.action);
                        if (text.equals(action[0])) {
                            SinaWeiBoShareUtil share = new SinaWeiBoShareUtil(context);
                            share.setTextObj(bean.getDesc() + "\n" + context.getString(R.string.app_download_url));
                            share.setWebpageObj("来自干货IO的分享", bean.getUrl(), bean.getDesc());
                            share.sendMultiMessage();
                        } else if (text.equals(action[1])) {
                            doCollect(bean, context, itemView);
                        }
                    }
                })
                .show();
    }

    private static void doCollect(CollectTable bean, final Context context, final View view) {
        if (AuthorityUtils.isLogin()) {
            bean.setUsername(AuthorityUtils.getUserName());
            bean.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    SnackBarUtils.makeShort(view, "收藏成功").success();
                    RxBus.getInstance().send(new CollectChangeEvent());
                }

                @Override
                public void onFailure(int i, String s) {
                    if (i == 401) {
                        SnackBarUtils.makeShort(view, "你已经收藏过了").info();
                    } else {
                        SnackBarUtils.makeShort(view, "收藏失败").danger();
                    }
                }
            });
        } else {
            SnackBarUtils.makeLong(view, context.getResources().getString(R.string.mine_no_login))
                    .warning(context.getString(R.string.mine_click_login)
                            , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                }
                            });
        }
    }

    public static void showUnDoCollectDialog(final View itemView
            , final CollectTable bean, final DeleteListener listener) {
        new MaterialDialog.Builder(itemView.getContext())
                .items(R.array.deleteCollect)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        unDoCollect(bean, itemView, listener);
                    }
                })
                .show();

    }

    private static void unDoCollect(CollectTable bean, final View view
            , final DeleteListener listener) {
        bean.delete(view.getContext(), listener);
    }
}
