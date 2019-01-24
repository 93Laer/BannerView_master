package com.example.laitianbing.bannerview_master;

import android.support.annotation.DrawableRes;

public class Item {
    public String url;
    public @DrawableRes int imgRes;

    public Item(String url, int imgRes) {
        this.url = url;
        this.imgRes = imgRes;
    }
}