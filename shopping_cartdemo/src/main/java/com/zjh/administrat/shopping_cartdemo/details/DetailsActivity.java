package com.zjh.administrat.shopping_cartdemo.details;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zjh.administrat.shopping_cartdemo.R;
import com.zjh.administrat.shopping_cartdemo.bean.ShoppingBean;
import com.zjh.administrat.shopping_cartdemo.adapter.StaggeredAdapter;
import com.zjh.administrat.shopping_cartdemo.presenter.IPresenterImpl;
import com.zjh.administrat.shopping_cartdemo.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements IView {

    private String urlStr = "http://www.zhaoapi.cn/product/getCarts";
    private RecyclerView recyclerView;
    private Banner banner;
    private IPresenterImpl iPresenter;
    private StaggeredAdapter mAdapter;
    private int mSpanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
    }

    //初始化View
    private void initView() {
        recyclerView = findViewById(R.id.recycleView);
        banner = findViewById(R.id.banner);
        iPresenter = new IPresenterImpl(this);

        Map<String, String> map = new HashMap<>();
        map.put("uid", "71");
        iPresenter.pRequestData(urlStr, map, ShoppingBean.class);
        //布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //创建适配器
        mAdapter = new StaggeredAdapter(this);
        recyclerView.setAdapter(mAdapter);

    }


    //获取数据
    @Override
    public void viewData(Object object) {
        ShoppingBean shoppingBean = (ShoppingBean) object;
        mAdapter.setDatas(shoppingBean.getData());

        for (int i = 0; i < shoppingBean.getData().size(); i++) {
            List<ShoppingBean.DataBean.ListBean> list = shoppingBean.getData().get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                String str = "";
                int length = list.get(j).getImages().length();
                for (int m = 0; m < length; m++) {
                    if (list.get(j).getImages().substring(m, m + 1).equals("|")) {
                        str = list.get(j).getImages().substring(m + 1, length).trim();

                        //顶部轮播图
                        //设置banner样式
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        //设置图图片的加载器
                        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                ShoppingBean.DataBean.ListBean mBanner = (ShoppingBean.DataBean.ListBean) path;
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(mBanner.getImages(), imageView);
                            }
                            @Override
                            public ImageView createImageView(Context context) {
                                ImageView imageView = new ImageView(context);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                return imageView;
                            }
                        });
                        banner.setImageLoader((ImageLoaderInterface) list);
                        banner.start();
                    }

                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
