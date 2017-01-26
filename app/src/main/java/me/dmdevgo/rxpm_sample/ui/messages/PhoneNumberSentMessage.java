package me.dmdevgo.rxpm_sample.ui.messages;

import me.dmdevgo.rxpm_sample.pm.PmMessage;

/**
 * @author Dmitriy Gorbunov
 */

public class PhoneNumberSentMessage extends PmMessage {

    private final String phoneNumber;

    public PhoneNumberSentMessage(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
