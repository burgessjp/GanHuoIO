package ren.solid.ganhuoio.model;

import java.util.List;

import ren.solid.ganhuoio.model.bean.GanHuoTitleBean;
import ren.solid.library.rx.retrofit.HttpResult;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:57
 */
public interface IRecentlyModel {

    Observable<HttpResult<List<String>>> loadRecentlyDate();
    Observable<HttpResult<List<GanHuoTitleBean>>> loadRecentlyTitle();
}
