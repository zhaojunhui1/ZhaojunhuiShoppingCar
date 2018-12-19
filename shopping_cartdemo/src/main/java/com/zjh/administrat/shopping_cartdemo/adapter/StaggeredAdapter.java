package com.zjh.administrat.shopping_cartdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjh.administrat.shopping_cartdemo.R;
import com.zjh.administrat.shopping_cartdemo.bean.ShoppingBean;

import java.util.ArrayList;
import java.util.List;

public class StaggeredAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShoppingBean.DataBean> mData;
    private Context mContext;

    public StaggeredAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(List<ShoppingBean.DataBean> data) {
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.staggered_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        ShoppingBean.DataBean dataBean = mData.get(i);
        List<ShoppingBean.DataBean.ListBean> list = dataBean.getList();
        for (int z = 0; z < list.size(); z++) {
            mHolder.textView1.setText(list.get(z).getTitle());
            mHolder.textView2.setText("￥"+list.get(z).getPrice()+"");
            //截取图片集
            String str = "";
            int length = list.get(z).getImages().length();
            for (int j = 0; j < length; j++) {
                if (list.get(z).getImages().substring(j, j + 1).equals("|")) {
                    str = list.get(z).getImages().substring(j + 1, length).trim();
                }
            }
            Glide.with(mContext).load(str).into(mHolder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }


}
