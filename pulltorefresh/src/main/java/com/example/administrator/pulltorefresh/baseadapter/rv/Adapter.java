package com.example.administrator.pulltorefresh.baseadapter.rv;

import java.util.List;

/**
 * Created by wanghongliang on 16/1/8.
 */
public interface Adapter<T> {
    void setData(List<T> data, boolean isRefresh);

    void setData(List<T> data);
}
