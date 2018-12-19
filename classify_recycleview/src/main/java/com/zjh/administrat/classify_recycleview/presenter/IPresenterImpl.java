package com.zjh.administrat.classify_recycleview.presenter;

import android.graphics.Paint;

import com.zjh.administrat.classify_recycleview.callback.MyCallBack;
import com.zjh.administrat.classify_recycleview.model.IModelImpl;
import com.zjh.administrat.classify_recycleview.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {

    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void PRequestData(String urlStr, Map<String, String> params, Class clazz) {
        iModel.mRequestData(urlStr, params, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object object) {
                iView.viewDatas(object);
            }
        });
    }

    public void onDetch() {
        if (iView != null) {
            iView = null;
        }
        if (iModel != null) {
            iModel = null;
        }

    }


}
