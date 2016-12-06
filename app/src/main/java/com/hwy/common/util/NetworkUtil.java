package com.hwy.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/6 10:47
 * 修改人：heweiyun
 * 修改时间：2016/12/6 10:47
 * 修改备注：
 */

public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}
