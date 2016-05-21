package ren.solid.ganhuoio.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cn.bmob.v3.listener.SaveListener;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.rx.RxBus;
import ren.solid.library.utils.SnackBarUtils;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:10:02
 */
public class DialogUtils {

    public static void showActionPopWindow(final Context context, final View view, final CollectTable bean) {

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        View popupView = LayoutInflater.from(context).inflate(R.layout.dialog_action, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(ViewUtils.dp2px(context, 5));
        drawable.setColor(0xe0000000);
        popupWindow.setBackgroundDrawable(drawable);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        int height = popupView.getMeasuredHeight();
        int width = popupView.getMeasuredWidth();
        popupView.findViewById(R.id.tv_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCollect(bean, context, view, popupWindow);
                popupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.tv_offline_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.makeShort(view, "离线下载").info();
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - width, location[1] - height);
    }

    private static void doCollect(CollectTable bean, Context context, final View view, PopupWindow popupWindow) {
        if (AuthorityUtils.isLogin()) {
            bean.setUsername(AuthorityUtils.getUserName());
            bean.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    SnackBarUtils.makeShort(view, "收藏成功").info();
                    RxBus.getInstance().post("CollectChange");
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
