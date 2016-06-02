package com.example.administrator.mdwei.service;

import com.google.gson.JsonObject;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface HotBlogService {

    /**
     * @param map access_token 采用OAuth授权方式为必填参数，
     *            OAuth 授权后获得。
     *            count 单页返回的记录条数，默认为50。
     *            page 返回结果的页码，默认为1。
     *            base_app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @return
     */
    @GET("statuses/public_timeline")
    Observable<JsonObject> getPublicTimeline(@QueryMap Map<String, Object> map);


}