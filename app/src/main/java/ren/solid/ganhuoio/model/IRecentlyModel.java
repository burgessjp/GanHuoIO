package ren.solid.ganhuoio.model;

import ren.solid.library.http.callback.adapter.JsonHttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:57
 */
public interface IRecentlyModel {

    void loadRecentlyDate(JsonHttpCallBack callBack);
}
