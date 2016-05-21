package ren.solid.ganhuoio.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.presenter.impl.CollectPresenterImpl;
import ren.solid.ganhuoio.ui.view.ICollectView;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.adapter.SolidRVBaseAdapter;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.DateUtils;
import ren.solid.library.utils.Logger;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:23
 */
public class CollectFragment extends BaseFragment implements ICollectView {

    private CollectPresenterImpl mPresenter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SolidRVBaseAdapter<CollectTable> mAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.recyclerview);
        mSwipeRefreshLayout = $(R.id.swipeLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mAdapter = new SolidRVBaseAdapter<CollectTable>(getMContext(), new ArrayList<CollectTable>()) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, CollectTable bean, int position) {
                String date = bean.getCreatedAt();//bean.getPublishedAt().replace('T', ' ').replace('Z', ' ');
                holder.setText(R.id.tv_people, "by " + bean.getWho());
                holder.setText(R.id.tv_time, "收藏时间:" + DateUtils.friendlyTime(date));
                holder.setText(R.id.tv_tag, bean.getType());
                holder.setText(R.id.tv_desc, bean.getDesc());

            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_collect;
            }

            @Override
            protected void onItemClick(int position) {
                Intent intent = new Intent(getMContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_URL, mBeans.get(position).getUrl());
                intent.putExtra(WebViewActivity.TITLE, mBeans.get(position).getDesc());
                getMContext().startActivity(intent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getCollect();
            }
        });
    }

    @Override
    protected void setUpData() {
        mPresenter = new CollectPresenterImpl(this);
        mPresenter.getCollect();


    }

    @Override
    public void getCollect(List<CollectTable> list) {
        Logger.i(this, "list.size():" + list.size());
        mAdapter.addAll(list, true);
    }

    @Override
    public void showLoading() {
        Logger.i(this, "showLoading");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        Logger.i(this, "hideLoading");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(final String errMsg) {
        Logger.i(this, "showError：" + errMsg);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                SnackBarUtils.makeShort(mRecyclerView, errMsg).warning();
            }
        }, 100);

    }
}
