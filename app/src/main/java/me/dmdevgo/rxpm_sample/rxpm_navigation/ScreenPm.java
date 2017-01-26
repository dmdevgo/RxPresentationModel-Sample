package me.dmdevgo.rxpm_sample.rxpm_navigation;

import me.dmdevgo.rxpm_sample.pm.PmMessage;
import me.dmdevgo.rxpm_sample.rxpm.RxPresentationModel;

import rx.functions.Action1;

/**
 * @author Dmitriy Gorbunov
 */

public abstract class ScreenPm extends RxPresentationModel {

    public static final PmMessage UP = new PmMessage();
    public static final PmMessage BACK = new PmMessage();

    public Action1<Void> actionBack() {
        return aVoid -> messagesFromPm.call(BACK);
    }

    public Action1<Void> actionUp() {
        return aVoid -> messagesFromPm.call(UP);
    }

}
