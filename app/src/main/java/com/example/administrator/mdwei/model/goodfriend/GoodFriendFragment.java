package com.example.administrator.mdwei.model.goodfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.mdwei.BaseFragment;
import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.adapter.GoodFriendAdapter;
import com.example.administrator.mdwei.bean.GoodFriend;
import com.example.administrator.mdwei.service.GoodFriendService;
import com.example.administrator.mdwei.util.AccessTokenKeeper;
import com.example.administrator.mdwei.util.URLConstant;
import com.example.administrator.pulltorefresh.PtrClassicFrameLayout;
import com.example.administrator.pulltorefresh.PtrDefaultHandler;
import com.example.administrator.pulltorefresh.PtrFrameLayout;
import com.example.administrator.pulltorefresh.PtrHandler;
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
public class GoodFriendFragment extends BaseFragment {

    private PtrClassicFrameLayout prtLay;
    private RecyclerView mRecyclerView;
    private GoodFriendAdapter  mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goodfriend, null);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        prtLay = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        mAdapter=new GoodFriendAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

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
        //加载更多

    }

    @Override
    public void initData() {
        Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
        String token = mAccessToken.getToken();

        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);


        String baseUrl = URLConstant.BaseUrl;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GoodFriendService goodFriendService = retrofit.create(GoodFriendService.class);

        goodFriendService.getFriendsTimeline(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GoodFriend>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "***************", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("LOG", "" + e);
                    }

                    @Override
                    public void onNext(GoodFriend movieEntity) {
                        mAdapter.setData(movieEntity.getStatuses());
                    }
                });

    }
}
