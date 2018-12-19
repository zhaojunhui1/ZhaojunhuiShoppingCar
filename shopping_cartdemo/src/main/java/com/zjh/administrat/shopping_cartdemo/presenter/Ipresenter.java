package com.zjh.administrat.shopping_cartdemo.presenter;

import java.util.Map;

public interface Ipresenter {
    void pRequestData(String urlStr, Map<String, String> params, Class clazz);
}
