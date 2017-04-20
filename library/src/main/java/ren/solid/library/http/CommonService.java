package ren.solid.library.http;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:14:53
 */
public interface CommonService {
    String BASE_URL = "http://www.example.com/";//这个不重要，可以随便写，但是必须有

    @GET
    Single<ResponseBody> loadString(@Url String url);

    @GET
    @Streaming
    Single<ResponseBody> download(@Url String url);
}
