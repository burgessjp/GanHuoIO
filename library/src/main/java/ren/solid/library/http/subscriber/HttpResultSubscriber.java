package ren.solid.library.http.subscriber;

import java.util.concurrent.CancellationException;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import ren.solid.library.http.HttpResult;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:27
 */
public abstract class HttpResultSubscriber<T> implements SingleObserver<HttpResult<T>> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull HttpResult<T> result) {
        if (!result.error)
            _onSuccess(result.results);
        else
            _onError(new Throwable("error=true"));
    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            //处理RxLife取消订阅的问题,这实际上并不是一个真正的错误
            //问题链接:https://github.com/trello/RxLifecycle/tree/2.x#unsubscription
            if (!(e instanceof CancellationException)) {
                e.printStackTrace();
                if (e.getMessage() == null) {
                    _onError(new Throwable(e.toString()));
                } else {
                    _onError(new Throwable(e.getMessage()));
                }
            }
        } else {
            _onError(new Exception("null message"));
        }
    }

    public abstract void _onSuccess(T t);

    public abstract void _onError(Throwable e);
}
