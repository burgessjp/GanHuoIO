package ren.solid.ganhuoio.api;

import ren.solid.ganhuoio.model.bean.WeiboBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:17:50
 */
public interface SinaApiService {
    @GET("users/show.json")
    Observable<WeiboBean> getUserInfo(@Query("access_token") String access_token, @Query("uid") String uid);
}
