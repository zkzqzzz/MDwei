package com.example.administrator.mdwei.model.hotblog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.administrator.mdwei.BaseFragment;
import com.example.administrator.mdwei.R;
import com.example.administrator.pulltorefresh.PtrClassicFrameLayout;
import com.example.administrator.pulltorefresh.PtrDefaultHandler;
import com.example.administrator.pulltorefresh.PtrFrameLayout;
import com.example.administrator.pulltorefresh.PtrHandler;
import com.example.administrator.pulltorefresh.listview.WListView;

/**
 * Created by Administrator on 2016/6/1.
 */
public class HotBlogFragment  extends BaseFragment {

    private PtrClassicFrameLayout prtLay;
    private WListView wlistview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hotblog, null);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        prtLay= (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        prtLay.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                prtLay.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
    });

        wlistview= (WListView) view.findViewById(R.id.wlistview);
        //加载更多
        wlistview.setOnLoadMore(new WListView.OnLoadMore() {
            @Override
            public void loadMore() {
              //  loadData(false);
            }
        });
      /*  wlistview.setOnItemClickListener(new OnItemSingleClickListener() {
            @Override
            public void onItemSingleClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.isLockIsLoadingData() && position == adapter.getCount())
                    return;
                itemClick(parent, view, position, id);
            }
        });*/


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
