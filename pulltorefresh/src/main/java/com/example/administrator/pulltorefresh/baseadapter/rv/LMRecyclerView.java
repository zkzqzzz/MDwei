package com.example.administrator.pulltorefresh.baseadapter.rv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jyp on 15/8/10.
 */
public class LMRecyclerView extends RecyclerView {
    private FooterView mFooterView;
    private OnLoadMoreListener moreListener;
    private boolean isEndLoadMoreData = false;
    private boolean lockIsLoadingData = false;
    private View mEmptyView;

    public LMRecyclerView(Context context) {
        this(context, null);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new OnLoadMoreScrollListener());
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
        }
    }

    public void addFooter() {
        if (getAdapter() instanceof BaseRecyclerAdapter) {
            mFooterView = new FooterView(getContext());
            mFooterView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
            ((BaseRecyclerAdapter) getAdapter()).addFooter(mFooterView);
        }
    }

    public void removeFooter() {
        if (getAdapter() instanceof BaseRecyclerAdapter) {
            ((BaseRecyclerAdapter) getAdapter()).removeFooter();
        }
    }

    public void setStateDataIsLoading(boolean isLoading) {
        lockIsLoadingData = isLoading;
    }

    /**
     * 设置显示为加载更多
     * 设置正在加载
     */
    public void setStateLoadComplete() {
        mFooterView.setState(FooterView.STATE_NORMAL);
        lockIsLoadingData = false;
    }

    public void setStateNoMoreDataToBeLoaded() {
        isEndLoadMoreData = true;
        mFooterView.setState(FooterView.STATE_NO_MORE);
    }

    public void resetListStatus() {
        isEndLoadMoreData = false;
        mFooterView.setState(FooterView.STATE_NORMAL);
    }

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };



    public class OnLoadMoreScrollListener extends OnScrollListener {
        /**
         * 最后一个的位置
         */
        private int[] lastPositions;
        /**
         * 最后一个可见的item的位置
         */
        private int lastVisibleItemPosition;
        /**
         * 当前滑动的状态
         */
        private int currentScrollState = 0;

        private int layoutManagerType;
        private static final int LINEAR = 1;
        private static final int GRID = 2;
        private static final int STAGGERED_GRID = 3;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManagerType == 0) {
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = LINEAR;
                } else if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = GRID;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = STAGGERED_GRID;
                } else {
                    throw new RuntimeException(
                            "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case LINEAR:
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager
                            = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                    break;
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            currentScrollState = newState;
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (isEndLoadMoreData || lockIsLoadingData) {
                return;
            }
            if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                    lastVisibleItemPosition >= totalItemCount - 1) {
                mFooterView.setState(FooterView.STATE_LOADING);
                lockIsLoadingData = true;
                if (null != moreListener)
                    moreListener.onLoadMore();
            }
        }

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }

    public void setMoreListener(OnLoadMoreListener moreListener) {
        this.moreListener = moreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
