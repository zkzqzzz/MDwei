package com.example.administrator.mdwei.service;

import com.example.administrator.mdwei.bean.GoodFriend;
import com.example.administrator.mdwei.bean.PublicTimeline;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface GoodFriendService {


    /**
     * @param map access_token 采用OAuth授权方式为必填参数，
     *            http://open.weibo.com/wiki/2/statuses/friends_timeline
     * @return
     */
    @GET("statuses/friends_timeline.json")
    Observable<GoodFriend> getFriendsTimeline(@QueryMap Map<String, Object> map);

}
