package me.dmdevgo.rxpm_sample.model;

import me.dmdevgo.rxpm_sample.api.ServerApi;
import me.dmdevgo.rxpm_sample.api.request.AuthPhoneNumberRequest;
import me.dmdevgo.rxpm_sample.api.request.ConfirmPhoneNumberRequest;
import me.dmdevgo.rxpm_sample.api.response.AuthPhoneNumberResponse;
import me.dmdevgo.rxpm_sample.api.response.ConfirmPhoneNumberResponse;

import rx.Observable;

/**
 * @author Dmitriy Gorbunov
 */

public class AuthModel {

    private final ServerApi api;
    private String session;

    public AuthModel(ServerApi api) {
        this.api = api;
    }

    public boolean isAuth() {
        return session != null;
    }

    public Observable<AuthPhoneNumberResponse> authPhoneNumber(String phoneNumber) {
        return api.authPhoneNumber(new AuthPhoneNumberRequest(phoneNumber));
    }

    public Observable<ConfirmPhoneNumberResponse> confirmPhoneNumber(String phoneNumber, String smsCode) {
        return api.confirmPhoneNumber(new ConfirmPhoneNumberRequest(phoneNumber, smsCode))
                .doOnNext(response -> {
                    if (response.isSuccess()) {
                        session = response.getSession();
                    }
                });
    }

}
