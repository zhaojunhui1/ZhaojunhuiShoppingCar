package com.bwie.zhaojunhui1217.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bwie.zhaojunhui1217.R;
import com.bwie.zhaojunhui1217.adapter.PhoneAdapter;
import com.bwie.zhaojunhui1217.bean.PhoneBean;
import com.zjh.administrat.shopping_cartdemo.presenter.IPresenterImpl;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView{

    private IPresenterImpl iPresenter;
    private XRecyclerView xRecyclerView;
    private int page;
    private String urlStr = "http://www.zhaoapi.cn/product/searchProducts?keywords=%E6%89%8B%E6%9C%BA";
    private PhoneAdapter mAdapter;
    private boolean flag = true;
    private int mSpanCount = 2;
    private PhoneBean phoneBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initManager();
        initClick();
    }
    //获取资源view
    private void initView() {
        xRecyclerView = findViewById(R.id.xrecycleView);
        iPresenter = new IPresenterImpl(this);
        mAdapter = new PhoneAdapter(this, flag);
        xRecyclerView.setAdapter(mAdapter);
        page = 1;
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        iPresenter.pRequestData(urlStr, map, PhoneBean.class);
    }
    //布局管理器
    private void initManager() {
        findViewById(R.id.choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                    xRecyclerView.setLayoutManager(linearLayoutManager);
                    flag = false;
                }else {
                    GridLayoutManager gridLayoutManager = new  GridLayoutManager (MainActivity.this, mSpanCount);
                    gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                    xRecyclerView.setLayoutManager(gridLayoutManager);
                    flag = true;
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);

        //设置动画
        xRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    //获取回传的数据
    @Override
    public void viewData(Object data) {
        phoneBean = (PhoneBean) data;
        if (page == 1){
            mAdapter.setDatas(phoneBean.getData());
        }else {
            mAdapter.addDatas(phoneBean.getData());
        }
        page ++;
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();

    }

    public void initClick(){
        mAdapter.setClickLinear(new PhoneAdapter.Click() {
            @Override
            public void OnClick(int i, String price) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("title", phoneBean.getData().get(i).getTitle());
                intent.putExtra("price", price);
                intent.putExtra("images", phoneBean.getData().get(i).getImages());
                startActivity(intent);
            }

            @Override
            public void OnLongClick(int i) {
                mAdapter.removeData(i);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
