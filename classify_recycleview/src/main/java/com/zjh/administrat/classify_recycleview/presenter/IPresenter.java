package com.zjh.administrat.classify_recycleview.presenter;

import java.util.Map;

public interface IPresenter {
    void PRequestData(String urlStr, Map<String, String> params, Class clazz);
}
