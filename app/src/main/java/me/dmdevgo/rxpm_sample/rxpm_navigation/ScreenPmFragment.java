package me.dmdevgo.rxpm_sample.rxpm_navigation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.dmdevgo.rxpm_sample.pm.PmMessage;
import me.dmdevgo.rxpm_sample.pm.PmMessageReceiver;
import me.dmdevgo.rxpm_sample.rxpm.ErrorHandler;
import me.dmdevgo.rxpm_sample.rxpm.RxPmFragment;

/**
 * @author Dmitriy Gorbunov
 */

public abstract class ScreenPmFragment<PM extends ScreenPm> extends RxPmFragment<PM> implements BackHandler {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onBindPresentationModel(PM pm) {
        subscribe(pm.messages(), this::onReceive);
        subscribe(pm.errors(), this::onError);
    }

    @Override
    public boolean onBackPressed() {
        getPresentationModel().actionBack().call(null);
        return true;
    }

    @Override
    public boolean onUpPressed() {
        getPresentationModel().actionUp().call(null);
        return true;
    }

    @Override
    public void onReceive(PmMessage message) {
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof PmMessageReceiver) {
            ((PmMessageReceiver) parent).onReceive(message);
        } else {

            Activity activity = getActivity();
            if (activity != null && activity instanceof PmMessageReceiver) {
                ((PmMessageReceiver) activity).onReceive(message);
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof ErrorHandler) {
            ((ErrorHandler) parent).onError(throwable);
        } else {

            Activity activity = getActivity();
            if (activity != null && activity instanceof ErrorHandler) {
                ((ErrorHandler) activity).onError(throwable);
            }
        }
    }

    abstract protected @LayoutRes int getFragmentLayout();

}
