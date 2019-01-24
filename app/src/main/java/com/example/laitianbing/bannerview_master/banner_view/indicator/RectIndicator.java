package com.example.laitianbing.bannerview_master.banner_view.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

/**
 * 圆形指示器。
 *
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class RectIndicator extends BaseIndicator {
    /**
     * 圆形
     */
    public static final int SHAPE_CIRCLE = 0;
    /**
     * 长方形
     */
    public static final int SHAPE_RECTANGLE = 1;
   //默认未选中颜色
    public static final String DEF_UN_SELETE_COLOR = "#CC84849A";
    //默认选中颜色
    public static final String DEF_SELETE_COLOR = "#CC532FE6";
    //默认指示器大小.
    public static final int DEF_INDICATOR_SIZE = 12;
    //默认item之间间距
    public static final int DEF_ITEM_MARGIN = 8;

    //选中和未选择的颜色
    protected int mUnSeleteColor, mSeleteColor;

    //形状
    private @Shape int mShape;

    public RectIndicator(Context context, @Shape int shape) {
        super(context);
        mShape = shape;
        mUnSeleteColor = Color.parseColor(DEF_UN_SELETE_COLOR);
        mSeleteColor = Color.parseColor(DEF_SELETE_COLOR);
        mIndicatorWidth = mIndicatorHeight = dp2px(DEF_INDICATOR_SIZE);
        mItemMargin = dp2px(DEF_ITEM_MARGIN);
    }

    /**
     * 设置指示器间的间距.
     *
     * @param itemMargin 单位：dp
     * @return
     */
    public RectIndicator setItemMargin(int itemMargin) {
        mItemMargin = dp2px(itemMargin);
        return this;
    }

    /**
     * 设置未选中颜色
     *
     * @param unSeleteColor
     * @return
     */
    public RectIndicator setUnSeleteColor(@ColorInt int unSeleteColor) {
        mUnSeleteColor = unSeleteColor;
        return this;
    }

    /**
     * 设置选中的指示器显示的颜色
     *
     * @param seleteColor
     * @return
     */
    public RectIndicator setSeleteColor(@ColorInt int seleteColor) {
        mSeleteColor = seleteColor;
        return this;
    }

    /**
     * 指示器宽度.
     *
     * @param indicatorWidth
     * @return
     */
    public RectIndicator setIndicatorSize(int indicatorWidth, int indicatorHeight) {
        if (mShape == SHAPE_CIRCLE) {
            mIndicatorWidth = mIndicatorHeight = dp2px(Math.max(indicatorHeight, indicatorWidth));
        } else {
            mIndicatorWidth = dp2px(indicatorWidth);
            mIndicatorHeight = dp2px(indicatorHeight);
        }
        return this;
    }

    @Override
    protected void onDrawView(Canvas canvas) {
        //绘制默认
        drawDefIndicator(canvas);
        //绘制选中
        drawSeleteView(canvas);
    }

    //绘制默认
    @NonNull
    private void drawDefIndicator(Canvas canvas) {
        float indicatorW = mIndicatorWidth;
        float indicatorH = mIndicatorHeight;
        float itemMargin = mItemMargin;
        Paint paint = mPaint;
        @Shape int shape = mShape;

        canvas.save();
        if (shape == SHAPE_CIRCLE) {
            indicatorW = indicatorH = Math.min(indicatorW, indicatorH);
            canvas.translate(indicatorW / 2, indicatorH / 2);
        }
        paint.setColor(mUnSeleteColor);
        for (int i = 0; i < mPagerSize; i++) {

            if (shape == SHAPE_CIRCLE) {
                canvas.drawCircle(0, 0, indicatorH / 2, paint);
            } else if (shape == SHAPE_RECTANGLE) {
                canvas.drawRect(0, 0, indicatorW, indicatorH, paint);
            }

            float translationX = 0, translationY = 0;

            if (mHorizontal) {
                translationX = itemMargin + indicatorW;
            } else {
                translationY = itemMargin + indicatorH;
            }
            canvas.translate(translationX, translationY);
        }
        canvas.restore();
    }

    protected void drawSeleteView(Canvas canvas) {
        @Shape int shape = mShape;
        float indicatorW = mIndicatorWidth;
        float indicatorH = mIndicatorHeight;
        float offset = mOffset;
        Paint paint = mPaint;

        paint.setColor(mSeleteColor);
        if (shape == SHAPE_CIRCLE) {
            float x, y;
            if (mHorizontal) {
                x = indicatorW / 2 + offset;
                y = indicatorH / 2;
            } else {
                x = indicatorW / 2;
                y = indicatorH / 2 + offset;
            }

            float radius = Math.max(indicatorW, indicatorH) / 2;
            canvas.drawCircle(x, y, radius, paint);
        } else if (shape == SHAPE_RECTANGLE) {
            canvas.save();
            if (mHorizontal) {
                canvas.translate(offset, 0);
            } else {
                canvas.translate(0, offset);
            }
            canvas.drawRect(0, 0, indicatorW, indicatorH, paint);
            canvas.restore();
        }
    }

    @IntDef({SHAPE_CIRCLE, SHAPE_RECTANGLE})
    public @interface Shape {

    }

}
