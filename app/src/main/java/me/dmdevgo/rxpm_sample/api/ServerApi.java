package me.dmdevgo.rxpm_sample.api;

import me.dmdevgo.rxpm_sample.api.request.AuthPhoneNumberRequest;
import me.dmdevgo.rxpm_sample.api.request.ConfirmPhoneNumberRequest;
import me.dmdevgo.rxpm_sample.api.response.AuthPhoneNumberResponse;
import me.dmdevgo.rxpm_sample.api.response.ConfirmPhoneNumberResponse;

import rx.Observable;

/**
 * @author Dmitriy Gorbunov
 */

public interface ServerApi {

    Observable<AuthPhoneNumberResponse> authPhoneNumber(AuthPhoneNumberRequest request);

    Observable<ConfirmPhoneNumberResponse> confirmPhoneNumber(ConfirmPhoneNumberRequest request);
}
