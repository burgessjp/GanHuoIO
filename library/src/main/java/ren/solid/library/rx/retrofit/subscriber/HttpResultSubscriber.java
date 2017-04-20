package ren.solid.library.rx.retrofit.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import ren.solid.library.rx.retrofit.HttpResult;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:27
 */
public abstract class HttpResultSubscriber<T> implements Subscriber<HttpResult<T>> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(1);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
            if (e.getMessage() == null) {
                _onError(new Throwable(e.toString()));
            } else {
                _onError(new Throwable(e.getMessage()));
            }
        } else {
            _onError(new Exception("null message"));
        }
    }

    @Override
    public void onNext(HttpResult<T> t) {
        if (!t.error)
            onSuccess(t.results);
        else
            _onError(new Throwable("error=true"));
    }

    public abstract void onSuccess(T t);

    public abstract void _onError(Throwable e);
}
