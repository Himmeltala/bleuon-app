package com.bleuon.utils;

import java.util.Random;

public class NumbersUtil {

    /**
     * 生成一个在 [min, max] 范围内的随机数
     *
     * @param min 100000000000
     * @param max 999999999999
     * @return [100000000000, 999999999999]
     */
    public static long random(long min, long max) {
        Random random = new Random();
        return min + (long) (random.nextDouble() * (max - min + 1));
    }

}
