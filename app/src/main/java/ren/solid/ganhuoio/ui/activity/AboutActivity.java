package ren.solid.ganhuoio.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ren.solid.ganhuoio.R;
import ren.solid.library.activity.base.BaseActivity;
import ren.solid.library.utils.SystemUtils;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/5/5
 * Time:10:30
 */
public class AboutActivity extends BaseActivity {

    private static String TAG = "AboutActivity";
    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;


    private ImageView mIvPlaceholder; // 大图片

    private LinearLayout mLlTitleContainer; // Title的LinearLayout

    private FrameLayout mFlTitleContainer; // Title的FrameLayout

    private AppBarLayout mAblAppBar; // 整个可以滑动的AppBar

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private TextView mTvToolbarTitle; // 标题栏Title

    private TextView mTvMsg;

    private Toolbar mToolbar; // 工具栏
    private TextView tv_version;

    @Override
    protected void setUpView() {

        mToolbar = $(R.id.toolbar);
        mAblAppBar = $(R.id.app_bar);
        mIvPlaceholder = $(R.id.iv_placeholder);
        mFlTitleContainer = $(R.id.fl_title_container);
        mLlTitleContainer = $(R.id.ll_title_container);
        mTvMsg = $(R.id.tv_msg);
        mTvToolbarTitle = $(R.id.tv_toolbar_title);
        mCollapsingToolbarLayout = $(R.id.collapsing_toolbar_layout);
        tv_version = $(R.id.tv_version);
        tv_version.setText("version:" + SystemUtils.getAppVersion(this));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvMsg.setAutoLinkMask(Linkify.ALL);
        mTvMsg.setMovementMethod(LinkMovementMethod
                .getInstance());


        // AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                if (percentage >= 0.5f) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeButtonEnabled(true);
                    ViewCompat.setElevation(mToolbar, ViewUtils.dp2px(AboutActivity.this, 2));
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                    ViewCompat.setElevation(mToolbar, 0);
                }
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });

        initParallaxValues(); // 自动滑动效果
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about;
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mIvPlaceholder.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mIvPlaceholder.setLayoutParams(petDetailsLp);
        mFlTitleContainer.setLayoutParams(petBackgroundLp);
    }

    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
