package com.glofox.api.utils;

import java.util.Date;

/**
 * @author Charles on 17/05/2021
 */
public class DateUtil {

    public static boolean isStillValid(Date d1, Date d2){
        Date now = new Date();
        return d1.compareTo(now) <= 0 && d2.compareTo(now) >= 0;
    }
}
