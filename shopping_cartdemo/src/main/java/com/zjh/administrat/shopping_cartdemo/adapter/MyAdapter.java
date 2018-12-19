package com.zjh.administrat.shopping_cartdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjh.administrat.shopping_cartdemo.R;
import com.zjh.administrat.shopping_cartdemo.bean.ShoppingBean;
import com.zjh.administrat.shopping_cartdemo.view.AddSubView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseExpandableListAdapter {
    private List<ShoppingBean.DataBean> list;
    private Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
    }

    public void setDatas(List<ShoppingBean.DataBean> data) {
        if (data != null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ShoppingBean.DataBean dataBean = list.get(groupPosition);
        ParentViewHolder parentViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cart_parent, parent, false);
            parentViewHolder = new ParentViewHolder(convertView);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        parentViewHolder.seller_name_tv.setText(dataBean.getSellerName());
        boolean currentSellerAllProductSelected = isCurrentSellerAllProductSelected(groupPosition);
        parentViewHolder.seller_cb.setChecked(currentSellerAllProductSelected);
        //D.设置点击CheckBox
        parentViewHolder.seller_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCartListChangeListener != null) {
                    mOnCartListChangeListener.onSellerCheckedChange(groupPosition);
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cart_child, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //设置商品名字
        childViewHolder.product_title_name_tv.setText(list.get(groupPosition).getList().get(childPosition).getTitle());
        //设置商品单价
        childViewHolder.product_price_tv.setText("￥:"+list.get(groupPosition).getList().get(childPosition).getPrice()+"");
        //设置复选框是否选中
        childViewHolder.child_cb.setChecked(list.get(groupPosition).getList().get(childPosition).getSelected() == 1);
        //设置组合式自定义控件内部的数量
        childViewHolder.add_remove_view.setNumber(list.get(groupPosition).getList().get(childPosition).getNum());

        String str = "";
        int length = list.get(groupPosition).getList().get(childPosition).getImages().length();
        for (int j = 0; j < length; j++) {
            if (list.get(groupPosition).getList().get(childPosition).getImages().substring(j, j + 1).equals("|")) {
                str = list.get(groupPosition).getList().get(childPosition).getImages().substring(j + 1, length).trim();
            }
        }
        //展示图片
        Glide.with(mContext).load(str).into(childViewHolder.product_icon_iv);

        //D.设置商品CheckBox的点击事件,通过接口回调,暴露给外面
       childViewHolder.child_cb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mOnCartListChangeListener != null) {
                   mOnCartListChangeListener.onProductCheckedChange(groupPosition, childPosition);
               }
           }
       });

        //D.设置商品数量的点击事件,通过接口回调,暴露给外面
        childViewHolder.add_remove_view.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int number) {
                if (mOnCartListChangeListener != null){
                    mOnCartListChangeListener.onProducNumberChange(groupPosition, childPosition, number);
                }
            }
        });

        return convertView;
    }

        public boolean isCurrentSellerAllProductSelected (int i){
            //根据商品改变商家--如果商品全部选中商家则选中--有一个商品没选中则商家不选中
            ShoppingBean.DataBean dataBean = list.get(i);
            List<ShoppingBean.DataBean.ListBean> beans = dataBean.getList();
            for (ShoppingBean.DataBean.ListBean bean : beans) {
                if (bean.getSelected() == 0) {
                    return false;
                }
            }
            return true;
        }

        public boolean isAllProductsSelected () {
            //根据商品改变全选--如果商品全部选中全选则选中--有一个商品没选中则全选不选中
            for (int x = 0; x < list.size(); x++) {
                ShoppingBean.DataBean dataBean = list.get(x);
                List<ShoppingBean.DataBean.ListBean> list1 = dataBean.getList();
                for (int j = 0; j < list1.size(); j++) {
                    if (list1.get(j).getSelected() == 0) {
                        return false;
                    }
                }
            }
            return true;
        }

        public int calculateTotalNumber () {
            //计算总数量
            int totalNumber = 0;
            for (int i = 0; i < list.size(); i++) {
                ShoppingBean.DataBean dataBean = list.get(i);
                List<ShoppingBean.DataBean.ListBean> list1 = dataBean.getList();
                for (int j = 0; j < list1.size(); j++) {
                    if (list1.get(j).getSelected() == 1) {
                        int num = list1.get(j).getNum();
                        totalNumber += num;
                    }
                }
            }
            return totalNumber;
        }

        public float calculateTotalPrice () {
            //获取总价
            float totalPrice = 0;
            for (int i = 0; i < list.size(); i++) {
                ShoppingBean.DataBean dataBean = list.get(i);
                List<ShoppingBean.DataBean.ListBean> list1 = dataBean.getList();
                for (int j = 0; j < list1.size(); j++) {
                    if (list1.get(j).getSelected() == 1) {
                        float price = (float) list1.get(j).getPrice();
                        int num = list1.get(j).getNum();
                        totalPrice += price * num;
                    }
                }
            }
            return totalPrice;
        }

        //C.当商品组的全选框点击时,更新所有商品的状态
        public void changeCurrentSellerAllProductsStatus ( int i, boolean isSelected){
            ShoppingBean.DataBean dataBean = list.get(i);
            List<ShoppingBean.DataBean.ListBean> beans = dataBean.getList();
            for (int j = 0; j < beans.size(); j++) {
                ShoppingBean.DataBean.ListBean bean = beans.get(j);
                bean.setSelected(isSelected ? 1 : 0);
            }
        }

        //C.当商家子条目的全选框选中时,更新其状态
        public void changeCurrentProductStatus ( int i, int i1){
            ShoppingBean.DataBean dataBean = list.get(i);
            List<ShoppingBean.DataBean.ListBean> list1 = dataBean.getList();
            ShoppingBean.DataBean.ListBean listBean = list1.get(i1);
            listBean.setSelected(listBean.getSelected() == 0 ? 1 : 0);
        }

        //C.设置所有商品的状态
        public void changeAllProductStatus (boolean selected){
            for (int x = 0; x < list.size(); x++) {
                ShoppingBean.DataBean dataBean = list.get(x);
                List<ShoppingBean.DataBean.ListBean> list1 = dataBean.getList();
                for (int j = 0; j < list1.size(); j++) {
                    list1.get(j).setSelected(selected ? 1 : 0);
                }
            }
        }

        //C.当加减器被点击时,调用,改变里面当前商品的数量 参数1定位那个商家 参数2定位哪个商品 参数3定位改变具体的数量是多少
        public void changeCurrentProductNumber (int i, int i1, int number){
            ShoppingBean.DataBean dataBean = list.get(i);
            List<ShoppingBean.DataBean.ListBean> list = dataBean.getList();
            ShoppingBean.DataBean.ListBean listBean = list.get(i1);
            listBean.setNum(number);

        }

    //成员变量
    OnCartListChangeListener mOnCartListChangeListener;
    //D.方法的set
    public void setOnCartListChangeListener (OnCartListChangeListener onCartListChangeListener){
        mOnCartListChangeListener = onCartListChangeListener;
    }
    public interface OnCartListChangeListener {
            //当商家的checkBox点击时回调
            void onSellerCheckedChange(int i);

             //当点击子条目商品的CheckBox回调
            void onProductCheckedChange(int i, int i1);

            //当点击加减按钮的回调
            void onProducNumberChange(int i, int i1, int number);
        }


        public static class ParentViewHolder {
            public CheckBox seller_cb;
            public TextView seller_name_tv;

            public ParentViewHolder(View rootView) {
                seller_cb = rootView.findViewById(R.id.seller_cb);
                seller_name_tv = rootView.findViewById(R.id.seller_name_tv);
            }
        }

        public static class ChildViewHolder {
            public CheckBox child_cb;
            public ImageView product_icon_iv;
            public TextView product_title_name_tv;
            public TextView product_price_tv;
            public AddSubView add_remove_view;

            public ChildViewHolder(View rootView) {
                child_cb = rootView.findViewById(R.id.child_cb);
                product_icon_iv = rootView.findViewById(R.id.product_icon_iv);
                product_title_name_tv = rootView.findViewById(R.id.product_title_name_tv);
                product_price_tv = rootView.findViewById(R.id.product_price_tv);
                add_remove_view = rootView.findViewById(R.id.add_remove_view);
            }
        }

        @Override
        public boolean isChildSelectable ( int groupPosition, int childPosition){
            return false;
        }

        @Override
        public Object getGroup ( int groupPosition){
            return null;
        }

        @Override
        public Object getChild ( int groupPosition, int childPosition){
            return null;
        }

        @Override
        public long getGroupId ( int groupPosition){
            return 0;
        }

        @Override
        public long getChildId ( int groupPosition, int childPosition){
            return 0;
        }

        @Override
        public boolean hasStableIds () {
            return false;
        }

}
