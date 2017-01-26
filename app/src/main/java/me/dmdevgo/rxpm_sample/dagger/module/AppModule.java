package me.dmdevgo.rxpm_sample.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.dmdevgo.rxpm_sample.RxPmSampleApplication;
import me.dmdevgo.rxpm_sample.api.MockServerApi;
import me.dmdevgo.rxpm_sample.api.ServerApi;
import me.dmdevgo.rxpm_sample.model.AuthModel;
import me.dmdevgo.rxpm_sample.model.PhoneUtil;

/**
 * @author Dmitriy Gorbunov
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    static Context provideApplicationContext() {
        return RxPmSampleApplication.getInstance();
    }

    @Provides
    @Singleton
    static PhoneUtil providePhoneUtil() {
        return new PhoneUtil();
    }

    @Provides
    @Singleton
    static ServerApi provideServerApi() {
        return new MockServerApi();
    }

    @Provides
    @Singleton
    static AuthModel provideAuthModel(ServerApi api) {
        return new AuthModel(api);
    }
}
