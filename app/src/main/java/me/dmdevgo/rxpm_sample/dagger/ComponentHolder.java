package me.dmdevgo.rxpm_sample.dagger;

import me.dmdevgo.rxpm_sample.dagger.component.AppComponent;
import me.dmdevgo.rxpm_sample.dagger.component.DaggerAppComponent;
import me.dmdevgo.rxpm_sample.dagger.module.AppModule;

/**
 * @author Dmitriy Gorbunov
 */

public class ComponentHolder {

    private static final ComponentHolder INSTANCE = new ComponentHolder();

    private AppComponent appComponent;

    private ComponentHolder() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public static ComponentHolder getInstance() {
        return INSTANCE;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
