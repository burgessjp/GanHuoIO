package ren.solid.ganhuoio.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.presenter.ICategoryPresenter;
import ren.solid.ganhuoio.presenter.impl.CategoryPresenterImpl;
import ren.solid.ganhuoio.ui.adapter.GanHuoPagerAdapter;
import ren.solid.ganhuoio.ui.view.ICategoryView;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.Logger;

/**
 * Created by _SOLID
 * Date:2016/5/17
 * Time:9:33
 */
public class CategoryFragment extends BaseFragment implements ICategoryView {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private GanHuoPagerAdapter mAdapter;
    private ICategoryPresenter mIHomePresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void handleRxMsg(String msg) {
        Logger.i(this, msg);
        if (msg.equals("SortChange")) {
            mAdapter.addAll(Apis.getGanHuoCateGory());
        }
    }

    @Override
    protected void setUpView() {
        mTabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.viewpager);
    }

    @Override
    protected void setUpData() {
        mIHomePresenter = new CategoryPresenterImpl(this);

        //减少打开Fragment时切换时的一些卡顿
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIHomePresenter.getAdapterData();
            }
        }, 200);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void setPagerAdapterData(List<String> list) {
        mAdapter = new GanHuoPagerAdapter(getChildFragmentManager(), list);
        for (int i = 0; i < list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(list.get(i)));
        }
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
