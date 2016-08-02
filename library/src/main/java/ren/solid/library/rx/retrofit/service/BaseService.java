package ren.solid.library.rx.retrofit.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:14:53
 */
public interface BaseService {
    String BASE_URL = "http://www.gank.io/api/";

    @GET("{url}")
    Observable<ResponseBody> loadString(@Path("url") String url);

    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);
}
