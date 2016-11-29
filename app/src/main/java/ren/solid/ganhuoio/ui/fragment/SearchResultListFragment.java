package ren.solid.ganhuoio.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.bean.SearchResult;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.adapter.SolidRVBaseAdapter;
import ren.solid.library.fragment.XRecyclerViewFragment;
import ren.solid.library.utils.json.JsonConvert;

import static u.aly.av.W;

/**
 * Created by _SOLID
 * Date:2016/11/22
 * Time:9:56
 * Desc:
 */

public class SearchResultListFragment extends XRecyclerViewFragment<SearchResult> {
    private String keyWord = "Android";

    public static SearchResultListFragment newInstance(String keyWord) {

        SearchResultListFragment fragment = new SearchResultListFragment();
        fragment.keyWord = keyWord;
        return fragment;
    }

    @Override
    protected String getUrl(int mCurrentPageIndex) {

        return String.format(Apis.Urls.GanHuoSearchResult, keyWord, mCurrentPageIndex);
    }

    @Override
    protected List parseData(String result) {
        List<SearchResult> list;
        JsonConvert<List<SearchResult>> jsonConvert = new JsonConvert<List<SearchResult>>() {
        };
        jsonConvert.setDataName("results");
        list = jsonConvert.parseData(result);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    protected SolidRVBaseAdapter setAdapter() {
        return new SolidRVBaseAdapter<SearchResult>(getContext(), new ArrayList()) {

            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, SearchResult bean, int position) {
                holder.setText(R.id.tv_title, bean.getDesc());
                holder.setText(R.id.tv_author, bean.getWho());
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_search_result;
            }

            @Override
            protected void onItemClick(int position) {
                Intent intent = new Intent(getMContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_URL, mBeans.get(position).getUrl());
                intent.putExtra(WebViewActivity.TITLE, mBeans.get(position).getDesc());
                getMContext().startActivity(intent);
            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getMContext());
    }
}
