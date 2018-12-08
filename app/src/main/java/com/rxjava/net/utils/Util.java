package com.rxjava.net.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lenovo on 2018/12/3.
 */

public class Util {

    public static String millisString(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date(millis));
    }




}
