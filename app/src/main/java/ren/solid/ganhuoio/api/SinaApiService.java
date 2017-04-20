package ren.solid.ganhuoio.api;

import io.reactivex.Single;
import ren.solid.ganhuoio.bean.Weibo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:17:50
 */
public interface SinaApiService {
    String BASE_URL = "https://api.weibo.com/2/";

    @GET("users/show.json")
    Single<Weibo> getUserInfo(@Query("access_token") String access_token, @Query("uid") String uid);
}
