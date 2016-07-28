package ren.solid.ganhuoio.model.impl;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.IRecentlyModel;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.ObservableProvider;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:58
 */
public class RecentlyModelImpl implements IRecentlyModel {

    @Override
    public Observable<HttpResult<List<String>>> loadRecentlyDate() {
        Type t = new TypeToken<HttpResult<List<String>>>() {
        }.getType();
        return ObservableProvider.getInstance().loadResult(Apis.Urls.GanHuoDates, t);
    }
}
