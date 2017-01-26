package me.dmdevgo.rxpm_sample.ui.auth.phone;

import me.dmdevgo.rxpm_sample.api.response.AuthPhoneNumberResponse;
import me.dmdevgo.rxpm_sample.dagger.ComponentHolder;
import me.dmdevgo.rxpm_sample.model.AuthModel;
import me.dmdevgo.rxpm_sample.model.Country;
import me.dmdevgo.rxpm_sample.model.PhoneUtil;
import me.dmdevgo.rxpm_sample.rxpm_navigation.ScreenPm;
import me.dmdevgo.rxpm_sample.ui.auth.country.ChooseCountryPm;
import me.dmdevgo.rxpm_sample.ui.base.LoadingPm;
import me.dmdevgo.rxpm_sample.ui.messages.ChooseCountryMessage;
import me.dmdevgo.rxpm_sample.ui.messages.PhoneNumberSentMessage;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.PublishRelay;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * @author Dmitriy Gorbunov
 */
public class EnterPhonePm extends ScreenPm {

    @Inject
    PhoneUtil phoneUtil;

    @Inject
    AuthModel authModel;

    private final LoadingPm<AuthPhoneNumberResponse> loadingPm = new LoadingPm<>();

    private final BehaviorRelay<String> countryName = BehaviorRelay.create("");
    private final BehaviorRelay<String> countryCode = BehaviorRelay.create("");
    private final BehaviorRelay<String> phoneNumber = BehaviorRelay.create("");
    private final BehaviorRelay<Boolean> canSendPhone = BehaviorRelay.create(false);
    private final BehaviorRelay<Country> countryChoose = BehaviorRelay.create();

    private final PublishRelay<String> countryCodeChange = PublishRelay.create();
    private final PublishRelay<String> phoneNumberChange = PublishRelay.create();
    private final PublishRelay<Void> doneAction = PublishRelay.create();
    private final PublishRelay<Void> countryClick = PublishRelay.create();

    public EnterPhonePm() {
        ComponentHolder.getInstance().getAppComponent().inject(this);
    }

    public Observable<String> countryName() {
        return countryName.distinctUntilChanged();
    }

    public Observable<String> countryCode() {
        return countryCode;
    }

    public Observable<String> phoneNumber() {
        return phoneNumber.distinctUntilChanged();
    }

    public Observable<Boolean> canSendPhone() {
        return canSendPhone.asObservable();
    }

    public Observable<Boolean> sendingPhoneProgress() {
        return loadingPm.loading();
    }

    public Action1<String> countryCodeChange() {
        return countryCodeChange;
    }

    public Action1<String> phoneNumberChange() {
        return phoneNumberChange;
    }

    public Action1<Void> doneAction() {
        return doneAction;
    }

    public Action1<Void> countryClick() {
        return countryClick;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadingPm.onCreate();
        init();
        countryChoose.call(phoneUtil.getCountryForRegionCode("RU"));
    }

    private void init() {

        countryCodeChange
                .map(code -> "+" + code.replaceAll("\\D", ""))
                .subscribe(countryCode);

        countryCodeChange
                .map(code -> code.replaceAll("\\D", ""))
                .map(code -> {
                    try {
                        if (code.length() > 3) {
                            Phonenumber.PhoneNumber number = phoneUtil.parsePhone("+" + code);
                            phoneNumberChange.call(String.valueOf(number.getNationalNumber()));
                            return phoneUtil.getCountryForCountryCode(number.getCountryCode());
                        }
                    } catch (NumberParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        return phoneUtil.getCountryForCountryCode(Integer.parseInt(code));
                    } catch (NumberFormatException e) {
                        return Country.UNKNOWN;
                    }
                }).subscribe(countryChoose);

        countryChoose
                .filter(country -> country != Country.UNKNOWN)
                .map(country -> "+" + country.getCountryCallingCode())
                .subscribe(countryCode);

        countryChoose
                .map(Country::getDisplayName)
                .subscribe(countryName);

        Observable.combineLatest(countryChoose, phoneNumberChange, phoneUtil::formatPhoneNumber)
                .subscribe(phoneNumber);

        Observable.combineLatest(countryChoose, phoneNumber, phoneUtil::isValidPhone)
                .subscribe(canSendPhone);

        doneAction
                .withLatestFrom(canSendPhone, (done, canSend) -> canSend)
                .filter(canSend -> canSend)
                .map(aBoolean -> getPhone())
                .map(authModel::authPhoneNumber)
                .subscribe(loadingPm.actionRequest());

        loadingPm
                .error()
                .subscribe(errorRelay);

        loadingPm
                .response()
                .map(response -> response.isSuccess() ? new PhoneNumberSentMessage(response.getPhoneNumber()) : EMPTY)
                .subscribe(messagesFromPm);

        countryClick
                .map(aVoid -> new ChooseCountryMessage())
                .subscribe(messagesFromPm);

        receiveMessages()
                .ofType(ChooseCountryPm.ChosenCountryMessage.class)
                .map(msg -> msg.country)
                .subscribe(countryChoose);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadingPm.onDestroy();
    }

    private String getPhone() {
        return (countryChoose.getValue().getCountryCallingCode()
                + phoneNumber.getValue()).replaceAll("\\D","");
    }
}
