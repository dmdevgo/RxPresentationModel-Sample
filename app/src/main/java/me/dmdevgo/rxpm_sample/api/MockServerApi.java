package me.dmdevgo.rxpm_sample.api;

import me.dmdevgo.rxpm_sample.api.errors.WrongSmsCodeError;
import me.dmdevgo.rxpm_sample.api.request.AuthPhoneNumberRequest;
import me.dmdevgo.rxpm_sample.api.request.ConfirmPhoneNumberRequest;
import me.dmdevgo.rxpm_sample.api.response.AuthPhoneNumberResponse;
import me.dmdevgo.rxpm_sample.api.response.ConfirmPhoneNumberResponse;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * @author Dmitriy Gorbunov
 */

public class MockServerApi implements ServerApi {

    private static final String HARDCODED_SMS_CODE = "1234";

    @Override
    public Observable<AuthPhoneNumberResponse> authPhoneNumber(AuthPhoneNumberRequest request) {
        return Observable.just(AuthPhoneNumberResponse.createSuccessResponse(request.getPhoneNumber()))
                .delay(1, TimeUnit.SECONDS);
    }

    @Override
    public Observable<ConfirmPhoneNumberResponse> confirmPhoneNumber(ConfirmPhoneNumberRequest request) {
        if (!request.getCode().equals(HARDCODED_SMS_CODE)) {
            return Observable.just(ConfirmPhoneNumberResponse.createFailureResponse())
                    .delay(1, TimeUnit.SECONDS)
                    .flatMap(response -> Observable.error(new WrongSmsCodeError("Use a test code: 1234")));
        } else {
            return Observable.just(ConfirmPhoneNumberResponse.createSuccessResponse())
                    .delay(1, TimeUnit.SECONDS);
        }
    }
}
