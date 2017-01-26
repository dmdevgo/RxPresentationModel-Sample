package me.dmdevgo.rxpm_sample.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.dagger.ComponentHolder;
import me.dmdevgo.rxpm_sample.model.AuthModel;
import me.dmdevgo.rxpm_sample.pm.PmMessage;
import me.dmdevgo.rxpm_sample.rxpm_navigation.ContainerActivity;
import me.dmdevgo.rxpm_sample.ui.auth.confirm.ConfirmPhoneFragment;
import me.dmdevgo.rxpm_sample.ui.auth.country.ChooseCountryFragment;
import me.dmdevgo.rxpm_sample.ui.auth.country.ChooseCountryPm;
import me.dmdevgo.rxpm_sample.ui.auth.phone.EnterPhoneFragment;
import me.dmdevgo.rxpm_sample.ui.messages.AuthIsSuccessfulMessage;
import me.dmdevgo.rxpm_sample.ui.messages.AuthorizationRequiredMessage;
import me.dmdevgo.rxpm_sample.ui.messages.ChooseCountryMessage;
import me.dmdevgo.rxpm_sample.ui.messages.PhoneNumberSentMessage;
import me.dmdevgo.rxpm_sample.ui.messages.ShowMainScreenMessage;

/**
 * @author Dmitriy Gorbunov
 */

public class MainActivity extends ContainerActivity {

    @Inject
    AuthModel authModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComponentHolder.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected PmMessage getFirstMessage() {
        if (authModel.isAuth()) {
            return new ShowMainScreenMessage();
        } else {
            return new AuthorizationRequiredMessage();
        }
    }

    @Override
    public void onReceive(PmMessage message) {
        if (message instanceof ShowMainScreenMessage
                || message instanceof AuthIsSuccessfulMessage) {

            clearFragmentBackStack();
            showFragment(new MainFragment(), false);

        } else if (message instanceof AuthorizationRequiredMessage) {

            showFragment(new EnterPhoneFragment(), false);

        } else if (message instanceof PhoneNumberSentMessage) {

            String phoneNumber = ((PhoneNumberSentMessage) message).getPhoneNumber();
            showFragment(ConfirmPhoneFragment.newInstance(phoneNumber), true);

        } else if (message instanceof ChooseCountryMessage) {

            showFragment(new ChooseCountryFragment(), true);

        } else if (message instanceof ChooseCountryPm.ChosenCountryMessage) {

            sendMessageToTargetPm(message);
            getSupportFragmentManager().popBackStackImmediate();

        } else {
            super.onReceive(message);
        }
    }

    @Override
    protected int getContainerId() {
        return R.id.container;
    }

    @Override
    protected int getContainerLayout() {
        return R.layout.container;
    }
}
