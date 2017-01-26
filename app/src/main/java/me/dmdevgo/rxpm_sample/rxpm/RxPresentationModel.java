package me.dmdevgo.rxpm_sample.rxpm;

import me.dmdevgo.rxpm_sample.pm.PresentationModel;
import me.dmdevgo.rxpm_sample.pm.PmMessage;
import com.jakewharton.rxrelay.PublishRelay;

import rx.Observable;
import rx.functions.Action1;

/**
 * @author Dmitriy Gorbunov
 */
public abstract class RxPresentationModel extends PresentationModel {

    public static final PmMessage EMPTY = new PmMessage();

    protected final PublishRelay<PmMessage> messagesFromPm = PublishRelay.create();
    protected final PublishRelay<Throwable> errorRelay = PublishRelay.create();
    private final PublishRelay<PmMessage> messagesToPm = PublishRelay.create();

    public Observable<PmMessage> messages() {
        return messagesFromPm.filter(message -> message != EMPTY);
    }

    public Observable<Throwable> errors() {
        return errorRelay;
    }

    public Action1<PmMessage> actionMessage() {
        return messagesToPm;
    }

    protected Observable<PmMessage> receiveMessages() {
        return messagesToPm.filter(message -> message != EMPTY);
    }

}
