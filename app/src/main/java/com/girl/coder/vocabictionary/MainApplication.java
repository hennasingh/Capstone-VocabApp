package com.girl.coder.vocabictionary;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.girl.coder.vocabictionary.dependencyinjection.ApplicationComponent;
import com.girl.coder.vocabictionary.dependencyinjection.ApplicationModule;
import com.girl.coder.vocabictionary.dependencyinjection.DaggerApplicationComponent;
import com.girl.coder.vocabictionary.dependencyinjection.DictionaryApiModule;
import com.girl.coder.vocabictionary.dependencyinjection.RoomModule;
import com.girl.coder.vocabictionary.dependencyinjection.components.DaggerOxfordComponent;
import com.girl.coder.vocabictionary.dependencyinjection.components.OxfordComponent;
import com.girl.coder.vocabictionary.dependencyinjection.modules.OxfordModule;
import com.girl.coder.vocabictionary.network.repository.Utilities;

public class MainApplication extends Application {

    private ApplicationComponent mApplicationComponent;
    private OxfordComponent mOxfordComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //Stetho
        //Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        //dagger Component Name
        mApplicationComponent = DaggerApplicationComponent.builder()
                //list of modules that are part of this component need to be created here too
                .applicationModule(new ApplicationModule(this))
                .dictionaryApiModule(new DictionaryApiModule(Utilities.BASE_URL, Utilities.APP_ID,
                        Utilities.APP_KEY))
                .build();

        mOxfordComponent = DaggerOxfordComponent.builder()
                .applicationComponent(mApplicationComponent)
                .roomModule(new RoomModule(this))
                .oxfordModule(new OxfordModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public OxfordComponent getOxfordComponent() {
        return mOxfordComponent;
    }
}
