package com.example.bannerview.banner_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.bannerview.R;
import com.example.bannerview.banner_view.indicator.Indicator;
import com.example.bannerview.banner_view.layoutmanager.BannerLayoutManager;
import com.example.bannerview.banner_view.layoutmanager.CenterSnapHelper;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class BannerView extends RelativeLayout implements OnPageChangeListener {
    public static final String NO_DATA = "暂无数据";
    //暂无数据显示的默认字体大小
    public static final int DEF_TEXT_SIZE = 16;

    private int mAutoPlayDuration;//刷新间隔时间

    private RecyclerView mRecyclerView;

    private BannerLayoutManager mLayoutManager;

    private int WHAT_AUTO_PLAY = 1000;

    private boolean mHasInit;
    private int mBannerSize = 1;
    private int mCurrentIndex;
    private boolean mPlaying = false;

    private boolean mAutoPlaying = true;
    int mItemSpace;
    float mCenterScale;
    float mMoveSpeed;
    private List<OnPageChangeListener> mOnPageChangeListeners;

    //indicator
    private Indicator mIndicator;
    //空数据展示的资源ID
    private int mEmptyResId;
    //空界面view
    private View mEmptyView;
    private LayoutInflater mInflater;


    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            int currentIndex = mCurrentIndex;
            if (what == WHAT_AUTO_PLAY) {
                if (currentIndex == mLayoutManager.getCurrentPosition()) {
                    ++currentIndex;
                    mRecyclerView.smoothScrollToPosition(currentIndex);
                    mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPlayDuration);
                }
            }
            return false;
        }
    });

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mAutoPlayDuration = a.getInt(R.styleable.BannerView_interval, 4000);
        mAutoPlaying = a.getBoolean(R.styleable.BannerView_autoPlaying, true);
        mItemSpace = a.getInt(R.styleable.BannerView_itemSpace, 0);
        mCenterScale = a.getFloat(R.styleable.BannerView_centerScale, 1.0f);
        mMoveSpeed = a.getFloat(R.styleable.BannerView_moveSpeed, 1.0f);
        mEmptyResId = a.getLayoutDimension(R.styleable.BannerView_empty_layout, 0);
        int o = a.getInt(R.styleable.BannerView_orientation, OrientationHelper.HORIZONTAL);
        a.recycle();
        //轮播图部分
        mRecyclerView = new RecyclerView(context);
        LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, vpLayoutParams);
        mLayoutManager = new BannerLayoutManager(getContext(), o);
        mLayoutManager.setItemSpace(mItemSpace);
        mLayoutManager.setCenterScale(mCenterScale);
        mLayoutManager.setMoveSpeed(mMoveSpeed);
        mLayoutManager.setOnPageChangeListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new CenterSnapHelper().attachToRecyclerView(mRecyclerView);

    }

    // 设置是否禁止滚动播放
    public void setAutoPlaying(boolean isAutoPlaying) {
        this.mAutoPlaying = isAutoPlaying;
        setPlaying(this.mAutoPlaying);
    }

    public boolean isPlaying() {
        return mPlaying;
    }

    //设置当前图片缩放系数
    public void setCenterScale(float centerScale) {
        this.mCenterScale = centerScale;
        mLayoutManager.setCenterScale(centerScale);
    }

    //设置跟随手指的移动速度
    public void setMoveSpeed(float moveSpeed) {
        this.mMoveSpeed = moveSpeed;
        mLayoutManager.setMoveSpeed(moveSpeed);
    }

    //设置图片间距
    public void setItemSpace(int itemSpace) {
        this.mItemSpace = itemSpace;
        mLayoutManager.setItemSpace(itemSpace);
    }

    /**
     * 设置轮播间隔时间
     *
     * @param autoPlayDuration 时间毫秒
     */
    public void setAutoPlayDuration(int autoPlayDuration) {
        this.mAutoPlayDuration = autoPlayDuration;
    }

    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    protected synchronized void setPlaying(boolean playing) {
        Handler handler = mHandler;

        if (mAutoPlaying && mHasInit) {
            if (!mPlaying && playing) {
                handler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPlayDuration);
                mPlaying = true;
            } else if (mPlaying && !playing) {
                handler.removeMessages(WHAT_AUTO_PLAY);
                mPlaying = false;
            }
        }
    }


    /**
     * 设置轮播数据集
     */
    public void setAdapter(BannerAdapter adapter) {
        mHasInit = false;
        RecyclerView recyclerView = mRecyclerView;
        adapter.setBannerView(this);
        recyclerView.setAdapter(adapter);
        mBannerSize = adapter.getItemCount();
        mLayoutManager.setInfinite(mBannerSize >= 3);
        setPlaying(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dx != 0) {
                    setPlaying(false);
                }
                pageScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int first = mLayoutManager.getCurrentPosition();
                if (mCurrentIndex != first) {
                    mCurrentIndex = first;
                }

                scrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    setPlaying(true);
                }
            }
        });
        mHasInit = true;
    }

    private void addOnpageChangeListener(OnPageChangeListener listener) {
        List<OnPageChangeListener> onPageChangeListeners = mOnPageChangeListeners;
        if (null == onPageChangeListeners) {
            mOnPageChangeListeners = onPageChangeListeners = new ArrayList<>();
        }
        onPageChangeListeners.add(listener);
    }

    private void scrollStateChanged(@NonNull RecyclerView recyclerView, int state) {
        List<OnPageChangeListener> onPageChangeListeners = mOnPageChangeListeners;
        if (null != onPageChangeListeners) {
            for (OnPageChangeListener listener : onPageChangeListeners) {
                listener.onScrollStateChanged(recyclerView, state);
            }
        }
    }

    private void pageSelected(int index) {
        List<OnPageChangeListener> onPageChangeListeners = mOnPageChangeListeners;
        if (null != onPageChangeListeners) {
            for (OnPageChangeListener listener : onPageChangeListeners) {
                listener.onPageSelected(index);
            }
        }
    }

    private void pageScrolled(RecyclerView recyclerView, int dx, int dy) {
        List<OnPageChangeListener> onPageChangeListeners = mOnPageChangeListeners;
        if (null != onPageChangeListeners) {
            for (OnPageChangeListener listener : onPageChangeListeners) {
                listener.onScrolled(recyclerView, dx, dy);
            }
        }

    }

    /**
     * 添加指示器
     *
     * @param indicator
     */
    public void addIndicator(@NonNull Indicator indicator) {
        if (null != indicator && indicator instanceof View) {
            BannerLayoutManager manager = mLayoutManager;
            Indicator tmpIndicator = mIndicator;
            if (null != tmpIndicator) {
                removeView((View) tmpIndicator);
            }
            mIndicator = indicator;
            if (null != manager) {
                indicator.setHorizontal(manager.getOrientation() == OrientationHelper.HORIZONTAL);
            }
            indicator.setPagerSize(mBannerSize);
            addOnpageChangeListener(indicator);
            addView((View) indicator);
        }
    }

    private void showEmptyView() {
        LayoutInflater inflater = mInflater;
        View emptyView = mEmptyView;
        if (null == inflater) {
            mInflater = inflater = LayoutInflater.from(getContext());
        }

        if (emptyView == null) {
            if (mEmptyResId != 0) {
                emptyView = inflater.inflate(mEmptyResId, this, false);
            } else {
                TextView textView = new TextView(getContext());
                textView.setTextSize(DEF_TEXT_SIZE);
                textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                textView.setText(NO_DATA);
                emptyView = textView;
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(CENTER_IN_PARENT);
            emptyView.setLayoutParams(params);
            addView(emptyView);
            mEmptyView = emptyView;
        }
        emptyView.setVisibility(VISIBLE);
        mRecyclerView.setVisibility(GONE);
    }

    private void showBanner() {
        mRecyclerView.setVisibility(VISIBLE);
        if (null != mEmptyView) {
            mEmptyView.setVisibility(GONE);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPlaying(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPlaying(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            setPlaying(true);
        } else {
            setPlaying(false);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    }

    @Override
    public void onPageSelected(int position) {
        pageSelected(position);
    }


    /**
     * 设置banner页面数量.
     *
     * @param pagers
     */
    void setPagers(int pagers) {
        setPlaying(false);
        mBannerSize = pagers;
        Indicator indicator = mIndicator;
        BannerLayoutManager manager = mLayoutManager;
        mCurrentIndex = 0;
        if (pagers <= 0) {
            showEmptyView();
        } else {
            mRecyclerView.scrollToPosition(0);
            showBanner();
        }
        if (null != indicator) {
            indicator.setPagerSize(pagers);
        }
        if (null != manager) {
            manager.setInfinite(pagers >= 3);
            setPlaying(true);
        }
    }
}