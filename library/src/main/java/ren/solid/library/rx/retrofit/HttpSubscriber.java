package ren.solid.library.rx.retrofit;

import rx.Subscriber;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:27
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
