package me.dmdevgo.rxpm_sample.ui.base;

import me.dmdevgo.rxpm_sample.rxpm.RxPresentationModel;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.PublishRelay;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Dmitriy Gorbunov
 */

public class LoadingPm<T> extends RxPresentationModel {

    private Subscription subscription;

    private PublishRelay<T> response = PublishRelay.create();
    private BehaviorRelay<Boolean> loading = BehaviorRelay.create(false);
    private PublishRelay<Throwable> error = PublishRelay.create();

    private PublishRelay<Observable<T>> requestRelay = PublishRelay.create();

    public Observable<T> response() {
        return response;
    }

    public Observable<Throwable> error() {
        return error;
    }

    public Observable<Boolean> loading() {
        return loading;
    }

    public Action1<Observable<T>> actionRequest() {
        return requestRelay;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        subscription = requestRelay
                .filter(observable -> !loading.getValue())
                .doOnNext(observable -> loading.call(true))
                .subscribeOn(Schedulers.io())
                .concatMap(observable -> observable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error)
                .doOnEach(notification -> loading.call(false))
                .retry()
                .subscribe(response);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
