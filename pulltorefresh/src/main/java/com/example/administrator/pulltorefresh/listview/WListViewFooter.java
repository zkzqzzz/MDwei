/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.example.administrator.pulltorefresh.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.pulltorefresh.R;


public class WListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_NO_MORE = 3;
    private View progressBarTip;
    private TextView loadMoreHintView;
    private TextView noMoreHintView;

    public WListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public WListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public void setState(int state) {
        switch (state) {
            case 1:
                normal();
                break;
            case 2:
                loading();
                break;
            case 3:
                noMore();
                break;
        }
    }

    private void normal() {
        loadMoreHintView.setVisibility(View.VISIBLE);
        progressBarTip.setVisibility(View.GONE);
        noMoreHintView.setVisibility(View.GONE);
    }

    private void loading() {
        loadMoreHintView.setVisibility(View.GONE);
        progressBarTip.setVisibility(View.VISIBLE);
        noMoreHintView.setVisibility(View.GONE);
    }

    private void noMore() {
        loadMoreHintView.setVisibility(View.GONE);
        progressBarTip.setVisibility(View.GONE);
        noMoreHintView.setVisibility(View.VISIBLE);
    }


    private void initView(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myInflater.inflate(R.layout.wlistview_footer, this);
        progressBarTip = findViewById(R.id.wlistview_footer_progressbar_layout);
        loadMoreHintView = (TextView) findViewById(R.id.wlistview_footer_hint_load_more);
        noMoreHintView = (TextView) findViewById(R.id.wlistview_footer_hint_no_more);
    }
}
