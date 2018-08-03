package com.girl.coder.vocabictionary.dependencyinjection.modules;

import android.arch.lifecycle.ViewModelProvider;

import com.girl.coder.vocabictionary.database.WordListRepository;
import com.girl.coder.vocabictionary.dependencyinjection.scope.UserScope;
import com.girl.coder.vocabictionary.network.ApiManager;
import com.girl.coder.vocabictionary.network.DictionaryApi;
import com.girl.coder.vocabictionary.network.repository.WebRepository;
import com.girl.coder.vocabictionary.viewmodel.CustomViewModelFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class OxfordModule {

    @Provides
    @UserScope// needs to be consistent with the component scope
    public DictionaryApi provideApiService(Retrofit retrofit) {
        return retrofit.create(DictionaryApi.class);
    }

    @Provides
    @UserScope
    public ApiManager provideApiManager(DictionaryApi apiService) {
        return new ApiManager(apiService);
    }


    @Provides
    @UserScope
    ViewModelProvider.Factory provideViewModelFactory(WordListRepository localRepo, WebRepository webRepo) {
        return new CustomViewModelFactory(localRepo, webRepo);
    }

    @Provides
    @UserScope
    WebRepository provideWebRepo(ApiManager apiManager) {
        return new WebRepository(apiManager);
    }

}
