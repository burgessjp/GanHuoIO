package ren.solid.library.rx;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by _SOLID
 * Date:2016/6/1
 * Time:11:44
 */
public class RxBus {

    private final Subject<Object> _bus;

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }

    private RxBus() {
        _bus = PublishSubject.create();
    }

    public static synchronized RxBus getInstance() {
        return RxBusHolder.instance;
    }

    public void send(Object o) {
        _bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }
}
