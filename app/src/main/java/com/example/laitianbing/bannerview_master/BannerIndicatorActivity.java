package com.example.laitianbing.bannerview_master;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.laitianbing.bannerview_master.adapter.ViewHolder;
import com.example.laitianbing.bannerview_master.banner_view.BannerAdapter;
import com.example.laitianbing.bannerview_master.banner_view.BannerView;
import com.example.laitianbing.bannerview_master.banner_view.indicator.Indicator;
import com.example.laitianbing.bannerview_master.banner_view.indicator.RectIndicator;
import com.example.laitianbing.bannerview_master.banner_view.indicator.TextIndicator;

import java.util.List;

public class BannerIndicatorActivity extends AppCompatActivity {

    private BannerAdapter<Item> mBannerAdapter1;
    private BannerAdapter<Item> mBannerAdapter2;
    private BannerAdapter<Item> mBannerAdapter3;
    private RectIndicator mCircleIndicator;
    private RectIndicator mRectIndicator;
    private TextIndicator mTextIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_indicator);
        BannerView bannerView1 = findViewById(R.id.banner1);
        BannerView bannerView2 = findViewById(R.id.banner2);
        BannerView bannerView3 = findViewById(R.id.banner3);

        initAdapter();
        initIndicator();

        //设置适配器
        setAdapter(mBannerAdapter1, bannerView1);
        setAdapter(mBannerAdapter2, bannerView2);
        setAdapter(mBannerAdapter3, bannerView3);

        //设置指示器
        setIndicator(mCircleIndicator, bannerView1);
        setIndicator(mRectIndicator, bannerView2);
        setIndicator(mTextIndicator, bannerView3);
    }

    private void initAdapter() {
        mBannerAdapter1 = new BannerAdapter<Item>(this, R.layout.item_3d_banner, DataHelper.getItems()) {
            @Override
            public void convert(ViewHolder holder, final Item item) {
                holder.setImage(R.id.iv_image, item.imgRes);
                holder.setText(R.id.tv_name, item.url);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity.start(BannerIndicatorActivity.this, item.url);
                    }
                });
            }

        };
        mBannerAdapter2 = new BannerAdapter<Item>(this, R.layout.item_3d_banner, DataHelper.getItems()) {
            @Override
            public void convert(ViewHolder holder, final Item item) {
                holder.setImage(R.id.iv_image, item.imgRes);
                holder.setText(R.id.tv_name, item.url);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity.start(BannerIndicatorActivity.this, item.url);
                    }
                });
            }

        };
        mBannerAdapter3 = new BannerAdapter<Item>(this, R.layout.item_vertical_3d_banner, DataHelper.getItems()) {
            @Override
            public void convert(ViewHolder holder, final Item item) {
                holder.setImage(R.id.iv_image, item.imgRes);
                holder.setText(R.id.tv_name, item.url);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity.start(BannerIndicatorActivity.this, item.url);
                    }
                });
            }
        };
    }

    private void initIndicator() {
        mCircleIndicator = new RectIndicator(this, RectIndicator.SHAPE_CIRCLE);
        //设置指示器长和宽.
        mCircleIndicator.setIndicatorSize(10, 10);
        //设置指示器之间的间距
        mCircleIndicator.setItemMargin(20);
        //设置指示器显示在banner的底部的中间
        mCircleIndicator.setRules(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
        //设置指示器显示距离banner底部10dp
        mCircleIndicator.setMargins(0, 0, 0, 10);


        mRectIndicator = new RectIndicator(this, RectIndicator.SHAPE_RECTANGLE);
        //设置指示器的宽度和高度
        mRectIndicator.setIndicatorSize(8, 14);
        //设置指示器显示在banner的底部的中间
        mRectIndicator.setRules(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.CENTER_VERTICAL);
        //设置指示器显示距离banner底部10dp
        mRectIndicator.setMargins(0, 0, 10, 0);


        mTextIndicator = new TextIndicator(this, 0);
        mTextIndicator.setRules(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.ALIGN_PARENT_BOTTOM);
        mTextIndicator.setMargins(0, 0, 5, 5);
        mTextIndicator.setTextColor(ContextCompat.getColor(this, android.R.color.white));

    }

    public void refreshDatas(View view) {
        List<Item> items = DataHelper.getItems();
        mBannerAdapter1.setDatas(items);
        mBannerAdapter2.setDatas(items);
        mBannerAdapter3.setDatas(items);

    }

    private void setAdapter(BannerAdapter adapter, BannerView bannerView) {
        bannerView.setAdapter(adapter);
    }

    private void setIndicator(Indicator indicator, BannerView bannerView) {
        bannerView.addIndicator(indicator);
    }

    public static void start(Activity activity) {
        ContextCompat.startActivity(activity, new Intent(activity, BannerIndicatorActivity.class), null);
    }


}
