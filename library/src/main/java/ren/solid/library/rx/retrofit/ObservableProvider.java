
package ren.solid.library.rx.retrofit;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.func.RetryWhenNetworkException;
import ren.solid.library.rx.retrofit.func.StringFunc;
import ren.solid.library.rx.retrofit.service.BaseService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:9:22
 */
public class ObservableProvider {

    private BaseService mBaseService;

    private String mBaseUrl = "http://www.gank.io/api/";

    private static class SingletonHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    private ObservableProvider() {
        mBaseService = ServiceFactory.getInstance().createService(BaseService.class, mBaseUrl);
    }

    public static ObservableProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<String> loadString(String url) {
        return mBaseService
                .loadString(url.substring(mBaseUrl.length()))
                .compose(TransformUtils.<ResponseBody>defaultSchedulers())
                .retryWhen(new RetryWhenNetworkException())
                .map(new StringFunc());
    }

    public <T> Observable<HttpResult<T>> loadResult(String url, final Type type) {
        return loadString(url).map(new Func1<String, HttpResult<T>>() {
            @Override
            public HttpResult<T> call(String s) {
                Gson gson = new Gson();
                HttpResult<T> httpResult = gson.fromJson(s, type);
                return httpResult;
            }
        });
    }
}
