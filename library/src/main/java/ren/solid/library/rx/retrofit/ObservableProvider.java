
package ren.solid.library.rx.retrofit;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.func.ResultFunc;
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

    private static String mDefaultBaseUrl = "http://www.gank.io/api/";
    private String mBaseUrl;
    private static Map<String, ObservableProvider> mProviders;

    private static class DefaultHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider(mDefaultBaseUrl);
    }

    private ObservableProvider(String baseUrl) {
        mBaseUrl = baseUrl;
        mBaseService = ServiceFactory.getInstance().createService(BaseService.class, mBaseUrl);

    }

    public static ObservableProvider getInstance(String baseUrl) {
        ObservableProvider provider;
        if (null == mProviders) {
            mProviders = new HashMap<>();
        }
        if (mProviders.containsKey(baseUrl)) {
            provider = mProviders.get(baseUrl);
        } else {
            provider = new ObservableProvider(baseUrl);
            mProviders.put(baseUrl, provider);
        }
        return provider;
    }

    public static ObservableProvider getDefault() {
        return DefaultHolder.INSTANCE;
    }

    public Observable<String> loadString(String url) {
        return mBaseService
                .loadString(url.substring(mBaseUrl.length()))
                .compose(TransformUtils.<ResponseBody>defaultSchedulers())
                .retryWhen(new RetryWhenNetworkException())
                .map(new StringFunc());
    }

    public <T> Observable<HttpResult<T>> loadResult(String url) {
        return loadString(url).map(new ResultFunc<T>());
    }
}
