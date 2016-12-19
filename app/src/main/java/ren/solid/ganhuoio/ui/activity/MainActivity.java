package ren.solid.ganhuoio.ui.activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.List;

import cn.bmob.v3.update.BmobUpdateAgent;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.event.LoginEvent;
import ren.solid.ganhuoio.ui.fragment.CategoryFragment;
import ren.solid.ganhuoio.ui.fragment.CollectListFragment;
import ren.solid.ganhuoio.ui.fragment.MeizhiFragmant;
import ren.solid.ganhuoio.ui.fragment.ReadingFragment;
import ren.solid.ganhuoio.ui.fragment.RecentlyFragment;
import ren.solid.ganhuoio.utils.AppUtils;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.ganhuoio.utils.ShakePictureUtils;
import ren.solid.library.SettingCenter;
import ren.solid.library.activity.base.BaseMainActivity;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.fragment.base.LazyLoadFragment;
import ren.solid.library.rx.RxBus;
import ren.solid.library.utils.SnackBarUtils;
import ren.solid.library.utils.SystemShareUtils;
import ren.solid.library.utils.ViewUtils;
import rx.functions.Action1;

public class MainActivity extends BaseMainActivity {

    private Toolbar mToolbar;
    private Drawer mDrawer;
    private BottomNavigationView mBottomNavigationView;

    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private MenuItem mSortMenu;

    private ProfileDrawerItem mProfileDrawerItem;
    private AccountHeader mProfileHeader;

