package com.example.pe_prm_team2.api;

import com.example.pe_prm_team2.model.Dataresponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiservice = new Retrofit.Builder().baseUrl("https://reqres.in/").
            addConverterFactory(GsonConverterFactory.create(gson)).
            build().create(ApiService.class);

    @GET("api/users")
    Call<Dataresponse> getUserApi(@Query("page") int page);
}
