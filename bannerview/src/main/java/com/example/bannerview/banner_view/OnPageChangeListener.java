package com.example.bannerview.banner_view;

import android.support.v7.widget.RecyclerView;

public interface OnPageChangeListener {
    /**
     * 滚动状态改变监听.
     * @param recyclerView
     * @param newState
     */
    void onScrollStateChanged(RecyclerView recyclerView, int newState);

    /**
     * banner滚动监听。
     * @param recyclerView
     * @param dx
     * @param dy
     */
    void onScrolled(RecyclerView recyclerView, int dx, int dy);

    /**
     * pager选中监听.
     * @param index
     */
    void onPageSelected(int index);
}
