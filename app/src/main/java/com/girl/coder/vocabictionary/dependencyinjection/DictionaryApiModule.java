package com.girl.coder.vocabictionary.dependencyinjection;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DictionaryApiModule {

    private final String BASE_URL;
    private final String APP_ID;
    private final String APP_KEY;

    public DictionaryApiModule(String url, String appId, String appKey) {
        BASE_URL = url;
        APP_ID = appId;
        APP_KEY = appKey;
    }

    /**
     * The methods that will actually expose available return types should also be annotated with the
     *
     * @Provides annotation. The @Singleton annotation also
     * signals to the Dagger compiler that the instance should be created only once in the application.
     */

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpInterceptor) {
        return new OkHttpClient().newBuilder().addInterceptor(httpInterceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("app_id", APP_ID)
                        .addHeader("app_key", APP_KEY)
                        .build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }


}
