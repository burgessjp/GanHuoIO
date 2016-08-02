
package ren.solid.library.rx.retrofit;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.func.ResultFunc;
import ren.solid.library.rx.retrofit.func.RetryWhenNetworkException;
import ren.solid.library.rx.retrofit.func.StringFunc;
import ren.solid.library.rx.retrofit.service.BaseService;
import ren.solid.library.rx.retrofit.subscriber.DownLoadSubscribe;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:9:22
 */
public class ObservableProvider {

    private BaseService mBaseService;

    private static class DefaultHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    private ObservableProvider() {
        mBaseService = ServiceFactory.getInstance().createService(BaseService.class);

    }

    public static ObservableProvider getDefault() {
        return DefaultHolder.INSTANCE;
    }

    public Observable<String> loadString(String url) {
        return mBaseService
                .loadString(url.substring(mBaseService.BASE_URL.length()))
                .compose(TransformUtils.<ResponseBody>defaultSchedulers())
                .retryWhen(new RetryWhenNetworkException())
                .map(new StringFunc());
    }

    public <T> Observable<HttpResult<T>> loadResult(String url) {
        return loadString(url).map(new ResultFunc<T>());
    }


    public void download(String url, final DownLoadSubscribe subscribe) {
        mBaseService
                .download(url)
                .compose(TransformUtils.<ResponseBody>all_io())
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        subscribe.writeResponseBodyToDisk(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        subscribe.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscribe.onError(e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //do nothing
                    }
                });
    }


}
