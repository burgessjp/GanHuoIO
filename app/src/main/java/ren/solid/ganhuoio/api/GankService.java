package ren.solid.ganhuoio.api;

import java.util.List;

import ren.solid.ganhuoio.model.bean.GanHuoTitleBean;
import ren.solid.library.rx.retrofit.HttpResult;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/8/3
 * Time:9:28
 */
public interface GankService {

    String BASE_URL = "http://www.gank.io/api/";

    @GET("day/history")
    Observable<HttpResult<List<String>>> getRecentlyDate();

    @GET("history/content/5/1")
    Observable<HttpResult<List<GanHuoTitleBean>>> getTitles();
}
