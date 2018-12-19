package com.zjh.administrat.shopping_cartdemo.presenter;

import com.zjh.administrat.shopping_cartdemo.callback.MyCallBack;
import com.zjh.administrat.shopping_cartdemo.model.IModelImpl;
import com.zjh.administrat.shopping_cartdemo.view.IView;

import java.util.Map;

public class IPresenterImpl implements Ipresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void pRequestData(String urlStr, Map<String, String> params, Class clazz) {
        iModel.mRequestData(urlStr, params, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object object) {
                iView.viewData(object);
            }
        });
    }

    public void onDetch(){
        if (iModel != null){
            iModel = null;
        }
        if (iView != null){
            iView = null;
        }
    }

}
