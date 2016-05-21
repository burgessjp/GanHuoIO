package ren.solid.ganhuoio.model;

import java.util.List;

import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.library.http.callback.adapter.JsonHttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:14:05
 */
public interface ICollectModel {

    void loadCollect(JsonHttpCallBack<List<CollectTable>> list);
}
