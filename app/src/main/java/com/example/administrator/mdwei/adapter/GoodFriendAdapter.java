package com.example.administrator.mdwei.adapter;


import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.base.adapter.MultiItemCommonAdapter;
import com.example.administrator.mdwei.base.adapter.MultiItemTypeSupport;
import com.example.administrator.mdwei.base.adapter.ViewHolder;
import com.example.administrator.mdwei.bean.GoodFriend;

import java.util.List;


/**
 * Created by whl on 16/6/5.
 */
public class GoodFriendAdapter extends MultiItemCommonAdapter<GoodFriend.StatusesBean> {
    private Activity mActivity;

    public GoodFriendAdapter(Context context, List<GoodFriend.StatusesBean> datas) {

        super(context, datas, new MultiItemTypeSupport<GoodFriend.StatusesBean>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 0) {
                    return R.layout.fragment_goodfriend_listview_item_one;
                } else
                    return R.layout.fragment_goodfriend_listview_item_one;
            }

            @Override
            public int getItemViewType(int position, GoodFriend.StatusesBean statusesBean) {

                if (statusesBean.getRetweeted_status() != null) {

                    return 0;
                }
                return 1;
            }
        });


        this.mActivity=(Activity) context;
    }

    @Override
    public void convert(ViewHolder holder, GoodFriend.StatusesBean statusesBean) {
        ImageView imageView = holder.getView(R.id.iv_good_profile_image_url);
        Glide.with(mActivity)
                .load(statusesBean.getUser().getProfile_image_url())
                .into(imageView);

        holder.setText(R.id.tv_good_name, statusesBean.getUser().getName());
        holder.setText(R.id.tv_good_created_at, statusesBean.getUser().getCreated_at());
        holder.setText(R.id.tv_good_source, statusesBean.getSource());
        holder.setText(R.id.tv_good_text, statusesBean.getText());
    }
}

