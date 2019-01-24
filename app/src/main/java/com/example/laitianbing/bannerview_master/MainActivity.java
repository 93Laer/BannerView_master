package com.example.laitianbing.bannerview_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void normalBanner(View view) {
        BannerActivity.start(this, R.layout.banner_normal, R.layout.item_normal_banner);
    }

    public void threeDBanner(View view) {
        BannerActivity.start(this, R.layout.banner_3d, R.layout.item_3d_banner);
    }

    public void verticalBanner(View view) {
        BannerActivity.start(this, R.layout.banner_3d_vertical, R.layout.item_vertical_3d_banner);
    }

    public void hasIndicatorBanner(View view) {
        BannerIndicatorActivity.start(this);
    }

    public void taobaoNews(View view) {
        BannerActivity.start(this, R.layout.taobao_news, R.layout.item_taobao_news);
    }
}
