package com.example.administrator.mdwei;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/6/1.
 */
public abstract class BaseActivity extends Activity {
    /**
     * 初始化 View , Data , Listener
     */
    public void init(){
        initView();
        initData();
        initListener();

    }
    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

}
