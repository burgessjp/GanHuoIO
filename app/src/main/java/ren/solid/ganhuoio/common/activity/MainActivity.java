package ren.solid.ganhuoio.common.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import cn.bmob.v3.update.BmobUpdateAgent;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.module.home.fragment.HomeFragment;
import ren.solid.ganhuoio.module.mine.CollectListFragment;
import ren.solid.ganhuoio.module.read.ReadingFragment;
import ren.solid.ganhuoio.utils.ShakePictureUtils;
import ren.solid.library.activity.base.BaseMainActivity;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.ViewUtils;

public class MainActivity extends BaseMainActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;

    //摇一摇相关
    private ShakePictureUtils mShakePictureUtils;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        BmobUpdateAgent.update(this);
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = (BaseFragment) mFragmentManager.findFragmentById(R.id.frame_content);
        if (mCurrentFragment == null) {
            mCurrentFragment = ViewUtils.createFragment(HomeFragment.class);
            mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        }
        //初始化摇一摇
        mShakePictureUtils = new ShakePictureUtils(this);
    }

    @Override
    protected void setUpView() {
        mBottomNavigationView = $(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class<?> clazz = null;
                switch (item.getItemId()) {
                    case R.id.item_home:
                        clazz = HomeFragment.class;
                        break;
                    case R.id.item_collect:
                        clazz = CollectListFragment.class;
                        break;
                    case R.id.item_reading:
                        clazz = ReadingFragment.class;
                        break;
                    default:
                        break;
                }
                if (clazz != null) {
                    switchFragment(clazz);
                }
                return false;
            }
        });

    }

    @Override
    protected void setUpData() {
    }


    private void switchFragment(Class<?> clazz) {
        if (clazz == null) return;
        BaseFragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commit();
        } else {
            to.setUserVisibleHint(true);
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commit();
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

    @Override
    protected void onResume() {
        super.onResume();
        mShakePictureUtils.registerSensor();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mShakePictureUtils.unRegisterSensor();
    }
}
