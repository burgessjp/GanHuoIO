package ren.solid.library.rx.retrofit.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import ren.solid.library.rx.OkHttpProvider;
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
    private OkHttpClient mOkHttpClient;


    public ServiceFactory() {
        mGsonDateFormat = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        mOkHttpClient = OkHttpProvider.getDefaultOkHttpClient();

    }

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static ServiceFactory getNoCacheInstance() {
        ServiceFactory factory = SingletonHolder.INSTANCE;
        factory.mOkHttpClient = OkHttpProvider.getNoCacheOkHttpClient();
        return factory;
    }

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
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGsonDateFormat))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }


}
