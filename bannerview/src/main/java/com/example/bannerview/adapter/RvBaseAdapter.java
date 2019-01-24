/*
 * Copyright (c) 2018 Beijing Chinaway Technologies Co., Ltd. All rights reserved.
 */
package com.example.bannerview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:RecyclerView 多样式adapter封装.
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */

public abstract class RvBaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    public static final String VERTICAL = "vertical";
    public static final String HORIZONTAL = "horizontal";
    protected List<T> mDatas = new ArrayList<>();
    protected Context mContext;

    public RvBaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position), holder.mItemLayoutId, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutId(position, mDatas.get(position));
    }

    /**
     * 绑定适配器，以及设置LinearLayoutManager.
     *
     * @param rv          需要绑定的recyclerView
     * @param typeManager type的类型
     */
    public void attachLinearRv(RecyclerView rv, String typeManager) {
        switch (typeManager) {
            case VERTICAL:
                rv.setLayoutManager(new LinearLayoutManager(mContext));
                break;
            case HORIZONTAL:
            default:
                rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                break;
        }
        rv.setAdapter(this);
        //设置增加或删除条目的动画
        rv.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * 设置新数据，并更新.
     *
     * @param datas 新数据源
     */
    public void setDatas(List<T> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }

        notifyDataSetChanged();
    }

    /**
     * 根据当前position，返回当前position的item的布局资源Id.
     *
     * @param position position
     * @return
     */
    public abstract @LayoutRes int getLayoutId(int position, T item);

    /**
     * 处理数据填充等逻辑.
     *
     * @param holder   holder
     * @param item     item
     * @param layoutId 布局资源id
     * @param position position
     */
    public abstract void convert(ViewHolder holder, T item, int layoutId, int position);

}
