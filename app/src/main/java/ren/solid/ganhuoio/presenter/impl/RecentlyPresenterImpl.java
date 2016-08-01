package ren.solid.ganhuoio.presenter.impl;

import java.util.List;

import ren.solid.ganhuoio.model.IRecentlyModel;
import ren.solid.ganhuoio.model.impl.RecentlyModelImpl;
import ren.solid.ganhuoio.presenter.IRecentlyPresenter;
import ren.solid.ganhuoio.ui.view.IRecentlyView;
import ren.solid.library.rx.retrofit.subscriber.HttpResultSubscriber;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:04
 */
public class RecentlyPresenterImpl implements IRecentlyPresenter {

    private IRecentlyView view;
    private IRecentlyModel model;

    public RecentlyPresenterImpl(IRecentlyView view) {
        this.view = view;
        model = new RecentlyModelImpl();
    }

    @Override
    public void getRecentlyDate() {
        view.showLoading();
        model.loadRecentlyDate().subscribe(new HttpResultSubscriber<List<String>>() {
            @Override
            public void onSuccess(List<String> stringList) {
                view.hideLoading();
                if (stringList != null && stringList.size() > 5)
                    view.setDateList(stringList.subList(0, 5));
            }

            @Override
            public void _onError(Throwable e) {
                view.hideLoading();
                view.showError(e.getMessage());
            }
        });
    }
}
