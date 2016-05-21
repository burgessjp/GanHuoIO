package ren.solid.ganhuoio.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.presenter.IRecentlyPresenter;
import ren.solid.ganhuoio.presenter.impl.RecentlyPresenterImpl;
import ren.solid.ganhuoio.ui.activity.MainActivity;
import ren.solid.ganhuoio.ui.adapter.GanHuoRecentlyPageAdapter;
import ren.solid.ganhuoio.ui.view.IRecentlyView;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.Logger;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:23
 */
public class RecentlyFragment extends BaseFragment implements IRecentlyView {

    private IRecentlyPresenter mPresenter;
    private GanHuoRecentlyPageAdapter mPageAdapter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private LinearLayout mLLLoadingWarp;
    private Button mBtnReload;
    private LinearLayout mLLReloadWarp;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_recently;
    }

    @Override
    protected void setUpView() {
        mTabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.viewpager);
        mLLReloadWarp = $(ren.solid.library.R.id.ll_reload_wrap);
        mLLLoadingWarp = $(ren.solid.library.R.id.ll_loading);
        mBtnReload = $(ren.solid.library.R.id.btn_reload);

        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getRecentlyDate();
            }
        });
    }

    @Override
    protected void setUpData() {
        mPresenter = new RecentlyPresenterImpl(this);
        mPresenter.getRecentlyDate();
    }

    @Override
    public void setDateList(List<String> dateList) {
        mPageAdapter = new GanHuoRecentlyPageAdapter(getChildFragmentManager(), dateList);
        mViewPager.setAdapter(mPageAdapter);
        for (int i = 0; i < dateList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showLoading() {
        mLLReloadWarp.setVisibility(View.GONE);
        mLLLoadingWarp.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        mLLReloadWarp.setVisibility(View.GONE);
        mLLLoadingWarp.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errMsg) {
        mLLReloadWarp.setVisibility(View.VISIBLE);
        mLLLoadingWarp.setVisibility(View.GONE);

        SnackBarUtils.makeShort(mViewPager, "哇哇哇，网络遇到问题了...").danger();
    }
}
