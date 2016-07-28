package ren.solid.library.rx.retrofit.service;

import okhttp3.ResponseBody;
import ren.solid.library.rx.retrofit.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:14:53
 */
public interface BaseService {

    @GET("{url}")
    Observable<ResponseBody> loadString(@Path("url") String url);
}
