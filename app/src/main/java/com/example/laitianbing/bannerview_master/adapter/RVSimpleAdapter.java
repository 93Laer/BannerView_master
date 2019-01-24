/*
 * Copyright (c) 2018 Beijing Chinaway Technologies Co., Ltd. All rights reserved.
 */
package com.example.laitianbing.bannerview_master.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * 描述: RecyclerView 通用适配器.
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 *
 * @param <T>
 */
public abstract class RVSimpleAdapter<T> extends RvBaseAdapter<T> {

    private int mItemLayoutId;

    public RVSimpleAdapter(Context context, List datas, @LayoutRes int itemLayoutId) {
        super(context, datas);
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getLayoutId(int position, T item) {
        return mItemLayoutId;
    }

    @Override
    public void convert(ViewHolder holder, T item, int layoutId, int position) {
        convert(holder, item);
    }

    public abstract void convert(ViewHolder holder, T item);

}
