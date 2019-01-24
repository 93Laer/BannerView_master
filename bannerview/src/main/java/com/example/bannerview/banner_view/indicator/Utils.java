package com.example.bannerview.banner_view.indicator;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static float getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
