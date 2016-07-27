package ren.solid.ganhuoio;

import android.test.AndroidTestCase;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.service.BaseService;
import rx.Subscriber;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:16:02
 */
public class AndroidTest extends AndroidTestCase {

    public void testRetrofit() {
        Log.e("testRetrofit", "testRetrofit");
        BaseService service = ServiceFactory.getInstance().createService(BaseService.class, BaseService.baseUrl);

        service.loadString("data/福利/10/1").subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("testRetrofit", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("testRetrofit", "onError");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    Log.e("testRetrofit", responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
