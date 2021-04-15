package com.chat.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageUtil {
    /**
     * 将一组数据固定分组，每组n个元素
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static final Integer PAGE_SIZE_10000 = 10000;
    public static final Integer PAGE_SIZE_5000 = 5000;
    public static final Integer PAGE_SIZE_1000 = 1000;
    public static final Integer PAGE_SIZE_500 = 500;
    public static final Integer PAGE_SIZE_200 = 200;
    public static final Integer PAGE_SIZE_100 = 100;
    public static final Integer PAGE_SIZE_50 = 50;
    public static final Integer PAGE_SIZE_10 = 10;

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0) {
            return Arrays.asList();
        }
        List<List<T>> result = new ArrayList<>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }
}
