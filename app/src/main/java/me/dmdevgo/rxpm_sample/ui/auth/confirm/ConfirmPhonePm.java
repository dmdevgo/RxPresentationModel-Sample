package me.dmdevgo.rxpm_sample.ui.auth.confirm;

import me.dmdevgo.rxpm_sample.api.response.ConfirmPhoneNumberResponse;
import me.dmdevgo.rxpm_sample.dagger.ComponentHolder;
import me.dmdevgo.rxpm_sample.model.AuthModel;
import me.dmdevgo.rxpm_sample.model.PhoneUtil;
import me.dmdevgo.rxpm_sample.rxpm_navigation.ScreenPm;
import me.dmdevgo.rxpm_sample.ui.base.LoadingPm;
import me.dmdevgo.rxpm_sample.ui.messages.AuthIsSuccessfulMessage;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.PublishRelay;

import javax.inject.Inject;

import me.dmdevgo.rxpm_sample.rxpm.RxPresentationModel;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Dmitriy Gorbunov
 */

public class ConfirmPhonePm extends ScreenPm {

    private static final int SMS_CODE_LENGTH = 4;
    private final String phone;

    @Inject
    AuthModel authModel;

    @Inject
    PhoneUtil phoneUtil;

    private final LoadingPm<ConfirmPhoneNumberResponse> loadingPm = new LoadingPm<>();

    private final BehaviorRelay<String> formattedPhone = BehaviorRelay.create();

    private final BehaviorRelay<String> smsCodeChange = BehaviorRelay.create();
    private final PublishRelay<Void> doneAction = PublishRelay.create();

    public ConfirmPhonePm(String phoneNumber) {
        ComponentHolder.getInstance().getAppComponent().inject(this);
        this.phone = phoneNumber;
        formattedPhone.call(phoneUtil.formatPhone(phoneNumber));
    }

    public Observable<String> getFormattedPhone() {
        return formattedPhone;
    }

    public Observable<Boolean> confirmPhoneProgress() {
        return loadingPm.loading();
    }

    public Observable<Boolean> canSend() {
        return Observable.combineLatest(loadingPm.loading(), smsCodeChange,
                (loading, smsCode) -> !loading && smsCode.length() == SMS_CODE_LENGTH);
    }

    public Action1<String> smsCodeChange() {
        return smsCodeChange;
    }

    public Action1<Void> doneAction() {
        return doneAction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadingPm.onCreate();

        Observable.combineLatest(doneAction, smsCodeChange, (aVoid, smsCode) -> smsCode)
                .map(smsCode -> smsCode.replaceAll("\\D", ""))
                .filter(smsCode -> smsCode.length() == SMS_CODE_LENGTH)
                .map(smsCode -> authModel.confirmPhoneNumber(phone, smsCode))
                .subscribe(loadingPm.actionRequest());

        smsCodeChange.subscribe(smsCode -> doneAction.call(null));

        loadingPm
                .error()
                .subscribe(errorRelay);

        loadingPm
                .response()
                .map(response -> response.isSuccess() ? new AuthIsSuccessfulMessage() : RxPresentationModel.EMPTY)
                .subscribe(messagesFromPm);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadingPm.onDestroy();
    }
}
