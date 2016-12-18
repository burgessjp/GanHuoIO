package ren.solid.ganhuoio.ui.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.model.bean.SearchResult;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.RxUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.subscriber.HttpResultSubscriber;
import ren.solid.library.widget.LinearDecoration;

/**
 * Created by _SOLID
 * Date:2016/11/22
 * Time:9:56
 * Desc:
 */

public class SearchResultListFragment extends AbsListFragment {
    private String keyWord = "Android";

    public static SearchResultListFragment newInstance(String keyWord) {

        SearchResultListFragment fragment = new SearchResultListFragment();
        fragment.keyWord = keyWord;
        return fragment;
    }

    @Override
    protected void customConfig() {
        addItemDecoration(new LinearDecoration(getContext(), RecyclerView.VERTICAL));
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance().createService(GankService.class)
                .search(keyWord, pageIndex)
                .compose(RxUtils.<HttpResult<List<SearchResult>>>defaultSchedulers())
                .subscribe(new HttpResultSubscriber<List<SearchResult>>() {
                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void onSuccess(List<SearchResult> searchResults) {
                        onDataSuccessReceived(pageIndex, searchResults);
                    }


                });
    }


}
