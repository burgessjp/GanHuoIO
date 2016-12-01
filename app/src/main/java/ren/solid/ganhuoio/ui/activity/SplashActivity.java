package ren.solid.ganhuoio.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.utils.AppUtils;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.library.activity.base.BaseActivity;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/5/20
 * Time:7:58
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {
//        ObjectAnimator animatorX = ObjectAnimator.ofFloat($(R.id.iv_logo), "scaleX", 1.0f, 1.5f);
//        ObjectAnimator animatorY = ObjectAnimator.ofFloat($(R.id.iv_logo), "scaleY", 1.0f, 1.5f);
//        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat($(R.id.iv_logo), "alpha", 0.1f, 1);
//        ObjectAnimator animatorTv = ObjectAnimator.ofFloat($(R.id.tv_appname), "translationY", 0, ViewUtils.dp2px(this, 30));
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animatorX, animatorY, animatorAlpha, animatorTv);
//        animatorSet.setDuration(2000).start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    private void checkLogin() {
        if (!AuthorityUtils.isLogin()) {
            showTipsDialog();
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private void showTipsDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("提示")
                .content("检测到你还未登录过本应用，为确保一些功能的正常使用，建议登录")
                .positiveText("现在去登录").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }).negativeText("以后再说吧").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                        AppUtils.setFirstRun(false);
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();
    }
}
