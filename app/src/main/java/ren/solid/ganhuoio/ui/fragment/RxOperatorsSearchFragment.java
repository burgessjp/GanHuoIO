package ren.solid.ganhuoio.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.SearchView;

import com.jakewharton.rxbinding.widget.RxSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.bomb.RxOperators;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.adapter.SolidRVBaseAdapter;
import ren.solid.library.fragment.base.BaseFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by _SOLID
 * Date:2016/6/1
 * Time:15:38
 */
public class RxOperatorsSearchFragment extends BaseFragment {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private RxOperatorsAdapter mAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_rx_operators_search;
    }

    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.recyclerview);
        mSearchView = $(R.id.search);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setFocusable(false);
        mSearchView.requestFocus();

        mAdapter = new RxOperatorsAdapter(getMContext(), new ArrayList<RxOperators>());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));


        RxSearchView.queryTextChanges(mSearchView).debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        queryFromBomb(charSequence);
                    }
                });

    }

    private void queryFromBomb(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            BmobQuery<RxOperators> query = new BmobQuery<>();
            query.addWhereContains("name", charSequence.toString());
            query.findObjects(getMContext(), new FindListener<RxOperators>() {
                @Override
                public void onSuccess(List<RxOperators> list) {
                    mAdapter.addAll(list, true);
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        } else {
            mAdapter.clear();
        }
    }

    @Override
    protected void setUpData() {

    }

    public static class RxOperatorsAdapter extends SolidRVBaseAdapter<RxOperators> {

        public RxOperatorsAdapter(Context context, List<RxOperators> beans) {
            super(context, beans);
        }

        @Override
        protected void onBindDataToView(SolidCommonViewHolder holder, RxOperators bean, int position) {
            holder.setText(R.id.tv_name, bean.getName());
        }

        @Override
        public int getItemLayoutID(int viewType) {
            return R.layout.item_rxoperators;
        }

        @Override
        protected void onItemClick(int position) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.TITLE, mBeans.get(position).getName());
            intent.putExtra(WebViewActivity.WEB_URL, mBeans.get(position).getDesc());
            mContext.startActivity(intent);
        }
    }
}
