package ren.solid.library.rx.retrofit.subscriber;

import ren.solid.library.rx.retrofit.HttpResult;
import rx.Subscriber;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:27
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
            //在这里做全局的错误处理
//            if (e instanceof HttpException) {
//                // ToastUtils.getInstance().showToast(e.getMessage());
//            }
            if (e.getMessage() == null) {//e.getMessage()可能为null
                _onError(new Throwable(e.toString()));
            } else {
                _onError(e);
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
