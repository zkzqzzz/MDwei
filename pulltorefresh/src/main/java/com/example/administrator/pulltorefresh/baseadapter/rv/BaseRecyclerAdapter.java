package com.example.administrator.pulltorefresh.baseadapter.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyp on 15/8/10.
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerAdapter.BaseViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Adapter<T> {
    protected List<T> mData = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    protected View mFooterView;
    protected Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private View.OnClickListener mFooterViewClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setFooterViewListener(View.OnClickListener listener) {
        mFooterViewClickListener = listener;
    }

    @Override
    public void setData(List<T> data, boolean isRefresh) {
        if (isRefresh) {
            this.mData.clear();
        }
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void setData(List<T> data) {
        int lastIndex = mData.size();
        this.mData.addAll(data);
        notifyItemRangeInserted(lastIndex, data.size());
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View v = mFooterView;
            if (mFooterViewClickListener != null) {
                mFooterView.setOnClickListener(mFooterViewClickListener);
            }
            return new LoadMoreVH(v);
        }
        RecyclerView.ViewHolder viewHolder = onCreateItemVH(parent);
        if (viewHolder instanceof BaseViewHolder && mOnItemClickListener != null) {
            ((BaseViewHolder) viewHolder).setItemClick(mOnItemClickListener);
        }
        return viewHolder;
    }

    public abstract VH onCreateItemVH(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreVH) {
            return;
        }
        onBindViewHolder((VH) holder, position, getItem(position));
    }

    public abstract void onBindViewHolder(VH holder, int position, T object);

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && getFooterViewSize() == 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public int getFooterViewSize() {
        return mFooterView == null ? 0 : 1;
    }

    public void addFooter(View footer) {
        mFooterView = footer;
        notifyItemInserted(mData.size());
    }

    public void removeFooter() {
        notifyItemRemoved(mData.size());
        mFooterView = null;
    }

    protected boolean isShowFooterViewIfNone() {
        return false;
    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0) {
            if (isShowFooterViewIfNone()) {
                return 1;
            } else {
                return 0;
            }
        }
        return mData.size() + getFooterViewSize();
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnItemClickListener mOnItemClickListener;

        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setItemClick(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (v == itemView && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

    }

    public static class LoadMoreVH extends RecyclerView.ViewHolder {

        public LoadMoreVH(View itemView) {
            super(itemView);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
