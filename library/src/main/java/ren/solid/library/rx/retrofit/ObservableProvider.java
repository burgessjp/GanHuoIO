
package ren.solid.library.rx.retrofit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import ren.solid.library.rx.retrofit.func.ResultFunc;
import ren.solid.library.rx.retrofit.func.StringFunc;
import ren.solid.library.rx.retrofit.subscriber.DownLoadSubscribe;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:9:22
 */
public class ObservableProvider {

    private CommonService mCommonService;

    private static class DefaultHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    private ObservableProvider() {
        mCommonService = ServiceFactory.getInstance().createService(CommonService.class);

    }

    public static ObservableProvider getDefault() {
        return DefaultHolder.INSTANCE;
    }

    public Flowable<String> loadString(String url) {
        return mCommonService
                .loadString(url)
                .map(new StringFunc());
    }

    public <T> Flowable<HttpResult<T>> loadResult(String url) {
        return loadString(url).map(new ResultFunc<T>());
    }

    public void download(String url, final DownLoadSubscribe subscribe) {
        mCommonService
                .download(url)
                .compose(RxUtils.<ResponseBody>all_io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        subscribe.writeResponseBodyToDisk(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }

                    @Override
                    public void onComplete() {
                        subscribe.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscribe.onError(e);
                    }
                });

    }


}
