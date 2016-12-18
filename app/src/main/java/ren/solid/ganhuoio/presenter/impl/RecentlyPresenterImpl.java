package ren.solid.ganhuoio.presenter.impl;

import java.util.List;

import ren.solid.ganhuoio.model.IRecentlyModel;
import ren.solid.ganhuoio.model.bean.GanHuoRecentlyWrapper;
import ren.solid.ganhuoio.model.bean.GanHuoTitleBean;
import ren.solid.ganhuoio.model.impl.RecentlyModelImpl;
import ren.solid.ganhuoio.presenter.IRecentlyPresenter;
import ren.solid.ganhuoio.ui.view.IRecentlyView;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.RxUtils;
import ren.solid.library.rx.retrofit.subscriber.HttpResultSubscriber;
import rx.functions.Func2;

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
        model.loadRecentlyTitle()
                .zipWith(model.loadRecentlyDate(), new Func2<HttpResult<List<GanHuoTitleBean>>, HttpResult<List<String>>, HttpResult<GanHuoRecentlyWrapper>>() {
                    @Override
                    public HttpResult<GanHuoRecentlyWrapper> call(HttpResult<List<GanHuoTitleBean>> listHttpResult, HttpResult<List<String>> listHttpResult2) {
                        HttpResult<GanHuoRecentlyWrapper> wrapper = new HttpResult<>();
                        wrapper.error = false;
                        wrapper.results = new GanHuoRecentlyWrapper();
                        wrapper.results.dateList = listHttpResult2.results.subList(0, 5);
                        wrapper.results.titleList = listHttpResult.results;
                        for (int i = 0; i < wrapper.results.dateList.size(); i++) {
                            String title = wrapper.results.titleList.get(i).getTitle();
                            title = "[" + wrapper.results.dateList.get(i) + "] :" + title;
                            wrapper.results.titleList.get(i).setTitle(title);
                        }
                        return wrapper;
                    }
                })
                .compose(RxUtils.<HttpResult<GanHuoRecentlyWrapper>>defaultSchedulers())
                .subscribe(new HttpResultSubscriber<GanHuoRecentlyWrapper>() {
                    @Override
                    public void onSuccess(GanHuoRecentlyWrapper ganHuoRecentlyWrapper) {
                        view.hideLoading();
                        view.setDate(ganHuoRecentlyWrapper);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        view.hideLoading();
                        view.showError(e.getMessage());
                    }
                });
    }
}
