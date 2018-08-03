package com.girl.coder.vocabictionary.dependencyinjection;

import android.app.Application;

import com.girl.coder.vocabictionary.MainApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * to signal to Dagger to search within the available methods for
 * possible instance providers
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(MainApplication application) {
        mApplication = application;
    }

    /**
     * informs dagger that this method is in charge of providing
     * the instance of the Application class
     *
     * @return
     */
    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }
}
