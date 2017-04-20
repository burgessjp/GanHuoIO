package ren.solid.ganhuoio.api;

import java.util.List;

import io.reactivex.Single;
import ren.solid.ganhuoio.bean.Daily;
import ren.solid.ganhuoio.bean.DailyList;
import ren.solid.ganhuoio.bean.GanHuoData;
import ren.solid.ganhuoio.bean.SearchResult;
import ren.solid.library.http.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by _SOLID
 * Date:2016/8/3
 * Time:9:28
 */
public interface GankService {

    String BASE_URL = "http://www.gank.io/api/";

    /**
     * 获取发布干货的日期
     *
     * @return
     */
    @GET("day/history")
    Single<HttpResult<List<String>>> getRecentlyDate();

    /***
     * 根据类别查询干货
     *
     * @param category
     * @param pageIndex
     * @return
     */
    @GET("data/{category}/20/{pageIndex}")
    Single<HttpResult<List<GanHuoData>>> getGanHuo(@Path("category") String category
            , @Path("pageIndex") int pageIndex);

    /**
     * 获取某天的干货
     *
     * @param date
     * @return
     */
    @GET("day/{date}")
    Single<HttpResult<DailyList>> getRecentlyGanHuo(@Path("date") String date);

    /**
     * 搜索
     *
     * @param keyword
     * @param pageIndex
     * @return
     */
    @GET("search/query/{keyword}/category/{category}/count/20/page/{pageIndex}")
    Single<HttpResult<List<SearchResult>>> search(
            @Path("category") String category
            , @Path("keyword") String keyword
            , @Path("pageIndex") int pageIndex);

    @GET("history/content/10/{pageIndex}")
    Single<HttpResult<List<Daily>>> getRecently(@Path("pageIndex") int pageIndex);
}
