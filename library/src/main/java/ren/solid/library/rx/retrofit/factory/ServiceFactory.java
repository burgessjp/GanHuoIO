package ren.solid.library.rx.retrofit.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.rxbinding.internal.Preconditions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import ren.solid.library.SolidApplication;
import ren.solid.library.utils.FileUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:15:23
 */
public class ServiceFactory {

    private final Gson mGsonDateFormat;

    public ServiceFactory() {
        mGsonDateFormat = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * create a service
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(mGsonDateFormat))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private final static long DEFAULT_TIMEOUT = 10;

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
        File httpCacheDirectory = new File(FileUtils.getCacheDir(SolidApplication.getInstance()), "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        //处理拦截器，主要是做了个header和连接超时、读取超时设置，我项目里header放了些签名信息，主要是这里能拿到整个请求的所有参数，做任何想做的事，而且是全局动态处理
        //if (httpClientBuilder.interceptors() != null) {
        //    httpClientBuilder.interceptors().clear();
        //}
        //httpClientBuilder.addNetworkInterceptor(new Interceptor() {
        //    @Override
        //    public Response intercept(Chain chain) throws IOException {
        //        return null;
        //    }
        //});
        // httpClientBuilder.addInterceptor(new CacheControlInterceptor());
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClientBuilder.addInterceptor(logging);
        return httpClientBuilder.build();
    }

    public final class UserAgentInterceptor implements Interceptor {
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        public UserAgentInterceptor(String userAgentHeaderValue) {
            this.userAgentHeaderValue = Preconditions.checkNotNull(userAgentHeaderValue, "userAgentHeaderValue = null");
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final okhttp3.Request originalRequest = chain.request();
            final okhttp3.Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }


}
