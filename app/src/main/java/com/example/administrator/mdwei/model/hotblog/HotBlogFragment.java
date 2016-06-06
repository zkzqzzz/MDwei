package com.example.administrator.mdwei.model.hotblog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mdwei.BaseFragment;
import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.bean.PublicTimeline;
import com.example.administrator.mdwei.bean.Users;
import com.example.administrator.mdwei.service.HotBlogService;
import com.example.administrator.mdwei.service.UsersService;
import com.example.administrator.mdwei.util.AccessTokenKeeper;
import com.example.administrator.mdwei.util.URLConstant;
import com.example.administrator.pulltorefresh.PtrClassicFrameLayout;
import com.example.administrator.pulltorefresh.PtrDefaultHandler;
import com.example.administrator.pulltorefresh.PtrFrameLayout;
import com.example.administrator.pulltorefresh.PtrHandler;
import com.example.administrator.pulltorefresh.listview.OnItemSingleClickListener;
import com.example.administrator.pulltorefresh.listview.WListView;
import com.google.gson.JsonObject;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/1.
 */
public class HotBlogFragment extends BaseFragment {

    private PtrClassicFrameLayout prtLay;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hotblog, null);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        prtLay = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
    }

    @Override
    public void initListener() {
        prtLay.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                prtLay.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

    }

    @Override
    public void initData() {
        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
        String token = mAccessToken.getToken();

        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("count", 50);
        map.put("page", 1);
        map.put("base_app", 0);

        String baseUrl = URLConstant.BaseUrl;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        HotBlogService hotBlogService = retrofit.create(HotBlogService.class);

        hotBlogService.getPublicTimeline(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublicTimeline>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LOG", "" + e);
                    }

                    @Override
                    public void onNext(PublicTimeline movieEntity) {
                        Log.i("LOG", "" + movieEntity);
                    }
                });
    }

}
