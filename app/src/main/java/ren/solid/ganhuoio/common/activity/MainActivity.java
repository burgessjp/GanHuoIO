package ren.solid.ganhuoio.common.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import cn.bmob.v3.update.BmobUpdateAgent;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.module.home.fragment.HomeFragment;
import ren.solid.ganhuoio.module.mine.MineFragment;
import ren.solid.ganhuoio.module.read.ReadingFragment;
import ren.solid.library.activity.base.BaseMainActivity;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.ViewUtils;

public class MainActivity extends BaseMainActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

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
        mBottomNavigationView = $(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        switchFragment(0);
                        break;
                    case R.id.item_reading:
                        switchFragment(1);
                        break;
                    case R.id.item_collect:
                        switchFragment(2);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
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
                to = ViewUtils.createFragment(ReadingFragment.class);
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
        if (mCurrentFragment instanceof ReadingFragment) {
            ReadingFragment fragment = (ReadingFragment) mCurrentFragment;
            if (fragment.canGoBack()) {
                fragment.goBack();
                return false;
            }
        }
        return true;
    }
}
