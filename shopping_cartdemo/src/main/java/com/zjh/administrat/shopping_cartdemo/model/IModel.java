package com.zjh.administrat.shopping_cartdemo.model;

import com.zjh.administrat.shopping_cartdemo.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
