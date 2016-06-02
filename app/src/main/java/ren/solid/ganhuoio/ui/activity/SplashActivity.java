package ren.solid.ganhuoio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import cn.bmob.v3.update.BmobUpdateAgent;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.ui.fragment.StartFragment;
import ren.solid.ganhuoio.utils.AppUtils;
import ren.solid.ganhuoio.utils.AuthorityUtils;

/**
 * Created by _SOLID
 * Date:2016/5/20
 * Time:7:58
 */
public class SplashActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        Handler handler = new Handler();
        if (!AppUtils.isFirstRun()) {
            addSlide(new StartFragment());
            showSkipButton(false);
            setProgressButtonEnabled(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);

        } else {

            int color = getResources().getColor(R.color.colorPrimary);

            addSlide(AppIntroFragment.newInstance("最新", "能及时查看gank.io发布的最新信息", R.drawable.display1, color));
            addSlide(AppIntroFragment.newInstance("分类浏览", "能对信息进行分类浏览", R.drawable.display2, color));
            addSlide(AppIntroFragment.newInstance("排序", "可以对分类进行排序", R.drawable.display3, color));
            addSlide(AppIntroFragment.newInstance("在线收藏", "使用Bomb实现在线收藏", R.drawable.display4, color));
            //addSlide(AppIntroFragment.newInstance("", "干货IO", R.mipmap.logo, R.color.colorPrimary));
            setBarColor(color);
            // Hide Skip/Done button.
            //showSkipButton(false);
            //setProgressButtonEnabled(false);

            // Turn vibration on and set intensity.
            // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
//            setVibrate(true);
//            setVibrateIntensity(30);

            //setFadeAnimation();
            setZoomAnimation();
            setDoneText("立即开启");
            setSkipText("跳过");
        }

    }

    @Override
    public void onSkipPressed() {
        checkLogin();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        checkLogin();
    }

    @Override
    public void onSlideChanged() {

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
