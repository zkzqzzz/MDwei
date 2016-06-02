package com.example.administrator.mdwei.base.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AdapterBase <T, H extends ViewHolderHelper> extends BaseAdapter {
	
	protected final Context mContext;
	protected List<T> mData;
	protected final int [] mLayoutResArrays;
	
	public AdapterBase(Context context, int [] layoutResArrays){
		this(context, layoutResArrays, null);
	}
	
	public AdapterBase(Context context, int [] layoutResArrays, List<T> data){
		this.mData = data == null ? new ArrayList<T>() : data;
		this.mContext = context;
		this.mLayoutResArrays = layoutResArrays;
	}
	
	public void setData(List<T> data){
		this.mData = data;
		this.notifyDataSetChanged();
	}

	public void clear(){
		this.mData.clear();
		this.notifyDataSetChanged();
	}
    /**
     * 滑动删除一条数据，注意index
     * @param index
     */
    public void removeSwipeData(int index) {
        if (this.getCount() >= index) {
            this.mData.remove(index - 1);
            this.notifyDataSetChanged();
        }
    }

    public void addData(T data) {
        Log.e("event", mData.size() + "");
        this.mData.add(data);
        this.notifyDataSetChanged();
        Log.e("event", mData.size()+ "");
    }
	
	public void addData(List<T> data){
		if(data != null){
			this.mData.addAll(data);
		}
		
		this.notifyDataSetChanged();
	}

    public List<T> getAllData() {
        return this.mData;
    }

    public int getDataSize() {
        if(this.mData == null){
            return 0;
        }
        return this.mData.size();
    }

	
	@Override
	public int getCount() {
		if(this.mData == null){
			return 0;
		}
		return this.mData.size();
	}
	
	@Override
	public T getItem(int position) {
		if(position > this.mData.size()){
			return null;
		}
		return mData.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		return this.mLayoutResArrays.length;
	}
	
	/**
	 * You should always override this method,to return the 
	 * correct view type for every cell.
	 * 
	 */
	public int getItemViewType(int position){
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final H helper = getAdapterHelper(position, convertView, parent);
		T item = getItem(position);
		convert(helper, item);
		return helper.getView();
	}
	
	protected abstract void convert(H helper, T item);
	protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);
	
}
