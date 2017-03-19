package ren.solid.ganhuoio.common.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.bmob.v3.update.BmobUpdateAgent;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.module.home.fragment.HomeFragment;
import ren.solid.ganhuoio.module.mine.MineFragment;
import ren.solid.ganhuoio.module.read.ReadingTabFragment;
import ren.solid.ganhuoio.module.search.SearchActivity;
import ren.solid.library.activity.base.BaseMainActivity;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.ViewUtils;

public class MainActivity extends BaseMainActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private AppBarLayout mAppBarLayout;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        BmobUpdateAgent.update(this);
        mFragmentManager = getSupportFragmentManager();
        switchFragment(0);
    }

    @Override
    protected void setUpView() {
        mAppBarLayout = $(R.id.appbar_layout);
        mBottomNavigationView = $(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        showAppBar();
                        switchFragment(0);
                        break;
                    case R.id.item_reading:
                        hideAppBar();
                        switchFragment(1);
                        break;
                    case R.id.item_collect:
                        hideAppBar();
                        switchFragment(2);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        ViewCompat.setElevation(mAppBarLayout, ViewUtils.dp2px(this, 4));

        $(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(MainActivity.this);
            }
        });
    }

    private void hideAppBar() {
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        if (layoutParams.height == 0) return;
        layoutParams.height = 0;
        mAppBarLayout.setLayoutParams(layoutParams);
    }

    private void showAppBar() {
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        if (layoutParams.height != 0) return;
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.app_bar_height);
        mAppBarLayout.setLayoutParams(layoutParams);
    }

    @Override
    protected void setUpData() {
    }

    private void switchFragment(int index) {
        Fragment to = mFragmentManager.findFragmentByTag(index + "");
        if (to == null) {
            if (index == 0)
                to = ViewUtils.createFragment(HomeFragment.class);
            else if (index == 1)
                to = ViewUtils.createFragment(ReadingTabFragment.class);
            else if (index == 2)
                to = ViewUtils.createFragment(MineFragment.class);
            else
                to = ViewUtils.createFragment(HomeFragment.class);
        }
        if (to == mCurrentFragment && mCurrentFragment instanceof BaseFragment) {
            ((BaseFragment) mCurrentFragment).refresh();
        } else if (to.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commit();
        } else {
            to.setUserVisibleHint(true);
            if (mCurrentFragment != null)
                mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to, index + "").commit();
            else
                mFragmentManager.beginTransaction().add(R.id.frame_content, to, index + "").commit();
        }
        mCurrentFragment = to;
    }

    @Override
    protected boolean beforeOnBackPressed() {
        if (mCurrentFragment instanceof AbsListFragment) {
            AbsListFragment listFragment = (AbsListFragment) mCurrentFragment;
            return listFragment.scrollToTop();
        } else if (mCurrentFragment instanceof ReadingTabFragment) {
            ReadingTabFragment readingTabFragment = (ReadingTabFragment) mCurrentFragment;
            return readingTabFragment.scrollToTop();
        }

        return true;
    }
}
