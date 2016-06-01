package com.example.administrator.mdwei;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Administrator on 2016/6/1.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 初始化 View , Data , Listener
     */
    public void init(View view){
        initView(view);
        initData();
        initListener();

    }
    public abstract void initView(View view);

    public abstract void initListener();

    public abstract void initData();

}
