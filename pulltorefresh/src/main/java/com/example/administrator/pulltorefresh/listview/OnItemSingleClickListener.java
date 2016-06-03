package com.example.administrator.pulltorefresh.listview;

import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2016/6/3.
 */
public abstract class OnItemSingleClickListener implements AdapterView.OnItemClickListener {

    /**
     * 最短click事件的时间间隔
     */
    private static final long MIN_CLICK_INTERVAL = 1000;
    /**
     * 上次click的时间
     */
    private long mLastClickTime;

    /**
     * item click响应函数
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public abstract void onItemSingleClick(AdapterView<?> parent, View view, int position, long id);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        //有可能2次连击，也有可能3连击，保证mLastClickTime记录的总是上次click的时间
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;
        onItemSingleClick(parent, view, position, id);
    }
}
