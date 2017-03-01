package hwy.com.baselibrary.common.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/5 14:26
 * 修改人：heweiyun
 * 修改时间：2016/12/5 14:26
 * 修改备注：
 */

public class AppUtil {

    public static String getVersionName(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0";
        }
    }
}
