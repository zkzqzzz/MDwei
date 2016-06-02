package com.example.administrator.pulltorefresh.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;


public class WListView extends ListView implements OnScrollListener {

    private WListViewFooter mFooterView;
    private int lastVisibleIndex;
    private OnLoadMore callback = null;

    private boolean isEndLoadMoreData = false;
    private boolean lockIsLoadingData = false;

    public WListView(Context context) {
        this(context, null);
    }

    public WListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        setCacheColorHint(0);
        mFooterView = new WListViewFooter(context);
        addFooterView(mFooterView);
        setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        lastVisibleIndex = firstVisibleItem + visibleItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (!isEndLoadMoreData && !lockIsLoadingData) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastVisibleIndex == getAdapter().getCount()) {
                mFooterView.setState(WListViewFooter.STATE_LOADING);
                if (callback != null) {
                    callback.loadMore();
                }
            }
        }
    }

    public void setStateDataIsLoading(boolean isloading) {
        lockIsLoadingData = isloading;
    }

    public void setOnLoadMore(OnLoadMore l) {
        callback = l;
    }

    public void setStateLoadComplete() {
        mFooterView.setState(WListViewFooter.STATE_NORMAL);
    }

    public void setStateNoMoreDataToBeLoaded() {
        isEndLoadMoreData = true;
        mFooterView.setState(WListViewFooter.STATE_NO_MORE);
        removeFooterView(mFooterView);
    }

    public void resetListStatus() {
        isEndLoadMoreData = false;
        mFooterView.setState(WListViewFooter.STATE_NORMAL);
        if (getFooterViewsCount() == 0) {
            addFooterView(mFooterView);
        }
    }

    public boolean isLockIsLoadingData() {
        return lockIsLoadingData;
    }

    public interface OnLoadMore {
        void loadMore();
    }
}
