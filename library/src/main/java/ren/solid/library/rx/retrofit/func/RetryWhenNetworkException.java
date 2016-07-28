package ren.solid.library.rx.retrofit.func;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:38
 */
public class RetryWhenNetworkException implements Func1<Observable<? extends Throwable>, Observable<?>> {
    private int count = 3;//retry count
    private long delay = 3000;//delay time

    public RetryWhenNetworkException() {

    }

    public RetryWhenNetworkException(int count) {
        this.count = count;
    }

    public RetryWhenNetworkException(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable
                .zipWith(Observable.range(1, count + 1), new Func2<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper call(Throwable throwable, Integer integer) {
                        return new Wrapper(throwable, integer);
                    }
                }).flatMap(new Func1<Wrapper, Observable<?>>() {
                    @Override
                    public Observable<?> call(Wrapper wrapper) {
                        if ((wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException)
                                && wrapper.index < count + 1) {
                            return Observable.timer(delay + (wrapper.index - 1) * delay, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(wrapper.throwable);
                    }
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
