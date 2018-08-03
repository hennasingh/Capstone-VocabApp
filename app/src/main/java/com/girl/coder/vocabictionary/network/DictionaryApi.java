package com.girl.coder.vocabictionary.network;

import com.girl.coder.vocabictionary.model.DictionaryBaseInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {

    @GET("entries/{lang}/{word}")
    Call<DictionaryBaseInfo> getDictionaryResult(@Path("lang") String lang,
                                                 @Path("word") String word);

    @GET("entries/{lang}/{word}/synonyms;antonyms")
    Call<DictionaryBaseInfo> getThesaurusResult(@Path("lang") String lang,
                                                @Path("word") String word);

    @GET("wordlist/{lang}/{filters}")
    Call<DictionaryBaseInfo> getDomainWords(@Path("lang") String lang,
                                            @Path("filters") String filter);
}
