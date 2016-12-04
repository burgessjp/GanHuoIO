package ren.solid.ganhuoio.model.impl;

import java.util.List;
import java.util.Objects;

import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.IRecentlyModel;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.GanHuoTitleBean;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.ObservableProvider;
import ren.solid.library.rx.retrofit.TransformUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.subscriber.HttpResultSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:58
 */
public class RecentlyModelImpl implements IRecentlyModel {

    @Override
    public Observable<HttpResult<List<String>>> loadRecentlyDate() {

        GankService gankService = ServiceFactory.getInstance().createService(GankService.class);
        return gankService.getRecentlyDate();
    }

    @Override
    public Observable<HttpResult<List<GanHuoTitleBean>>> loadRecentlyTitle() {
        GankService gankService = ServiceFactory.getInstance().createService(GankService.class);
        return gankService.getTitles();

    }
}
