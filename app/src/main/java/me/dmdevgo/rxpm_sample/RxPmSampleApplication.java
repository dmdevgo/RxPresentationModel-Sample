package me.dmdevgo.rxpm_sample;

import android.app.Application;

import timber.log.Timber;

/**
 * @author Dmitriy Gorbunov
 */
public class RxPmSampleApplication extends Application {

    private static RxPmSampleApplication INSTANCE;

    public static RxPmSampleApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initLogger();
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
