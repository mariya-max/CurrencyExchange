package com.example.currencyExchange.Api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {

    @GET("daily_json.js")
    Call<String> getValuateList();
}
