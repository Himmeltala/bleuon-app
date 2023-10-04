package com.bleuon.utils;

import java.sql.Timestamp;

/**
 * @description: 日期工具
 * @package: com.bleuon.utils
 * @author: zheng
 * @date: 2023/10/1
 */
public class DateUtil {

    public static boolean isAfter(Timestamp timestamp) {
        if (timestamp == null) return true;

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.after(timestamp)) {
            return true;
        } else if (currentTimestamp.equals(timestamp)) {
            return false;
        } else {
            return false;
        }
    }

}
