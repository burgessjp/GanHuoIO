package ren.solid.ganhuoio.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.bean.CategoryList;
import ren.solid.ganhuoio.bean.Daily;
import ren.solid.ganhuoio.common.constant.Category;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.http.HttpResult;
import ren.solid.library.http.ServiceFactory;
import ren.solid.library.http.subscriber.HttpResultSubscriber;
import ren.solid.library.rx.RxUtils;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:14:48
 * Desc:
 */

public class HomeFragment extends AbsListFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUserVisibleHint(true);
    }

    @Override
    public void loadData(final int pageIndex) {
        final List data = new ArrayList();
        if (pageIndex == getInitPageIndex()) {
            CategoryList categoryList = new CategoryList();
            categoryList.setData(Category.getGanHuoCateGory());
            data.add(categoryList);
        }

        ServiceFactory.getInstance()
                .createService(GankService.class)
                .getRecently(pageIndex)
                .compose(this.<HttpResult<List<Daily>>>bindToLifecycle())
                .compose(RxUtils.<HttpResult<List<Daily>>>defaultSchedulers_single())
                .subscribe(new HttpResultSubscriber<List<Daily>>() {
                    @Override
                    public void _onSuccess(List<Daily> dailies) {
                        data.addAll(dailies);
                        onDataSuccessReceived(pageIndex, data);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }
                });
    }

    @Override
    protected int getInitPageIndex() {
        return 1;
    }
}
