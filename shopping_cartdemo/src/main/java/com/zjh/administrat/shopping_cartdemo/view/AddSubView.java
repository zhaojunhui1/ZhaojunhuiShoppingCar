package com.zjh.administrat.shopping_cartdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjh.administrat.shopping_cartdemo.R;

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private int number = 1;
    private TextView sub_tv, product_number_tv, add_tv;

    public AddSubView(Context context) {
        this(context,null);
    }

    public AddSubView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(context, R.layout.add_remove, this);
        sub_tv = view.findViewById(R.id.sub_tv);
        product_number_tv = view.findViewById(R.id.product_number_tv);
        add_tv = view.findViewById(R.id.add_tv);

        sub_tv.setOnClickListener(this);
        add_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sub_tv:
                if (number > 1) {
                    --number;
                    product_number_tv.setText(number+"");
                    if (mNumberChangeListener != null) {
                        mNumberChangeListener.onNumberChange(number);
                    }
                } else {
                    Toast.makeText(getContext(), "我是有底线的", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_tv:
                if (number < 9) {
                    ++number;
                    product_number_tv.setText(number+"");
                    if (mNumberChangeListener != null) {
                        mNumberChangeListener.onNumberChange(number);
                    }
                } else {
                    Toast.makeText(getContext(), "每人限购10件", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        product_number_tv.setText(number+"");
    }

    //成员变量
    OnNumberChangeListener mNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.mNumberChangeListener = onNumberChangeListener;
    }

    public interface OnNumberChangeListener {
        void onNumberChange(int number);
    }

}
