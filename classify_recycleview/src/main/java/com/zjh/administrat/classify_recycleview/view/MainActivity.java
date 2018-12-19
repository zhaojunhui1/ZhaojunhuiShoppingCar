package com.zjh.administrat.classify_recycleview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zjh.administrat.classify_recycleview.R;
import com.zjh.administrat.classify_recycleview.adapter.LeftAdapter;
import com.zjh.administrat.classify_recycleview.adapter.RightAdapter;
import com.zjh.administrat.classify_recycleview.bean.LeftBean;
import com.zjh.administrat.classify_recycleview.bean.RightBean;
import com.zjh.administrat.classify_recycleview.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView{

    private RecyclerView recyclerLeft, recyclerRight;
    public String urlLeft = "http://www.zhaoapi.cn/product/getCatagory";
    public String urlRight = "http://www.zhaoapi.cn/product/getProductCatagory";
    private IPresenterImpl iPresenter;
    private LeftAdapter mAdapter;
    private RightAdapter mAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLeft();
        initRight();
        leftType();
        //LeftAdapter接口回调获得cid
        mAdapter.setOnLeftClick(new LeftAdapter.OnLeftClick() {
            @Override
            public void OnClick(int i, String cid) {
                Toast.makeText(MainActivity.this, "点击了"+cid, Toast.LENGTH_SHORT).show();
                rightType(cid);

            }
        });
    }
    //左边数据
    private void leftType() {
        Map<String, String> map = new HashMap<>();
        iPresenter.PRequestData(urlLeft, map, LeftBean.class);
    }
    private void rightType(String cid){
        Map<String, String> map = new HashMap<>();
        map.put("cid", cid);
        iPresenter.PRequestData(urlRight, map, RightBean.class);
        initRight();
    }

    //左边操作
    private void initLeft() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerLeft.setLayoutManager(linearLayoutManager);
        mAdapter = new LeftAdapter(this);
        recyclerLeft.setAdapter(mAdapter);
        //分割线
        recyclerLeft.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    //右边数据
    private void initRight() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerRight.setLayoutManager(linearLayoutManager);
        mAdapter1 = new RightAdapter(this);
        recyclerRight.setAdapter(mAdapter1);
        //分割线
        recyclerRight.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    //初始化view
    private void initView() {
        recyclerLeft = findViewById(R.id.recycleLeft);
        recyclerRight = findViewById(R.id.recycleRight);
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    public void viewDatas(Object data) {
        if (data instanceof LeftBean){
            LeftBean leftBean = (LeftBean) data;
            mAdapter.setDatas(leftBean.getData());
        }else if(data instanceof RightBean){
            RightBean rightBean = (RightBean) data;
            mAdapter1.setDatas(rightBean.getData());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
