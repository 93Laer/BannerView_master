package com.example.bannerview.banner_view.indicator;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 圆形Drawable,可自定义背景颜色。
 *
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class CircleDrawable extends Drawable {
    private RectF mRect;
    private Paint mPaint;

    public CircleDrawable(float widthPx, float heightPx, @ColorInt int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mRect = new RectF(0, 0, widthPx, heightPx);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(mRect, mRect.height() / 2, mRect.height() / 2, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
