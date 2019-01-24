package com.example.bannerview.banner_view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.example.bannerview.adapter.ViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 * @param <T>
 */
public abstract class BannerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private WeakReference<BannerView> mBannerWR;
    private List<T> mDatas = new ArrayList<>();
    private Context mContext;
    private int mItemResId;

    public BannerAdapter(Context context, @LayoutRes int itemResId, List<T> datas) {
        mItemResId = itemResId;
        mContext = context;
        if (null != datas) {
            mDatas.addAll(datas);
        }
    }

    /**
     * 设置Bannerview
     *
     * @param bannerView
     */
    void setBannerView(BannerView bannerView) {
        if (null != bannerView) {
            bannerView.setPagers(mDatas.size());
            mBannerWR = new WeakReference<>(bannerView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(mContext, viewGroup, mItemResId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 处理数据填充等逻辑.
     *
     * @param holder holder
     * @param item   item
     */
    public abstract void convert(ViewHolder holder, T item);


    /**
     * 设置新数据，并更新.
     *
     * @param datas 新数据源
     */
    public void setDatas(List<T> datas) {
        WeakReference<BannerView> bannerWR = mBannerWR;
        List<T> list = mDatas;
        list.clear();
        if (datas != null) {
            list.addAll(datas);
        }
        if (null != bannerWR) {
            BannerView bannerView = bannerWR.get();
            if (null != bannerView) {
                bannerView.setPagers(list.size());
            }
        }
        notifyDataSetChanged();
    }
}
