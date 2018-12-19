package com.zjh.administrat.classify_recycleview.model;

import com.zjh.administrat.classify_recycleview.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
