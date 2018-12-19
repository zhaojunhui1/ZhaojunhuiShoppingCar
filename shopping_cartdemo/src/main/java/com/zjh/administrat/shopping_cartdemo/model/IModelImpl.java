package com.zjh.administrat.shopping_cartdemo.model;

import com.zjh.administrat.shopping_cartdemo.callback.ICallBack;
import com.zjh.administrat.shopping_cartdemo.callback.MyCallBack;
import com.zjh.administrat.shopping_cartdemo.utils.OkHttps;

import java.util.Map;

public class IModelImpl implements IModel {


    @Override
    public void mRequestData(String urlStr, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttps.getInstance().getRequest(urlStr, params, clazz, new ICallBack() {
            @Override
            public void success(Object object) {
                myCallBack.OnSuccess(object);
            }

            @Override
            public void fails(Exception e) {

            }
        });
    }

}
