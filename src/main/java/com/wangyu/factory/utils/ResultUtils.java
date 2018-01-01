/**
 * create by InteliJ Idea
 * <p>
 * ****************************************************************************
 * Change Log
 * <p>
 * 1. wangyu create me at 2018年01月01日23:45 for class DESC. 2.
 * ****************************************************************************
 * Copyright (c) 2018, www.bibenet.com All Rights Reserved.O(∩_∩)O
 */
package com.wangyu.factory.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author wangyu
 */
public class ResultUtils {

    /**
     * 随机取出若干个值
     * 组合成新的数据
     *
     * @param array 传入任意类型的数据
     * @return 新的数据
     */
    public static <T> T[] randomSubarray(T[] array) {
        int length = array.length;
        int args1 = RandomUtils.nextInt(0, length);
        int args2 = RandomUtils.nextInt(0, length);
        int start = NumberUtils.min(args1, args2);
        int end = NumberUtils.max(args1, args2);
        if (end == start && end == length) {
            start--;
        }
        return ArrayUtils.subarray(array, start, end);
    }
}
