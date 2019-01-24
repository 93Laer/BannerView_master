

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