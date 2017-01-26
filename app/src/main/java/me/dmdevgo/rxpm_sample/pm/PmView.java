package me.dmdevgo.rxpm_sample.pm;

/**
 * @author Dmitriy Gorbunov
 */
public interface PmView<PM extends PresentationModel> extends PmProvider<PM> {
    void onBindPresentationModel(PM pm);
    void onUnbindPresentationModel(PM pm);
}
