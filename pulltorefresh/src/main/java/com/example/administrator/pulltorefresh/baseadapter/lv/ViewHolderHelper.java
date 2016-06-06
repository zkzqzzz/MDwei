package com.example.administrator.pulltorefresh.baseadapter.lv;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * ViewHolder pattern for ListView adapter. It's support several types of view
 * styles.
 * 
 * 
 * @author aliouswang
 * 
 */
public class ViewHolderHelper {
	private SparseArray<View> mViews;
	private SparseArray<View> mconvertViews;
	private Context mContext;
	private int position;
	private View mConvertView;
	public int layoutId;

	private ViewHolderHelper(Context context, ViewGroup parent, int layoutId, int position) {
		this.mContext = context;
		this.position = position;
		this.mViews = new SparseArray<View>();
		this.mconvertViews = new SparseArray<View>();
		this.mConvertView = getConvertView(context, parent, layoutId, position);
		this.layoutId = layoutId;
		mConvertView.setTag(this);
	}

	public static ViewHolderHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolderHelper(context, parent, layoutId, position);
		}
		ViewHolderHelper existingHelper = (ViewHolderHelper) convertView.getTag();
		existingHelper.position = position;
		return existingHelper;
	}

	public <T extends View> T retrieveView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	private <T extends View> T getConvertView(Context context, ViewGroup parent, int layoutId, int position) {
		View view = mconvertViews.get(layoutId);
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
			mconvertViews.put(layoutId, view);
		}
		return (T) view;
	}

	public ViewHolderHelper setText(int viewId, String value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	public ViewHolderHelper setImageViewVisible(int viewId) {
		ImageView view = retrieveView(viewId);
		view.setVisibility(View.VISIBLE);
		return this;
	}

	public ViewHolderHelper setMyTextViewVisible(int viewId) {
		TextView view = retrieveView(viewId);
		view.setVisibility(View.VISIBLE);
		return this;
	}
	public ViewHolderHelper setMyTextViewGONE(int viewId) {
		TextView view = retrieveView(viewId);
		view.setVisibility(View.GONE);
		return this;
	}

	public ViewHolderHelper setTextColor(int viewId, int colorId) {
		TextView view = retrieveView(viewId);
		view.setTextColor(mContext.getResources().getColor(colorId));
		return this;
	}

	public ViewHolderHelper setChecked(int viewId, boolean bCheck) {
		CheckBox checkBox = retrieveView(viewId);
		checkBox.setChecked(bCheck);
		return this;
	}

	public ViewHolderHelper setCheckedListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
		CheckBox checkBox = retrieveView(viewId);
		checkBox.setOnCheckedChangeListener(listener);
		return this;
	}

	public ViewHolderHelper setImageFromUrl(int viewId, String url) {
		ImageView imageView = retrieveView(viewId);
		Glide.with(mContext)
				.load(url)
				.into(imageView);
		return this;
	}

	public ViewHolderHelper setImageFromUrl(int viewId, String url, int defaultImageId) {
		ImageView imageView = retrieveView(viewId);

		return this;
	}

	public ViewHolderHelper setImageResId(int viewId, int resId) {
		ImageView imageView = retrieveView(viewId);
		imageView.setImageResource(resId);
		return this;
	}

	public ViewHolderHelper setBackgroundResId(int viewId, int resId) {
		View view = retrieveView(viewId);
		view.setBackgroundResource(resId);
		return this;
	}

	public ViewHolderHelper setViewSize(int viewId, int width, int height) {
		View view = retrieveView(viewId);
		RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view.getLayoutParams();
		rlp.width = width;
		rlp.height = height;
		view.setLayoutParams(rlp);
		return this;
	}

	public ViewHolderHelper setClickListener(int viewId, View.OnClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolderHelper setVisiable(int viewId, int visiable) {
		View view = retrieveView(viewId);
		view.setVisibility(visiable);
		return this;
	}

	public ViewHolderHelper setClickable(int viewId, boolean bClickable) {
		View view = retrieveView(viewId);
		view.setClickable(bClickable);
		view.setFocusable(bClickable);
		return this;
	}

	public View getView() {
		return this.mConvertView;
	}

	public int getPosition() {
		return this.position;
	}


}
