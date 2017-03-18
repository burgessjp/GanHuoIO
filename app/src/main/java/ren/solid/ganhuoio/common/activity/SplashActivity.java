package ren.solid.ganhuoio.common.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import ren.solid.ganhuoio.R;
import ren.solid.library.activity.base.BaseActivity;

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
        ObjectAnimator rotate = ObjectAnimator.ofFloat($(R.id.iv_logo), "rotationY", 180, 360);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotate);
        animatorSet.setDuration(2000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
