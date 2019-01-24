
package com.example.laitianbing.bannerview_master;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.bannerview.adapter.ViewHolder;
import com.example.bannerview.banner_view.BannerAdapter;
import com.example.bannerview.banner_view.BannerView;

import java.util.List;

public class BannerActivity extends AppCompatActivity {
    private static final String EXTRA_LAYOUT_RES = "layout_res";
    private static final String EXTRA_ITEM_LAYOUT_RES = "item_layout_res";
    private BannerAdapter<Item> mAdapter;
    private BannerView mBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int layoutRes = intent.getIntExtra(EXTRA_LAYOUT_RES, R.layout.banner_normal);
        int bannerItemRes = intent.getIntExtra(EXTRA_ITEM_LAYOUT_RES, R.layout.item_normal_banner);

        setContentView(layoutRes);
        setAdapter(bannerItemRes, DataHelper.getItems());
    }

    public void setAdapter(@LayoutRes int itemLayout, List<Item> items) {
        mBanner = findViewById(R.id.banner);
        mAdapter = new BannerAdapter<Item>(this, itemLayout, items) {
            @Override
            public void convert(ViewHolder holder, final Item item) {
                holder.setImage(R.id.iv_image, item.imgRes);
                holder.setText(R.id.tv_name, item.url);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity.start(BannerActivity.this, item.url);
                    }
                });
            }
        };
        mBanner.setAdapter(mAdapter);
    }


    public void refreshDatas(View view) {
        mAdapter.setDatas(DataHelper.getItems());
    }


    public static void start(Activity activity, @LayoutRes int layoutRes, @LayoutRes int itemLayoutRes) {
        Intent intent = new Intent(activity, BannerActivity.class);
        intent.putExtra(EXTRA_LAYOUT_RES, layoutRes);
        intent.putExtra(EXTRA_ITEM_LAYOUT_RES, itemLayoutRes);
        ContextCompat.startActivity(activity, intent, null);
    }
}
