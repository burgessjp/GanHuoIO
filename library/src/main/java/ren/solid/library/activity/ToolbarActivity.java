package ren.solid.library.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import ren.solid.library.R;
import ren.solid.library.activity.base.BaseActivity;


/**
 * Created by _SOLID
 * Date:2016/4/22
 * Time:13:30
 * <p/>
 * ToolbarActivity
 */
public abstract class ToolbarActivity extends BaseActivity {

    protected AppBarLayout mAppBarLayout;
    protected TextSwitcher mTextSwitcher;
    protected FragmentManager mFragmentManager;

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void setUpView() {
        mAppBarLayout = $(R.id.appbar_layout);
        setUpToolBar();
        if (!isHaveTitle()) {
            mAppBarLayout.setVisibility(View.GONE);
        }
        setTitle(getToolbarTitle());

    }

    private void setUpToolBar() {
        Toolbar mToolbar = $(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTextSwitcher = $(R.id.textSwitcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressWarnings("deprecation")
            @Override
            public View makeView() {
                Context context = ToolbarActivity.this;
                TextView textView = new TextView(context);
                textView.setTextAppearance(context, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
    }

    protected abstract String getToolbarTitle();

    @Override
    protected void setUpData() {
        Fragment fragment = getFragment();
        if (fragment != null)
            mFragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_toolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
        mTextSwitcher.setSelected(true);
    }

    protected abstract Fragment getFragment();

    protected boolean isHaveTitle() {
        return true;
    }
}
