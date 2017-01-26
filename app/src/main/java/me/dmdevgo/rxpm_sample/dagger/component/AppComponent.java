package me.dmdevgo.rxpm_sample.dagger.component;

import me.dmdevgo.rxpm_sample.dagger.module.AppModule;
import me.dmdevgo.rxpm_sample.RxPmSampleApplication;
import me.dmdevgo.rxpm_sample.ui.auth.confirm.ConfirmPhonePm;
import me.dmdevgo.rxpm_sample.ui.auth.country.ChooseCountryPm;
import me.dmdevgo.rxpm_sample.ui.auth.phone.EnterPhonePm;
import me.dmdevgo.rxpm_sample.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Dmitriy Gorbunov
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(RxPmSampleApplication application);
    void inject(EnterPhonePm pm);
    void inject(ChooseCountryPm pm);
    void inject(ConfirmPhonePm pm);
    void inject(MainActivity activity);
}
