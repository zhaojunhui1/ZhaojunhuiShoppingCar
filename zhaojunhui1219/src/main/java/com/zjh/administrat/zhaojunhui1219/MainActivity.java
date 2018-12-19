package com.zjh.administrat.zhaojunhui1219;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
   //初始化View
    private void initView() {
        recyclerView = findViewById(R.id.recycleView);
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //适配器
        mAdapter = new UserAdapter(this);
        recyclerView.setAdapter(mAdapter);
        //模拟数据
        User user = new User();
        for (int i = 0; i < 20; i++) {
            user.setTitle("标题栏"+i);
            user.setName("滚动列表"+i);
            mAdapter.setDatas(user);
        }

        //分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(divider);
    }


}
