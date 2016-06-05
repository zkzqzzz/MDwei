package com.example.administrator.pulltorefresh.baseadapter;

import android.content.Context;

import java.util.List;

public abstract class AdapterEnhancedBase<T> extends AdapterQuickBase<T> {
	
	public AdapterEnhancedBase(Context context, int [] layoutResArrays){
		super(context, layoutResArrays);
	}
	
	public AdapterEnhancedBase(Context context, int [] layoutResArrays, List<T> data){
		super(context, layoutResArrays, data);
	}



}
