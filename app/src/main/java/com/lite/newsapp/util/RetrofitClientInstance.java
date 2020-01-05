package com.lite.newsapp.util;

import com.lite.newsapp.data.network.NewsApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private final static String BASE_URL = "https://newsapi.org";
    private final static String APIKEY = "b46db61165ffb55a2fd7654397f3131f0e7334ddfb3509b3a1c8c9e23f6a3311";
    private static RetrofitClientInstance instance = null;
    private static Retrofit retrofit = null;

    private RetrofitClientInstance() {

    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static NewsApiService getClient() {
        NewsApiService client = getRetrofit().create(NewsApiService.class);
        return client;
    }
}
