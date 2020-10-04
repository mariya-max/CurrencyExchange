package com.example.currencyExchange.Api;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api extends Application {

    private static DataApi dataApi;

    @Override
    public void onCreate() {
        super.onCreate();
        String BASE_URL = "https://www.cbr-xml-daily.ru/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        dataApi = retrofit.create(DataApi.class);
    }

    public static DataApi getDataApi() {
        return dataApi;
    }
}
