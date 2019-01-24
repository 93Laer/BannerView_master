
### 不使用指示器，普通样式
布局
```
 <com.example.laitianbing.bannerview_master.banner_view.BannerView
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:background="@color/bannerBackground"
        app:autoPlaying="true"
        app:empty_layout="@layout/empty_view"/>
```

找到控件并绑定数据
```
  public void setAdapter(@LayoutRes int itemLayout, List<Item> items) {
        //找到控件并绑定数据
        mBanner = findViewById(R.id.banner);
        mAdapter = new BannerAdapter<Item>(this, itemLayout, items) {
                      @Override
                      public void convert(ViewHolder holder, Item item) {
                          holder.setImage(R.id.iv_image, item.imgRes);
                          holder.setText(R.id.tv_name, item.name);
                      }
                  };
        mBanner.setAdapter(mAdapter);
    }

    //刷新数据
    public void refreshDatas(View view) {
        mAdapter.setDatas(DataHelper.getItems());
    }
```

### 使用指示器
**注意：**指示器可以自定义但必须继承`view`，且实现`Indicator`接口

布局
```
 <com.example.laitianbing.bannerview_master.banner_view.BannerView
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:background="@color/bannerBackground"
        app:autoPlaying="true"
        app:empty_layout="@layout/empty_view"/>
```

找到控件并绑定数据
```
  public void setAdapter(@LayoutRes int itemLayout, List<Item> items) {
        //找到控件并绑定数据
        mBanner = findViewById(R.id.banner);
        mAdapter = new BannerAdapter<Item>(this, itemLayout, items) {
                      @Override
                      public void convert(ViewHolder holder, Item item) {
                          holder.setImage(R.id.iv_image, item.imgRes);
                          holder.setText(R.id.tv_name, item.name);
                      }
                  };
        mBanner.setAdapter(mAdapter);
    }
    pulic void setIndicator(){
        CircleIndicator circleIndicator = new RectIndicator(this, RectIndicator.SHAPE_CIRCLE);
        //设置指示器长和宽.
        circleIndicator.setIndicatorSize(10, 10);
        //设置指示器之间的间距
        circleIndicator.setItemMargin(20);
        //设置指示器显示在banner的底部的中间
        circleIndicator.setRules(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
        //设置指示器显示距离banner底部10dp
        circleIndicator.setMargins(0, 0, 0, 10);
        mBanner.addIndicator(circleIndicator);
    }

    //刷新数据
    public void refreshDatas(View view) {
        mAdapter.setDatas(DataHelper.getItems());
    }
```