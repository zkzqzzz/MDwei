package com.example.administrator.mdwei.adapter;


import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.baseadapter.CommonAdapter;
import com.example.administrator.mdwei.baseadapter.ViewHolder;
import com.example.administrator.mdwei.bean.GoodFriend;


/**
 * Created by whl on 16/6/5.
 */
public class GoodFriendAdapter extends CommonAdapter<GoodFriend.StatusesBean> {
    private Activity mActivity;

    public GoodFriendAdapter(Context context, int layoutId) {
        super(context, layoutId);
        this.mActivity =(Activity) context;
    }

    @Override
    public void convert(ViewHolder holder, GoodFriend.StatusesBean statusesBean) {

        ImageView imageView= holder.getView(R.id.iv_good_profile_image_url);
        Glide.with(mActivity)
                .load(statusesBean.getUser().getProfile_image_url())
                .into(imageView);

        holder.setText(R.id.tv_good_name,statusesBean.getUser().getName());
        holder.setText(R.id.tv_good_created_at,statusesBean.getUser().getCreated_at());
        holder.setText(R.id.tv_good_source,statusesBean.getSource());
    }
}
