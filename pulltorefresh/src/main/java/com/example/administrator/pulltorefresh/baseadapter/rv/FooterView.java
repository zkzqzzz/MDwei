/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.example.administrator.pulltorefresh.baseadapter.rv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.pulltorefresh.R;


public class FooterView extends ViewGroup {
    public final static int STATE_NORMAL = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_NO_MORE = 3;
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private TextView mHintText;

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater myInflater = LayoutInflater.from(context);
        myInflater.inflate(R.layout.view_footer, this, true);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_footer);
        mLoadingText = (TextView) findViewById(R.id.text_footer_loading);
        mHintText = (TextView) findViewById(R.id.text_footer_hint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthUsed = 0;
        int heightUsed = 0;
        measureChildWithMargins(mProgressBar, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
        widthUsed += MeasureUtil.getMeasuredWidthWithMargins(mProgressBar);
        measureChildWithMargins(mLoadingText, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

        measureChildWithMargins(mHintText, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
        heightUsed = MeasureUtil.getMeasuredHeightWithMargins(mHintText);
        int heightSize = heightUsed + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();

        int currentLeft = paddingLeft;
        MeasureUtil.layoutView(mHintText, currentLeft, paddingTop, mHintText.getMeasuredWidth(), mHintText.getMeasuredHeight());

        int width = r - l;
        int loadingWidth = mProgressBar.getMeasuredWidth() + MeasureUtil.getMeasuredWidthWithMargins(mLoadingText);

        currentLeft += (width - loadingWidth) / 2;
        MeasureUtil.layoutView(mProgressBar, currentLeft, paddingTop, mProgressBar.getMeasuredWidth(), mProgressBar.getMeasuredHeight());
        currentLeft += MeasureUtil.getMeasuredWidthWithMargins(mProgressBar);
        MeasureUtil.layoutView(mLoadingText, currentLeft, paddingTop, mLoadingText.getMeasuredWidth(), mLoadingText.getMeasuredHeight());

    }
    //必须加 否则java.lang.ClassCastException: android.view.ViewGroup$LayoutParams without LayoutParams usage
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
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
        mHintText.setText("查看更多");
        mHintText.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        mLoadingText.setVisibility(INVISIBLE);
    }

    private void loading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingText.setVisibility(View.VISIBLE);
        mHintText.setVisibility(View.INVISIBLE);
    }

    private void noMore() {
        mProgressBar.setVisibility(View.GONE);
        mLoadingText.setVisibility(View.GONE);
        mHintText.setText("没有更多内容了...");
        mHintText.setVisibility(View.VISIBLE);
    }

}
