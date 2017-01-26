package me.dmdevgo.rxpm_sample.pm;

/**
 * @author Dmitriy Gorbunov
 */
public interface PmMessageReceiver {
    void onReceive(PmMessage message);
}
