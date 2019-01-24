/*
 * Copyright (c) 2018 Beijing Chinaway Technologies Co., Ltd. All rights reserved.
 */
package com.example.laitianbing.bannerview_master.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * RecyclerView.ViewHolder.
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public int mItemLayoutId;

    /**
     * @param context      context
     * @param itemLayoutId 布局资源id
     */
    public ViewHolder(Context context, ViewGroup parent, @LayoutRes int itemLayoutId) {
        super(LayoutInflater.from(context).inflate(itemLayoutId, parent, false));
        mViews = new SparseArray<>();
        mItemLayoutId = itemLayoutId;
    }

    /**
     * 通过id获取view.
     *
     * @param viewId 需要查找的view的ID
     * @param <T>    T 查找view的类型
     * @return 返回view
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找
        View view = mViews.get(viewId);
        if (view == null) {
            // 直接从ItemView中找
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的文本.
     *
     * @param id      该view的ID
     * @param content 需要设置的内容
     * @return 返回当前holder
     */
    public ViewHolder setText(@IdRes int id, SpannableString content) {
        TextView view = getView(id);
        view.setText(content);
        return this;
    }

    /**
     * 设置TextView的文本.
     *
     * @param id      该view的ID
     * @param content 需要设置的内容
     * @return 返回当前holder
     */
    public ViewHolder setText(@IdRes int id, String content) {
        TextView view = getView(id);
        view.setText(content);
        return this;
    }

    /**
     * 设置TextView的文本.
     *
     * @param id    view的ID
     * @param resId 设置的资源ID
     * @return holder
     */
    public ViewHolder setText(@IdRes int id, @StringRes int resId) {
        TextView view = getView(id);
        view.setText(resId);
        return this;
    }

    /**
     * 设置格式化TextView的文本.
     *
     * @param cxt
     * @param id       view的ID
     * @param formatId 设置格式化资源ID
     * @param args
     * @return
     */
    public ViewHolder setFormatText(Context cxt, @IdRes int id, int formatId, Object... args) {
        ((TextView) getView(id)).setText(cxt.getString(formatId, args));
        return this;
    }

    /**
     * 设置imageview.
     *
     * @param url 图片的URL
     * @param id  imageview的ID
     * @return 返回holder
     */
    public ViewHolder setImg(String url, @IdRes int id) {
        ImageView view = getView(id);
        // TODO: 2018/9/27 根据自己的图片库做图片的加载
        return this;
    }

    /**
     * 设置点击事件.
     *
     * @param id       view的ID
     * @param listener
     * @return
     */
    public ViewHolder setOnClickListener(@IdRes int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
        return this;
    }

    /**
     * 设置view的可见性.
     *
     * @param visibility {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     * @param viewId     id
     */
    public void setViewsVisibility(int visibility, @IdRes int... viewId) {
        for (int i : viewId) {
            View view = getView(i);
            if (view != null) {
                view.setVisibility(visibility);
            }
        }
    }

    public void setImage(@IdRes int id, @DrawableRes int res) {
        ImageView imageView = getView(id);
        if (null != imageView) {
            imageView.setImageResource(res);
        }
    }

    /**
     * 为传入的TextView设置""值.
     *
     * @param ids 需要设置控制的控件id
     */
    public void resetAllTextView(int... ids) {
        for (int id : ids) {
            TextView view = getView(id);
            if (view != null) {
                view.setText("");
            }
        }
    }
}
