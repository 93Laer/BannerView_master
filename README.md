# 支持横向、竖向自动无限滚动和自定义指示器的广告条BannerView和淘宝头条效果

**前言：** 有那么多的Banner框架，为什么还要重新写呢？其实不是由于第三方的太重、不好维护等原因，最致命的问题是第三方banner框架大多使用ViewPager实现，而使用这种方式一般会伴随两个问题：
- 1、如果适配器中getItemCount()放回一个很大的数量`return  Integer.MAX_VALUE`，可以很简单实现无线滚动，但是会产生ANR异常（我们公司项目以前就是这种方式实现）,如果你也是或是你是用的第三方框架也是，那么你需要注意了
- 2、如果适配器中getItemCount()放回一个是真实size的倍数（很多框架都是返回3倍，具体原因不细讲了），代码判断循环，这种确实解决了无线循环，也不会导致ANR，但是会有一个新的显示问题，就是当你滑动到最后一条或是第一条需要显示下一条的时候，会生硬的直接换成下一页内容，会感觉页面切换很生硬


### 先来一波效果图展示（demo偏丑，无关紧要，注意效果）

![普通横向banner.gif](https://upload-images.jianshu.io/upload_images/1744409-3a714b1aaf6d1f51.gif?imageMogr2/auto-orient/strip)

![3d横向banner.gif](https://upload-images.jianshu.io/upload_images/1744409-62a6c7cd673ad634.gif?imageMogr2/auto-orient/strip)

![竖向3d.gif](https://upload-images.jianshu.io/upload_images/1744409-a02d3f38cc71bb05.gif?imageMogr2/auto-orient/strip)

![指示器.gif](https://upload-images.jianshu.io/upload_images/1744409-75025d8d470a891f.gif?imageMogr2/auto-orient/strip)

![头条.gif](https://upload-images.jianshu.io/upload_images/1744409-ddbde36962ce89e8.gif?imageMogr2/auto-orient/strip)


### 属性介绍
```
 <!--广告条-->
    <declare-styleable name="BannerView">
        <!--时间-->
        <attr name="interval" />
        <!--方向-->
        <attr name="orientation" format="enum">
            <enum name="horizontal" value="0" />
            <enum name="vertical" value="1" />
        </attr>
        <!--自动播放-->
        <attr name="autoPlaying" format="boolean"/>
        <!--item间的间距-->
        <attr name="itemSpace" format="integer"/>
        <!--中间的item大小和设置item大小的比例-->
        <attr name="centerScale" format="float"/>
        <!--手指滑动时，banner移动的的速度-->
        <attr name="moveSpeed" format="float"/>
        <!--数据为空时，展示的view-->
        <attr name="empty_layout" format="reference" />

    </declare-styleable>
```

### 接入并使用框架
##### 添加依赖
```
//在工程的gradle中添加jitpack仓库
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
            //在module的gradle中添加依赖,注意将“LatestRelease”换成具体的版本号，建议使用最新版本号
	        implementation 'com.github.93Laer:BannerView_master:LatestRelease'
	}

```
**重新同步一下**
##### 使用步骤：3+2 

**第一步：布局**
```
<com.example.laitianbing.bannerview_master.banner_view.BannerView
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:background="@color/bannerBackground"
        app:autoPlaying="true"
        app:empty_layout="@layout/empty_view"
        app:interval="3000" />
```
**第二步：找到控件**
`mBanner = findViewById(R.id.banner);`

**第三部：绑定适配器，并放入初始化数据源**
```
 mAdapter = new BannerAdapter<Item>(this, itemLayout, items) {
            @Override
            public void convert(ViewHolder holder, final Item item) {
                //书写自己的处理逻辑
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
```

**第四部：添加指示器（不显示指示器就没有此步骤）**
```
        //创建指示器
        mCircleIndicator = new RectIndicator(this, RectIndicator.SHAPE_CIRCLE);
        //设置指示器长和宽.
        mCircleIndicator.setIndicatorSize(10, 10);
        //设置指示器之间的间距
        mCircleIndicator.setItemMargin(20);
        //设置指示器显示在banner的底部的中间
        mCircleIndicator.setRules(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
        //设置指示器显示距离banner底部10dp
        mCircleIndicator.setMargins(0, 0, 0, 10);
        //添加指示器
        mBanner.addIndicator(mCircleIndicator);
```

**第五部：刷新数据（不刷新数据就没有此步骤）**
`mAdapter.setDatas(DataHelper.getItems());`

**使用进阶提示：**
> - 1、在出厂前已将指示器分为图形指示器和文本指示器两类,如果想自定义图形指示器则直接继承`BaseIndicator`即可
> - 2、在添加指示器的时候，你可以像在RelativeLayout中设置子view的位置一样设置指示器的位置，换句话说 **你可以指定指示器显示在banner中的任意位置**
> - 3、可以自定义指示器，但是 **必须继承view且实现Indicator接口** ，换句话说任意View，只要实现Indicator接口，它就可以成为指示器，如果还不懂，那就看`TextIndicator`实现
> - 4、如果项目中要实现想淘宝，京东的头条那种垂直滚动，使用该框架就可以轻松完成


### 博客链接：[支持横向、竖向无限滚动和自定义指示器的广告条BannerView和淘宝头条效果](https://www.jianshu.com/p/c700ddb0a859)
