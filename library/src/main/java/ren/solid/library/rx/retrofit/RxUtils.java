package ren.solid.library.rx.retrofit;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:17
 */
public class RxUtils {
    public static <T> FlowableTransformer<T, T> defaultSchedulers() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());

            }
        };
    }

    public static <T> FlowableTransformer<T, T> all_io() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());

            }
        };
    }
}
