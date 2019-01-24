package com.example.laitianbing.bannerview_master.banner_view.indicator;


import com.example.laitianbing.bannerview_master.banner_view.OnPageChangeListener;

public interface Indicator extends OnPageChangeListener {
    /**
     *设置页面数量.
     * @param pagerSize
     */
    void setPagerSize(int pagerSize);

    /**
     * 设置横向.
     * @param horizontal
     */
    void setHorizontal(boolean horizontal);

}
