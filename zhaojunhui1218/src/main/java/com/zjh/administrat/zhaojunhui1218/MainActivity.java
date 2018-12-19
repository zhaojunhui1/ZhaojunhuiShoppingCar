package com.zjh.administrat.zhaojunhui1218;

import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView() {

        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new LinAdapter(this);
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setImage(R.mipmap.ic_launcher);
            user.setName("购物车里的第"+i+"件商品");
            user.setPrice(500.00);
            mAdapter.setDatas(user);
        }
        recyclerView.setAdapter(mAdapter);

    }


}
