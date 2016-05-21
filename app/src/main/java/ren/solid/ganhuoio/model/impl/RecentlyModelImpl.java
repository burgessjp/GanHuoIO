package ren.solid.ganhuoio.model.impl;

import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.IRecentlyModel;
import ren.solid.library.http.HttpClientManager;
import ren.solid.library.http.callback.adapter.JsonHttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:58
 */
public class RecentlyModelImpl implements IRecentlyModel {

    @Override
    public void loadRecentlyDate(JsonHttpCallBack callBack) {
        HttpClientManager.getData(Apis.Urls.GanHuoDates, callBack);
    }
}
