package me.dmdevgo.rxpm_sample.api.errors;

/**
 * @author Dmitriy Gorbunov
 */

public class WrongSmsCodeError extends Exception {

    public WrongSmsCodeError(String message) {
        super(message);
    }
}