    //摇一摇相关
    private ShakePictureUtils mShakePictureUtils;
    private PrimaryDrawerItem mItemCache;

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
            mCurrentFragment = ViewUtils.createFragment(RecentlyFragment.class);
            mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        }
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        if (null != savedInstanceState) {
            List<Fragment> fragments = mFragmentManager.getFragments();
            for (int i = 0; i < fragments.size(); i++) {
                trans.hide(fragments.get(i));
            }
        }
        trans.show(mCurrentFragment).commitAllowingStateLoss();

        //初始化摇一摇
        mShakePictureUtils = new ShakePictureUtils(this);
        RxBus.getInstance().toObserverable(LoginEvent.class).subscribe(new Action1<LoginEvent>() {
            @Override
            public void call(LoginEvent loginEvent) {
                updateProfile();
            }
        });
    }

    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar);
        mBottomNavigationView = $(R.id.bottom_navigation);
        setSupportActionBar(mToolbar);
        setUpDrawer();

        mToolbar.setTitle(getResources().getString(R.string.main_home));

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mSortMenu.setVisible(false);
                Class<?> clazz = null;
                switch (item.getItemId()) {
                    case R.id.item_home:
                        mToolbar.setTitle(getResources().getString(R.string.main_home));
                        clazz = RecentlyFragment.class;
                        break;
                    case R.id.item_category:
                        mSortMenu.setVisible(true);
                        mToolbar.setTitle(getResources().getString(R.string.main_category));
                        clazz = CategoryFragment.class;
                        break;
                    case R.id.item_meizhi:
                        mSortMenu.setVisible(false);
                        mToolbar.setTitle(getResources().getString(R.string.main_meizhi));
                        clazz = MeizhiFragmant.class;
                        break;
                    case R.id.item_collect:
                        mToolbar.setTitle(getResources().getString(R.string.main_collect));
                        clazz = CollectListFragment.class;
                        break;
                    case R.id.item_reading:
                        mToolbar.setTitle(getResources().getString(R.string.main_reading));
                        clazz = ReadingFragment.class;
                        break;
//                    case  R.id.item_rx:
//                        mToolbar.setTitle(getResources().getString(R.string.main_rx_search));
//                        clazz = RxOperatorsSearchFragment.class;
//                        break;
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

    private void setUpDrawer() {

        mProfileDrawerItem = new ProfileDrawerItem().withName("点我登陆");
        checkUseInfo();
        mProfileHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.colorAccent)))
                .addProfiles(
                        mProfileDrawerItem
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        if (!AuthorityUtils.isLogin()) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        return false;
                    }
                })
                .build();

        SwitchDrawerItem itemSwitch = new SwitchDrawerItem().withName(getResources().getString(R.string.main_only_wifi)).withIcon(GoogleMaterial.Icon.gmd_network_wifi).withChecked(SettingCenter.getOnlyWifiLoadImage()).withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                SettingCenter.setOnlyWifiLoadImage(isChecked);
            }
        }).withSelectable(false);

        SwitchDrawerItem itemShake = new SwitchDrawerItem().withName(getResources().getString(R.string.main_shake_picture)).withIcon(GoogleMaterial.Icon.gmd_vibration).withChecked(AppUtils.shakePicture()).withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                AppUtils.setShakePicture(isChecked);
            }
        }).withSelectable(false);


        PrimaryDrawerItem itemShare = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.main_share))
                .withIcon(GoogleMaterial.Icon.gmd_share)
                .withSelectable(false);
        PrimaryDrawerItem itemFeedback = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.main_feedback))
                .withIcon(GoogleMaterial.Icon.gmd_coffee)
                .withSelectable(false);
        PrimaryDrawerItem itemExit = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.main_exit))
                .withIcon(GoogleMaterial.Icon.gmd_grid_off)
                .withSelectable(false);
        mItemCache = new PrimaryDrawerItem()
                .withName(getResources()
                        .getString(R.string.main_cache_clear))
                .withIcon(GoogleMaterial.Icon.gmd_adb)
                .withBadgeStyle(new BadgeStyle()
                        .withTextColorRes(R.color.item_desc)
                        .withColorRes(R.color.colorAccent))
                .withSelectable(false);
        SettingCenter.countDirSizeTask(new SettingCenter.CountDirSizeListener() {
            @Override
            public void onResult(long result) {
                mItemCache.withBadge(SettingCenter.formatFileSize(result));
            }
        });
        mDrawer = new DrawerBuilder()
                .withAccountHeader(mProfileHeader)
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        itemSwitch,
                        itemShake,
                        itemShare,
                        itemFeedback,
                        mItemCache,
                        itemExit
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        setDrawerItemClickListener(position);
                        return true;
                    }
                })
                .build();

    }

    private void setDrawerItemClickListener(int position) {
        switch (position) {
            case 3:
                SystemShareUtils.shareText(MainActivity.this, getResources().getString(R.string.app_share));
                break;
            case 4:
                AppUtils.feedBack(this, mToolbar);
                break;
            case 6:
                AppUtils.logOut(this);
                break;
            case 5:
                AppUtils.clearCache(this, new SettingCenter.ClearCacheListener() {
                    @Override
                    public void onResult() {
                        mDrawer.updateItem(mItemCache.withBadge(SettingCenter.formatFileSize(0)));
                        SnackBarUtils.makeShort(mToolbar, "清理成功").success();
                    }
                });
                break;
            default:

                break;
        }
        mDrawer.closeDrawer();
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

        if (mCurrentFragment instanceof CategoryFragment) {
            mSortMenu.setVisible(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSortMenu = menu.findItem(R.id.action_sort);
        mSortMenu.setVisible(false);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("请输入搜索关键词");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResultActivity.openActivity(MainActivity.this, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            startActivityWithoutExtras(SortActivity.class);
        } else if (id == R.id.action_about) {
            startActivityWithoutExtras(AboutActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }


    public void checkUseInfo() {
        if (AuthorityUtils.isLogin()) {
            mProfileDrawerItem.withName(AuthorityUtils.getUserName());
            mProfileDrawerItem.withEmail(AuthorityUtils.getDescription());
            mProfileDrawerItem.withIcon(AuthorityUtils.getAvatar());

        }
    }

    public void updateProfile() {
        checkUseInfo();
        mProfileHeader.updateProfile(mProfileDrawerItem);
    }

    @Override
    protected boolean beforeOnBackPressed() {
        if (mDrawer.isDrawerOpen()) {//当前抽屉是打开的，则关闭
            mDrawer.closeDrawer();
        }
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
