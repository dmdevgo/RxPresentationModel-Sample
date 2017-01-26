package me.dmdevgo.rxpm_sample.api.response;

import java.util.UUID;

/**
 * @author Dmitriy Gorbunov
 */

public class ConfirmPhoneNumberResponse {

    private final boolean success;
    private final String session;

    public ConfirmPhoneNumberResponse(boolean success) {
        this.success = success;
        if (success) {
            session = UUID.randomUUID().toString();
        } else {
            session = null;
        }
    }

    public static ConfirmPhoneNumberResponse createSuccessResponse() {
        return new ConfirmPhoneNumberResponse(true);
    }

    public static ConfirmPhoneNumberResponse createFailureResponse() {
        return new ConfirmPhoneNumberResponse(false);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSession() {
        return session;
    }
}
