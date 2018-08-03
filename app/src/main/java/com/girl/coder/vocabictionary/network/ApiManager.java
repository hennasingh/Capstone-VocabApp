package com.girl.coder.vocabictionary.network;

import com.girl.coder.vocabictionary.model.DictionaryBaseInfo;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * made using http://codingsonata.com/retrofit-tutorial-android-request-headers/
 */
public class ApiManager {

    private DictionaryApi mApiService;

    @Inject
    public ApiManager(DictionaryApi apiService) {
        mApiService = apiService;
    }


    public void getDictionaryResult(String lang, String word, Callback<DictionaryBaseInfo> callback) {
        Call<DictionaryBaseInfo> resultCall = mApiService.getDictionaryResult(lang, word);
        resultCall.enqueue(callback);
    }

    public void getThesaurusResult(String lang, String word, Callback<DictionaryBaseInfo> callback) {
        Call<DictionaryBaseInfo> resultCall = mApiService.getThesaurusResult(lang, word);
        resultCall.enqueue(callback);
    }

    public void getDomainResults(String lang, String filter, Callback<DictionaryBaseInfo> callback) {
        Call<DictionaryBaseInfo> resultCall = mApiService.getDomainWords(lang, filter);
        resultCall.enqueue(callback);
    }
}
