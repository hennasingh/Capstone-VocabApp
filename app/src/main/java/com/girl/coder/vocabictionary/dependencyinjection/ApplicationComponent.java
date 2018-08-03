package com.girl.coder.vocabictionary.dependencyinjection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/*
This is the parent component
src- https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2
 */
@Singleton
@Component(modules = {ApplicationModule.class, DictionaryApiModule.class})
public interface ApplicationComponent {

    // downstream components need these exposed with the return type
    // method name does not really matter
    Application application();

    Retrofit retrofit();
}
