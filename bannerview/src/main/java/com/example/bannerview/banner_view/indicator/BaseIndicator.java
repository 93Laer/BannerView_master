package com.example.bannerview.banner_view.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
/**
 * RecyclerView.ViewHolder.
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public abstract class BaseIndicator extends View implements Indicator {
    public static String TAG;
    //画笔
    protected Paint mPaint;
    //当前显示的pager Index
    protected int mCurrentIndex;
    //pager总数
    protected int mPagerSize;
    //x轴上的偏移量
    protected float mOffset;
    //指示器宽度、高度
    protected float mIndicatorWidth, mIndicatorHeight;
    //item 间的间距
    protected float mItemMargin;
    //是否横向
    protected boolean mHorizontal;

    public BaseIndicator(Context context) {
        super(context);
        TAG = getClass().getSimpleName();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 设置是否横向.
     *
     * @param horizontal
     */
    public void setHorizontal(boolean horizontal) {
        mHorizontal = horizontal;
    }

    /**
     * 设置页面总数.
     *
     * @param pagerSize
     */
    public void setPagerSize(int pagerSize) {
        reset();
        mPagerSize = pagerSize;
        requestLayout();
        invalidate();
    }

    private RelativeLayout.LayoutParams getParams() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        if (params == null) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
        }
        return params;
    }

    /**
     * 设置margin
     */
    public void setMargins(int left, int top, int right, int bottom) {
        RelativeLayout.LayoutParams params = getParams();
        params.setMargins(Utils.dp2px(getContext(), left), Utils.dp2px(getContext(), top),
                Utils.dp2px(getContext(), right), Utils.dp2px(getContext(), bottom));
    }

    /**
     * 设置指示器显示规则.
     *
     * @param verbs {@link RelativeLayout#CENTER_IN_PARENT}
     */
    public void setRules(int... verbs) {
        RelativeLayout.LayoutParams params = getParams();
        for (int verb : verbs) {
            params.addRule(verb);
        }
    }


    protected void reset() {
        mCurrentIndex = 0;
        mPagerSize = 0;
        mOffset = 0;
    }

    protected float dp2px(int dp) {
        return Utils.dp2px(getContext(), dp);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    }

    @Override
    public void onPageSelected(int index) {
        if (mCurrentIndex == index) {
            return;
        }
        mCurrentIndex = index;
        if (mHorizontal) {
            mOffset = (mItemMargin + mIndicatorWidth) * index;
        } else {
            mOffset = (mItemMargin + mIndicatorHeight) * index;
        }

        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int pagers = mPagerSize;
        float indicatorW = mIndicatorWidth;
        float indicatorH = mIndicatorHeight;
        float itemMargin = mItemMargin;

        if (0 != pagers) {
            float width, height;
            if (mHorizontal) {
                width = indicatorW * pagers + itemMargin * (pagers - 1);
                height = indicatorH;
            } else {
                width = indicatorW;
                height = (indicatorH * pagers + itemMargin * (pagers - 1));
            }
            setMeasuredDimension((int) width, (int) height);

        } else {
            setMeasuredDimension(0, 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawView(canvas);
    }

    protected abstract void onDrawView(Canvas canvas);

}
