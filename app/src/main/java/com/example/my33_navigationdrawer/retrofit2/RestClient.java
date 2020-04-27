package com.example.my33_navigationdrawer.retrofit2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.my33_navigationdrawer.api.RestMethods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class RestClient {

    public static final String BASE_URL ="http://192.168.0.200:8081/bteam/";
    private static Retrofit retrofit = null;
    private static RestMethods restMethods = null;
    private static Gson gson;

    public static RestMethods buildHTTPClient() {

        if(gson== null) {
            gson = new GsonBuilder().setLenient().create();
        }
        if(restMethods== null){
    //TODO Replace with your URL [Must have backslash '/' in end]
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson)) //객체와 JSON의 변환을 자동으로 Gson 처리시
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
        restMethods = retrofit.create(RestMethods.class);
    }

    return restMethods;
}

    //Create OKHttp Client used by retrofit
    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .build();
    }

    //Attach logging intercept to print Logs in LogCat
    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NonNull String message) {
                        Log.d("HTTP", message);
                    }
                });
        httpLoggingInterceptor.setLevel(BODY);
        return httpLoggingInterceptor;
    }
}
