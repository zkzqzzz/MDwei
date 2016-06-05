package com.example.administrator.mdwei.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.baseadapter.BaseRecyclerAdapter;
import com.example.administrator.mdwei.bean.GoodFriend;
import com.example.administrator.mdwei.adapter.GoodFriendAdapter.GoodFriendViewHolder;

/**
 * Created by whl on 16/6/5.
 */
public class GoodFriendAdapter extends BaseRecyclerAdapter<GoodFriend.StatusesBean, GoodFriendViewHolder> {
    private Activity mActivity;

    public GoodFriendAdapter(Context context) {
        super(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public GoodFriendViewHolder onCreateItemVH(ViewGroup parent) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_goodfriend_listview_item, parent, false);
        return new GoodFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodFriendViewHolder holder, int position, GoodFriend.StatusesBean object) {

    }

    public class GoodFriendViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

        public GoodFriendViewHolder(View itemView) {
            super(itemView);
        }
    }
}
