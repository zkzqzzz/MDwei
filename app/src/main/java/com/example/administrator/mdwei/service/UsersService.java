package com.example.administrator.mdwei.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/31.
 */
public interface UsersService {

    @GET("users/show.json")
    Observable<JsonObject> getUsersShow(@Query("access_token") String token, @Query("uid") long uid);


}
