package me.dmdevgo.rxpm_sample.api.response;

/**
 * @author Dmitriy Gorbunov
 */

public class AuthPhoneNumberResponse {

    private final String phoneNumber;
    private final boolean success;

    public AuthPhoneNumberResponse(String phoneNumber, boolean success) {
        this.phoneNumber = phoneNumber;
        this.success = success;
    }

    public static AuthPhoneNumberResponse createSuccessResponse(String phoneNumber) {
        return new AuthPhoneNumberResponse(phoneNumber, true);
    }

    public static AuthPhoneNumberResponse createFailureResponse(String phoneNumber) {
        return new AuthPhoneNumberResponse(phoneNumber, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
