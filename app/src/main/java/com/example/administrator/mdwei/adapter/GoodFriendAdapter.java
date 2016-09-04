package com.example.administrator.mdwei.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.mdwei.R;
import com.example.administrator.mdwei.base.adapter.MultiItemCommonAdapter;
import com.example.administrator.mdwei.base.adapter.MultiItemTypeSupport;
import com.example.administrator.mdwei.base.adapter.ViewHolder;
import com.example.administrator.mdwei.bean.GoodFriend;
import com.example.administrator.mdwei.bean.Users;
import com.example.administrator.mdwei.view.ImageViewViewGroup;

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

                //转发
                if (statusesBean.getRetweeted_status() != null) {
                    return 0;
                }
                //原创
                return 0;
            }
        });


        this.mActivity = (Activity) context;
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


        TextView originalText = holder.getView(R.id.tv_good_original_text);
        ImageViewViewGroup originalViewGroup = holder.getView(R.id.vg_good_original);

        TextView reprintText = holder.getView(R.id.tv_good_reprint_text);
        ImageViewViewGroup reprintViewGroup = holder.getView(R.id.vg_good_reprint);

        if (statusesBean.getText() != null) {
            originalText.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_good_original_text, statusesBean.getText());
        } else {
            holder.setText(R.id.tv_good_original_text, "");
            originalText.setVisibility(View.GONE);
        }

        if (statusesBean.getPic_urls() != null && statusesBean.getPic_urls().size() != 0) {
            if (originalViewGroup.getChildCount() > 0) {
                originalViewGroup.removeAllViews();
            }
            originalViewGroup.setVisibility(View.VISIBLE);
            for (int i = 0; i < statusesBean.getPic_urls().size(); i++) {

                ImageView view = new ImageView(mActivity);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);

                view.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                Glide.with(mActivity).load(statusesBean.getPic_urls().get(i).getThumbnail_pic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
                originalViewGroup.addView(view);
            }
        } else {
            originalViewGroup.removeAllViews();
            originalViewGroup.setVisibility(View.GONE);
        }


        if (statusesBean.getRetweeted_status() != null && statusesBean.getRetweeted_status().getText() != null) {
            reprintText.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_good_reprint_text, statusesBean.getRetweeted_status().getText());
        } else {
            holder.setText(R.id.tv_good_reprint_text, "");
            reprintText.setVisibility(View.GONE);
        }


        if (statusesBean.getRetweeted_status() != null && statusesBean.getRetweeted_status().getPic_urls() != null && statusesBean.getRetweeted_status().getPic_urls().size() != 0) {

            if (reprintViewGroup.getChildCount() > 0) {
                reprintViewGroup.removeAllViews();
            }
            reprintViewGroup.setVisibility(View.VISIBLE);
            for (int i = 0; i < statusesBean.getRetweeted_status().getPic_urls().size(); i++) {
                ImageView view = new ImageView(mActivity);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                Glide.with(mActivity).load(statusesBean.getRetweeted_status().getPic_urls().get(i).getThumbnail_pic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
                reprintViewGroup.addView(view);
            }
        } else {
            reprintViewGroup.removeAllViews();
            reprintViewGroup.setVisibility(View.GONE);
        }

        holder.setText(R.id.tv_good_reposts_count, statusesBean.getReposts_count() + "");
        holder.setText(R.id.tv_good_comments_count, statusesBean.getComments_count() + "");
        holder.setText(R.id.tv_good_attitudes_count, statusesBean.getAttitudes_count() + "");


        if (statusesBean.getLiked()) {
            holder.setImageResource(R.id.iv_good_linked, R.mipmap.timeline_trend_icon_like);
        } else {
            holder.setImageResource(R.id.iv_good_linked, R.mipmap.timeline_topic_icon_like);
        }

    }
}

