package com.zjh.administrat.shopping_cartdemo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zjh.administrat.shopping_cartdemo.R;
import com.zjh.administrat.shopping_cartdemo.adapter.MyAdapter;
import com.zjh.administrat.shopping_cartdemo.bean.ShoppingBean;
import com.zjh.administrat.shopping_cartdemo.details.DetailsActivity;
import com.zjh.administrat.shopping_cartdemo.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private String urlStr = "http://www.zhaoapi.cn/product/getCarts";
    private IPresenterImpl iPresenter;
    private ExpandableListView el_cart;
    private CheckBox cb_cart_all_select;
    private TextView tv_cart_total_price;
    private Button btn_cart_pay;
    private MyAdapter adapter;
    private ShoppingBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        adapter = new MyAdapter(this);
        el_cart.setAdapter(adapter);
    }

    //初始化view
    private void initData() {
        iPresenter = new IPresenterImpl(this);
        Map<String, String> map = new HashMap<>();
        map.put("uid", "71");
        iPresenter.pRequestData(urlStr, map, ShoppingBean.class);
    }

    //B.刷新checkbox状态和总价和总数量
    private void refreshSelectedAndTotalPriceAndTotalNumber() {
        //去判断是否所有得商品都被选中
        boolean allProductsSelected = adapter.isAllProductsSelected();
        //设置给全选
        cb_cart_all_select.setChecked(allProductsSelected);
        //计算总价
        float totalPrice = adapter.calculateTotalPrice();
        tv_cart_total_price.setText("总价:￥"+totalPrice);
        //计算总数量
        int totalNumber = adapter.calculateTotalNumber();
        btn_cart_pay.setText("去结算("+totalNumber+")");
    }

    //初始化的操作
    private void initView() {
        el_cart = findViewById(R.id.el_cart);
        cb_cart_all_select = findViewById(R.id.cb_cart_all_select);
        tv_cart_total_price = findViewById(R.id.tv_cart_total_price);
        btn_cart_pay = findViewById(R.id.btn_cart_pay);

        //全选按钮
        findViewById(R.id.cb_cart_all_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //底部全选按钮选中的时候所有得商品都被选中
                boolean allProductsSelected = adapter.isAllProductsSelected();
                adapter.changeAllProductStatus(!allProductsSelected);
                adapter.notifyDataSetChanged();
                //刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }
        });
    }

    //获取数据
    @Override
    public void viewData(Object object) {
        data = (ShoppingBean) object;
        adapter.setDatas(data.getData());

        for (int p = 0; p < data.getData().size(); p++) {
            el_cart.expandGroup(p);
        }

        //回调方法
        adapter.setOnCartListChangeListener(new MyAdapter.OnCartListChangeListener() {
            @Override
            public void onSellerCheckedChange(int i) {
                //商家被点击
                boolean currentSellerAllProductSelected = adapter.isCurrentSellerAllProductSelected(i);
                adapter.changeCurrentSellerAllProductsStatus(i, !currentSellerAllProductSelected);
                adapter.notifyDataSetChanged();
                //B.刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void onProductCheckedChange(int i, int i1) {
                //点击商品得复选框
                adapter.changeCurrentProductStatus(i, i1);
                adapter.notifyDataSetChanged();
                //B.刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void onProducNumberChange(int i, int i1, int number) {
                //当加减被点击
                adapter.changeCurrentProductNumber(i, i1, number);
                adapter.notifyDataSetChanged();
                //B.刷新底部数据
                refreshSelectedAndTotalPriceAndTotalNumber();
            }
        });

        //跳转首页
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }

}
