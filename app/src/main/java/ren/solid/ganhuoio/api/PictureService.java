package ren.solid.ganhuoio.api;

import ren.solid.ganhuoio.model.bean.RandomPictureBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:17:06
 */
public interface PictureService {
    String BASE_URL = "http://lelouchcrgallery.tk/";

    @GET("{url}")
    Observable<RandomPictureBean> getRandomPicture(@Path("url") String url);
}
