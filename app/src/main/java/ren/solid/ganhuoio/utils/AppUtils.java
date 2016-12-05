package ren.solid.ganhuoio.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import cn.bmob.v3.listener.SaveListener;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.bomb.FeedBack;
import ren.solid.library.SettingCenter;
import ren.solid.library.SolidApplication;
import ren.solid.library.utils.PrefUtils;
import ren.solid.library.utils.SnackBarUtils;
import ren.solid.library.utils.SystemUtils;

/**
 * Created by _SOLID
 * Date:2016/5/20
 * Time:8:42
 */
public class AppUtils {

    public static boolean isFirstRun() {
        return PrefUtils.getBoolean(GanHuoIOApplication.getInstance(), "isFirstRun", true);
    }

    public static void setFirstRun(boolean isFirstRun) {
        PrefUtils.putBoolean(GanHuoIOApplication.getInstance(), "isFirstRun", isFirstRun);
    }

    public static boolean shakePicture() {
        return PrefUtils.getBoolean(SolidApplication.getInstance(), "shakePicture", true);
    }

    public static void setShakePicture(boolean isEnable) {
        PrefUtils.putBoolean(SolidApplication.getInstance(), "shakePicture", isEnable);
    }


    public static void feedBack(final Context context, final View view) {
        new MaterialDialog.Builder(context)
                .title(R.string.feedback_dialog_title)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.feedback_input_hint, R.string.feedback_input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                        if (TextUtils.isEmpty(input)) {
                            return;
                        }
                        FeedBack feedBack = new FeedBack();
                        feedBack.setContent(input.toString());
                        feedBack.setAppVersion(SystemUtils.getAppVersion(context));
                        feedBack.setDeviceName(SystemUtils.getDeviceName());
                        feedBack.setSystemVersion(SystemUtils.getSystemVersion());
                        feedBack.setUsername(AuthorityUtils.getUserName());
                        feedBack.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                SnackBarUtils.makeShort(view, context.getResources().getString(R.string.feedback_success)).success();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                SnackBarUtils.makeShort(view, context.getResources().getString(R.string.feedback_failed)).danger();
                            }
                        });
                    }
                }).show();
    }

    public static void logOut(final Activity activity) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                .title("提示")
                .content("确定注销吗？")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        AuthorityUtils.logout();
                        activity.finish();
                    }
                }).negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public static void clearCache(final Activity activity, final SettingCenter.ClearCacheListener listener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                .title("温馨提示")
                .content("确定清空缓存吗？如果你使用的是移动网络不建议清空缓存哦")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        SettingCenter.clearAppCache(listener);
                    }
                }).negativeText("取消");

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

//    public static int getResourseIDByUrl(String url) {
//        int resId = R.drawable.web;
//        if (url.contains("csdn.net")) {
//            resId = R.drawable.csdn;
//        } else if (url.contains("github.com")) {
//            resId = R.drawable.github;
//        } else if (url.contains("jianshu.com")) {
//            resId = R.drawable.jianshu;
//        } else if (url.contains("miaopai.com")) {
//            resId = R.drawable.miaopai;
//        } else if (url.contains("acfun.tv")) {
//            resId = R.drawable.acfun;
//        } else if (url.contains("bilibili.com")) {
//            resId = R.drawable.bilibili;
//        } else if (url.contains("youku.com")) {
//            resId = R.drawable.youku;
//        } else if (url.contains("weibo.com")) {
//            resId = R.drawable.weibo;
//        } else if (url.contains("weixin.qq")) {
//            resId = R.drawable.weixin;
//        }
//
//
//        return resId;
//    }
}
