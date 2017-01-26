package me.dmdevgo.rxpm_sample.api.request;

/**
 * @author Dmitriy Gorbunov
 */

public class ConfirmPhoneNumberRequest {

    private String phoneNumber;
    private String code;

    public ConfirmPhoneNumberRequest(String phoneNumber, String code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCode() {
        return code;
    }
}
