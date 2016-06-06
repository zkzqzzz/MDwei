package com.example.administrator.mdwei.baseadapter;

import java.util.List;

/**
 * Created by wanghongliang on 16/1/8.
 */
public interface AddData<T> {
    void setData(List<T> data, boolean isRefresh);

    void setData(List<T> data);
}
