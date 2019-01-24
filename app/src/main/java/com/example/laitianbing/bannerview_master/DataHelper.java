package com.example.laitianbing.bannerview_master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 赖天兵
 * 简书：https://www.jianshu.com/u/2229fd214880
 * github:https://github.com/93Laer/RVAdapter-master
 */
public class DataHelper {
    private static final List<Integer> IMGS = Arrays.asList(
            R.drawable.item1,
            R.drawable.item2,
            R.drawable.item3,
            R.drawable.item4,
            R.drawable.item5);
    public static final String GIT_HUB = "https://github.com/93Laer";
    public static final String JIAN_SHU = "https://www.jianshu.com/u/2229fd214880";
    private static Random mRandom = new Random();

    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 0, size = mRandom.nextInt(IMGS.size()); i < size; i++) {
            String name = mRandom.nextBoolean() ? GIT_HUB : JIAN_SHU;
            Item e = new Item(name, IMGS.get(i));
            items.add(e);
        }
        return items;
    }


}
