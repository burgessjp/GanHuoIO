package ren.solid.ganhuoio.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.GanHuoRecentlyWrapper;
import ren.solid.ganhuoio.presenter.IRecentlyPresenter;
import ren.solid.ganhuoio.presenter.impl.RecentlyPresenterImpl;
import ren.solid.ganhuoio.ui.activity.MainActivity;
import ren.solid.ganhuoio.ui.adapter.GanHuoRecentlyPageAdapter;
import ren.solid.ganhuoio.ui.view.IRecentlyView;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.Logger;
import ren.solid.library.utils.SnackBarUtils;
import ren.solid.library.widget.StatusView;

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
    private StatusView mStatusView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_recently;
    }

    @Override
    protected void setUpView() {
        mTabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.viewpager);
        mStatusView = $(R.id.status_view);
        mStatusView.setOnRetryListener(new View.OnClickListener() {
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
        mStatusView.showLoading();
    }

    @Override
    public void setDateList(GanHuoRecentlyWrapper wrapper) {
        mPageAdapter = new GanHuoRecentlyPageAdapter(getChildFragmentManager(), wrapper.dateList,wrapper.titleList);
        mViewPager.setAdapter(mPageAdapter);
        for (int i = 0; i < wrapper.dateList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showLoading() {
        mStatusView.showLoading();

    }

    @Override
    public void hideLoading() {
        mStatusView.showContent();
    }

    @Override
    public void showError(String errMsg) {
        mStatusView.showError();

        SnackBarUtils.makeShort(mViewPager, "哇哇哇，网络遇到问题了...").danger();
    }
}
