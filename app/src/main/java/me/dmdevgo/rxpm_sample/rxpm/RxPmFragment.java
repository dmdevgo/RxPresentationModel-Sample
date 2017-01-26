package me.dmdevgo.rxpm_sample.rxpm;

import me.dmdevgo.rxpm_sample.pm.PmFragment;
import me.dmdevgo.rxpm_sample.pm.PmMessageReceiver;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Dmitriy Gorbunov
 */
public abstract class RxPmFragment<PM extends RxPresentationModel> extends PmFragment<PM>
        implements ErrorHandler, PmMessageReceiver {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected <T> void subscribe(Observable<T> observable, final Action1<? super T> onNext) {
        compositeSubscription.add(
                observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onNext, this::onError)
        );
    }

    @Override
    public void onBindPresentationModel(PM pm) {
        subscribe(pm.messages(), this::onReceive);
        subscribe(pm.errors(), this::onError);
    }

    public void onUnbindPresentationModel(PM pm) {
        compositeSubscription.clear();
    }

    @Override
    public PM getPresentationModel() {
        return super.getPresentationModel();
    }
}
