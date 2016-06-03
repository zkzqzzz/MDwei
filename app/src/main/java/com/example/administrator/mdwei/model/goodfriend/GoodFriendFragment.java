package com.example.administrator.mdwei.model.goodfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.administrator.mdwei.BaseFragment;
import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.bean.GoodFriend;
import com.example.administrator.mdwei.bean.PublicTimeline;
import com.example.administrator.mdwei.service.GoodFriendService;
import com.example.administrator.mdwei.service.HotBlogService;
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
public class GoodFriendFragment extends BaseFragment {

    private PtrClassicFrameLayout prtLay;
    private WListView wlistview;

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
        wlistview = (WListView) view.findViewById(R.id.list_view);
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
        wlistview.setOnLoadMore(new WListView.OnLoadMore() {
            @Override
            public void loadMore() {
                //  loadData(false);
            }
        });
        wlistview.setOnItemClickListener(new OnItemSingleClickListener() {
            @Override
            public void onItemSingleClick(AdapterView<?> parent, View view, int position, long id) {
                if (wlistview.isLockIsLoadingData() && position == wlistview.getCount())
                    return;
                // itemClick(parent, view, position, id);
            }
        });
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
                        Log.i("LOG", "**2222222" + movieEntity);
                    }
                });

    }
}
