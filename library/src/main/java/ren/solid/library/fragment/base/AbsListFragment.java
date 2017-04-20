package ren.solid.library.fragment.base;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.solidev.statusviewlayout.StatusViewLayout;
import ren.solid.library.R;
import ren.solid.library.utils.ToastUtils;
import ren.solid.library.widget.loadmore.LoadMoreWrapper;

/**
 * Created by _SOLID
 * Date:2016/9/28
 * Time:15:02
 * Desc:列表基类，默认线性布局
 */

public abstract class AbsListFragment extends LazyLoadFragment implements IList {

    protected StatusViewLayout mStatusViewLayout;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;

    protected LoadMoreWrapper mLoadMoreWrapper;
    protected int mCurrentPageIndex;
    protected int mPageSize;
    protected List mItems;

    protected boolean isCanLoadMore = true;
    private MultiTypeAdapter mMultiTypeAdapter;

    public void disAbleLoadMore() {
        isCanLoadMore = false;
        mLoadMoreWrapper.disableLoadMore();
    }

    public void disAbleRefresh() {
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected final int setLayoutResourceID() {
        return R.layout.fragment_base_recyclerview;
    }

    @Override
    protected final void init() {
        mCurrentPageIndex = getInitPageIndex();
        mItems = new ArrayList<>();
        mMultiTypeAdapter = getAdapter();
        mMultiTypeAdapter.applyGlobalMultiTypePool();
        mLoadMoreWrapper = new LoadMoreWrapper(getContext(), mMultiTypeAdapter);
        mLoadMoreWrapper.setOnLoadListener(new LoadMoreWrapper.OnLoadListener() {
            @Override
            public void onRetry() {
                loadData(mCurrentPageIndex);
            }

            @Override
            public void onLoadMore() {
                if (isCanLoadMore)
                    AbsListFragment.this.loadMore();
            }
        });
    }

    protected void registerItemProvider(MultiTypeAdapter adapter) {

    }

    @Override
    protected void setUpView() {
        mStatusViewLayout = $(R.id.status_view_layout);
        mSwipeRefreshLayout = $(R.id.swipe_refresh_layout);
        mRecyclerView = $(R.id.recyclerview);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        customConfig();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        mStatusViewLayout.setOnRetryListener(new View.OnClickListener() {//错误重试
            @Override
            public void onClick(View v) {
                mStatusViewLayout.showLoading();
                loadData(getInitPageIndex());
            }
        });

        registerItemProvider(mMultiTypeAdapter);


    }


    @Override
    protected final void setUpData() {
    }

    @Override
    protected void lazyLoad() {
        showLoading();
        loadData(getInitPageIndex());//初始加载首页数据
    }

    @Override
    public final void refreshData() {
        mCurrentPageIndex = getInitPageIndex();
        if (isCanLoadMore)
            mLoadMoreWrapper.showLoadMore();
        loadData(getInitPageIndex());
    }

    @Override
    public final void loadMore() {
        loadData(++mCurrentPageIndex);
    }

    @Override
    public abstract void loadData(int pageIndex);

    //region 可直接调用的方法

    /**
     * 列表数据接收成功时调用（相关的实现类需要手动去调用此方法）
     *
     * @param pageIndex 当前请求的页数
     * @param items     返回的数据
     */
    @SuppressWarnings("unchecked")
    protected final void onDataSuccessReceived(int pageIndex, List items) {
        showContent();
        if (pageIndex == getInitPageIndex() && (items == null || items.size() <= 0)) {//无数据
            showEmpty(getEmptyMsg());
        } else if (pageIndex == getInitPageIndex()) {//刷新
            mItems.clear();
            mItems.addAll(items);
            if (items.size() < mPageSize) {
                mLoadMoreWrapper.showLoadComplete();
            }
        } else if (items != null && items.size() != 0) {//加载更多
            mItems.addAll(items);
        } else {//没有更多数据了
            mCurrentPageIndex--;
            mLoadMoreWrapper.showLoadComplete();
        }

        mLoadMoreWrapper.notifyDataSetChanged();

    }


    /**
     * 得到当前列表数据
     *
     * @return 当前列表数据
     */
    protected final List getItems() {
        return mItems;
    }

    /**
     * 添加分隔线
     *
     * @param itemDecoration 分隔线
     */
    protected final void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (mRecyclerView != null)
            mRecyclerView.addItemDecoration(itemDecoration);
    }


    //endregion

    //region 根据具体的情况可选择性实现下面方法

    protected void customConfig() {

    }

    protected int getInitPageIndex() {
        return 1;
    }

    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(mItems);
    }

    @NonNull
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @NonNull
    protected String getEmptyMsg() {
        return "无数据";
    }

    //endregion

    //region 数据加载状态的处理
    @Override
    public void showError(Exception e) {
        if (mCurrentPageIndex == getInitPageIndex()) {
            mStatusViewLayout.showError(e.getMessage());
        } else {
            mLoadMoreWrapper.showLoadError();
            ToastUtils.getInstance().showToast(e.getMessage());
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmpty(String msg) {
        mStatusViewLayout.showEmpty(msg);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mStatusViewLayout.showLoading();
    }

    @Override
    public void showContent() {
        mStatusViewLayout.showContent();
        mSwipeRefreshLayout.setRefreshing(false);
    }
    //endregion

    public boolean isTop() {
        if (mRecyclerView != null && mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
            if (firstVisiblePosition != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean scrollToTop() {
        if (!isTop()) {
            mRecyclerView.smoothScrollToPosition(0);
            return false;
        }
        return true;
    }
}
