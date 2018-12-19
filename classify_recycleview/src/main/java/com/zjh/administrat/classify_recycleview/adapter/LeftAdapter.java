package com.zjh.administrat.classify_recycleview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjh.administrat.classify_recycleview.R;
import com.zjh.administrat.classify_recycleview.bean.LeftBean;

import java.util.ArrayList;
import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LeftBean.DataBean> mLeft;
    private Context mContext;

    public LeftAdapter(Context mContext) {
        this.mContext = mContext;
        mLeft = new ArrayList<>();
    }

    public void setDatas(List<LeftBean.DataBean> data) {
        if (data != null){
            mLeft.addAll(data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.left_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.name.setText(mLeft.get(i).getName());

        //实现接口参数
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnLeftClick != null){
                    mOnLeftClick.OnClick(i, mLeft.get(i).getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLeft.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    //成员变量
    OnLeftClick mOnLeftClick;
    //set方法
    public void setOnLeftClick(OnLeftClick onLeftClick){
        mOnLeftClick = onLeftClick;
    }
    //定义一个接口
    public interface OnLeftClick{
        void OnClick(int i, String cid);
    }

}
