package com.bwie.zhaojunhui1217.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.zhaojunhui1217.R;
import com.bwie.zhaojunhui1217.bean.PhoneBean;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PhoneBean.DataBean> mData;
    private Context mContext;
    private boolean flag;

    public PhoneAdapter(Context mContext, boolean flag) {
        this.mContext = mContext;
        this.flag = flag;
        mData = new ArrayList<>();
    }

    public void setDatas(List<PhoneBean.DataBean> data) {
        mData.clear();
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<PhoneBean.DataBean> data) {
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
    //长按删除
    public void removeData(int i) {
        mData.remove(i);
        //notifyItemRemoved(i);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder mHolder = null;
        if (flag){
            View view = LayoutInflater.from(mContext).inflate(R.layout.linear_item, viewGroup, false);
            mHolder = new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, viewGroup, false);
            mHolder = new ViewHolder(view);
        }
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.textView1.setText(mData.get(i).getTitle());
        mHolder.textView2.setText("￥" + mData.get(i).getPrice() + "");

        String str = "";
        int length = mData.get(i).getImages().length();
        for (int j = 0; j < length; j++) {
            if (mData.get(i).getImages().substring(j, j + 1).equals("|")) {
                str = mData.get(i).getImages().substring(j + 1, length).trim();
            }
        }
        Glide.with(mContext).load(str).into(mHolder.imageView);

        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClick != null){
                    mClick.OnClick(i, "￥"+mData.get(i).getPrice()+"");
                }
            }
        });
        mHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClick != null){
                    mClick.OnLongClick(i);
                }
                return true;
            }
        });

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

    //自定义接口回调

    Click mClick;
    public void setClickLinear(Click click){
        mClick = click;
    }

    public interface Click{
        void OnClick(int i, String price);
        void OnLongClick(int i);
    }
}
