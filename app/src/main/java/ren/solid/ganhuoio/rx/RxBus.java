package ren.solid.ganhuoio.rx;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:11:38
 */
public class RxBus {
    private static Map<Object, Subject> subjectList = new HashMap<>();

    private RxBus() {
    }

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }

    public static synchronized RxBus getInstance() {
        return RxBusHolder.instance;
    }

    public synchronized <T> Observable<T> register(@NonNull Object object) {
        Subject<T, T> subject = PublishSubject.create();
        subjectList.put(object, subject);
        return subject;
    }

    public synchronized void unregister(Object object) {
        subjectList.remove(object);
    }

    public void post(@NonNull Object content) {
        synchronized (this) {
            for (Subject subject : subjectList.values()) {
                if (subject != null) {
                    subject.onNext(content);
                }
            }
        }
    }
}
