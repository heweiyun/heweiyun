package hwy.com.baselibrary.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 异常捕获工具类
 *
 * @author heweiyun
 * @version [1.0.0.0, 2014-6-12]
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private final String SD_ROOT = null;

    private static CrashHandler instance;
    private Context context;
    private HashMap<String, String> infoMap;
    private UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static CrashHandler getInstance() {
        if (null == instance) {
            instance = new CrashHandler();
        }
        return instance;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (null == infoMap) {
            infoMap = new HashMap<String, String>();
        } else {
            infoMap.clear();
        }
        getExceptionInfo(context, ex);
        saveInfo2SD();
        mDefaultHandler.uncaughtException(thread, ex);
        //关闭进程
        selfKill();
    }

    public void selfKill() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    private String getCrashRootDir(){
        if (TextUtils.isEmpty(SD_ROOT)){
            if (null != context){
                return context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
            }else {
                return Environment.getExternalStorageDirectory().getPath();
            }
        }else {
            return SD_ROOT;
        }
    }

    /**
     * 为程序绑定自定义的异常处理
     *
     * @param context
     * @author heweiyun
     */
    public void setCustomCrashHanler(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 获取异常与设备信息
     *
     * @param context
     * @param throwable
     * @return
     * @author heweiyun
     */
    private void getExceptionInfo(Context context, Throwable throwable) {

        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            infoMap.put("packageName", mPackageInfo.packageName);
            infoMap.put("versionName", mPackageInfo.versionName);
            infoMap.put("versionCode", "" + mPackageInfo.versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        infoMap.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        infoMap.put("MODEL", "" + Build.MODEL);
        infoMap.put("PRODUCT", "" + Build.PRODUCT);
        infoMap.put("time", getTime());
        obtainExceptionInfo(throwable);
    }


    /**
     * 保存信息到本地
     *
     * @author heweiyun
     */
    private void saveInfo2SD() {
        Log.d(TAG, "保留crash信息");
        StringBuffer sb = new StringBuffer();

        for (Entry<String, String> entry : infoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(getCrashRootDir() + File.separator + "crash" + File.separator);
            dir.mkdirs();
            File file = new File(dir.toString() + File.separator + getTime() + ".log");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "保留信息完成：" + sb.toString());

    }

    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     * @author heweiyun
     */
    private void obtainExceptionInfo(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (null != cause) {
            infoMap.put("exception_summary", cause.getMessage());
        } else {
            infoMap.put("exception_summary", throwable.getMessage());
        }
        infoMap.put("exception_detail", Log.getStackTraceString(throwable));

    }

    /**
     * 获取时间
     *
     * @return
     * @author heweiyun
     */
    @SuppressLint("SimpleDateFormat")
    private String getTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sf.format(new Date(System.currentTimeMillis()));
    }

}
