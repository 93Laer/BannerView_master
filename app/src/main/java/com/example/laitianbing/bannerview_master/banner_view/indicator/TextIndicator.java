package com.example.laitianbing.bannerview_master.banner_view.indicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 文本指示器，继承自TextView.所以设置文字大小，背景，字体颜色等均和TextView一样
 *
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class TextIndicator extends android.support.v7.widget.AppCompatTextView implements Indicator {
    //默认垂直方向上面的padding.
    public static final int PADDING_V = 2;
    //默认水平方向上面的padding.
    public static final int PADDING_H = 5;
    public static final String FORMAT_CONTENT = "%1$d/%2$d ";
    public static final String DEF_BG_COLOR = "#4D383838";
    private CircleDrawable mDrawable;
    //pager总数
    protected int mPagerSize;
    //水平和垂直方向上面的内部间距.
    private int mPaddingH, mPaddingV;
    //背景颜色.
    private int mBgColor;

    /**
     * @param context
     * @param bgColor     背景颜色.
     */
    public TextIndicator(Context context, int bgColor) {
        super(context);
        this.mBgColor = bgColor;
        mPaddingV = Utils.dp2px(context, PADDING_V);
        mPaddingH = Utils.dp2px(context, PADDING_H);
        setTextColor(Color.WHITE);
        setGravity(Gravity.CENTER);
        setPadding(mPaddingH, mPaddingV, mPaddingH, mPaddingV);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setPagerSize(int pagerSize) {
        mPagerSize = pagerSize;
        if (pagerSize > 0) {
            setVisibility(VISIBLE);
            setText(String.format(FORMAT_CONTENT, 1, pagerSize));
        } else {
            setText("");
            setVisibility(GONE);
        }
    }

    private RelativeLayout.LayoutParams getParams() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        if (null == params) {
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
        left = left < PADDING_H ? left + PADDING_H : left;
        top = top < PADDING_H ? top + PADDING_H : top;
        right = right < PADDING_H ? right + PADDING_H : right;
        bottom = bottom < PADDING_H ? bottom + PADDING_H : bottom;
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

    @Override
    public void setHorizontal(boolean horizontal) {

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onPageSelected(int index) {
        int bgColor = mBgColor;
        CircleDrawable drawable = mDrawable;
        String content = String.format(FORMAT_CONTENT, index + 1, mPagerSize);
        float textWidth = getPaint().measureText(content);
        setText(content);
        if (null == drawable) {
            if (bgColor == 0) {
                bgColor = Color.parseColor(DEF_BG_COLOR);
            }
            drawable = new CircleDrawable(textWidth + mPaddingH * 2, getHeight(), bgColor);
            setBackgroundDrawable(drawable);
        }
    }
}
