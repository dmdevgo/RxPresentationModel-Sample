package me.dmdevgo.rxpm_sample.api.request;

/**
 * @author Dmitriy Gorbunov
 */

public class AuthPhoneNumberRequest {

    private final String phoneNumber;

    public AuthPhoneNumberRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
