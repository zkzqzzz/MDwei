package com.example.administrator.mdwei.service;

import com.example.administrator.mdwei.bean.Users;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/31.
 */
public interface UsersService {
    /**
     * 获取用户信息
     *
     * @param token 授权返回的token
     * @param uid   授权返回的uid
     * @return
     */
    @GET("users/show.json")
    Observable<Users> getUsersShow(@Query("access_token") String token, @Query("uid") long uid);




}
