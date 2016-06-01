package com.example.administrator.mdwei.model.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mdwei.BaseFragment;
import com.example.administrator.mdwei.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class TopicFragment  extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_topic, null);
        init(view);
        return view;
    }

    @Override
    public void initView(View view)  {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
