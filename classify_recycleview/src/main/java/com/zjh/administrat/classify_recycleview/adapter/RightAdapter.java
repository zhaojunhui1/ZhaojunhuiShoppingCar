package com.zjh.administrat.classify_recycleview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjh.administrat.classify_recycleview.R;
import com.zjh.administrat.classify_recycleview.bean.RightBean;

import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RightBean.DataBean> mRight;
    private Context mContext;

    public RightAdapter(Context mContext) {
        this.mContext = mContext;
        mRight = new ArrayList<>();
    }

    public void setDatas(List<RightBean.DataBean> data) {
        if (data != null){
            mRight.addAll(data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.name.setText(mRight.get(i).getName());

        //商品详情
        GoodsAdapter gAdapter = new GoodsAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mHolder.recyclerData.setLayoutManager(linearLayoutManager);
        mHolder.recyclerData.setAdapter(gAdapter);
        mHolder.recyclerData.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        gAdapter.setDatas(mRight.get(i).getList());

    }

    @Override
    public int getItemCount() {
        return mRight.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        private RecyclerView recyclerData;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerData = itemView.findViewById(R.id.recycleData);
            name = itemView.findViewById(R.id.name);
        }
    }


}
