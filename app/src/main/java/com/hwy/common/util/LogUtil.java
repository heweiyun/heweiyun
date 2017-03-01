package com.hwy.common.util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.hwy.BuildConfig;
import com.hwy.HwyApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 日志记录工具类 可以通过修改是否启用调试模式、日志级别、是否记录日志到文件, 灵活的记录日志。
 * 记录日志到文件需要配置权限：android.permission.WRITE_EXTERNAL_STORAGE
 * 只有在根目录有xtc_crash.config文件才会记录WARN和ERROR级别的日志
 *
 * @author heweiyun
 * @version [1.0.0.0, 2014-6-12]
 */
public class LogUtil {
    public static final String TAG = "LogUtil";

    /**
     * 是否启用调试模式, 如果为false不记录任何日志
     */
    private static final boolean ADB;

    /**
     * 日志级别,低于该级别的不会打印
     */
    private static final int LOG_DEGREE;

    /**
     * 是否需要记录日志到文件
     */
    private static boolean IS_NEED_FILELOG;

    static {
        if (BuildConfig.DEBUG) {
            ADB = true;
            LOG_DEGREE = Log.VERBOSE;
            IS_NEED_FILELOG = false;
        } else {
            ADB = false;
            LOG_DEGREE = Log.WARN;
            IS_NEED_FILELOG = false;
        }
    }

    /**
     * 日志文件名称绝对路径
     */
    private static final String LOGFILENAME = HwyApplication.getApplication().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/log/log" +
            ".log";

    /**
     * 对于sd卡未准备好, 允许缓存的最大日志条数
     */
    private static final int MAX_CACHE_SIZE = 128;

    /**
     * 记录V级别日志 在记录V级别日志时调用, 如果日志配置为不记录日志或日志级别高于V, 不记录日志
     *
     * @param tag 日志tag
     * @param msg 日志信息, 支持动态传参可以是一个或多个(避免日志信息的+操作过早的执行)
     * @author heweiyun
     */
    public static void v(String tag, String... msg) {
        if (ADB && LOG_DEGREE <= Log.VERBOSE) {
            String msgStr = combineLogMsg(msg);

            Log.v(tag, msgStr);

            writeLogToFile(Log.VERBOSE, tag, msgStr, null);
        }
    }



    /**
     * 记录D级别日志 在记录D级别日志时调用, 如果日志配置为不记录日志或日志级别高于D, 不记录日志
     *
     * @param tag 日志tag
     * @param msg 日志信息, 支持动态传参可以是一个或多个(避免日志信息的+操作过早的执行)
     * @author heweiyun
     */
    public static void d(String tag, String... msg) {
        if (ADB && LOG_DEGREE <= Log.DEBUG) {
            String msgStr = combineLogMsg(msg);
            Log.d(tag, msgStr);

            writeLogToFile(Log.DEBUG, tag, msgStr, null);
        }
    }



    /**
     * 记录I级别日志 在记录I级别日志时调用, 如果日志配置为不记录日志或日志级别高于I, 不记录日志
     *
     * @param tag 日志tag
     * @param msg 日志信息, 支持动态传参可以是一个或多个(避免日志信息的+操作过早的执行)
     * @author heweiyun
     */
    public static void i(String tag, String... msg) {
        if (ADB && LOG_DEGREE <= Log.INFO) {
            String msgStr = combineLogMsg(msg);

            Log.i(tag, msgStr);

            writeLogToFile(Log.INFO, tag, msgStr, null);
        }
    }


    /**
     * 记录W级别日志 在记录W级别日志时调用, 如果日志配置为不记录日志或日志级别高于W, 不记录日志
     *
     * @param tag 日志tag
     * @param msg 日志信息, 支持动态传参可以是一个或多个(避免日志信息的+操作过早的执行)
     * @author heweiyun
     */
    public static void w(String tag, String... msg) {
        if (ADB && LOG_DEGREE <= Log.WARN) {
            String msgStr = combineLogMsg(msg);

            Log.w(tag, msgStr);

            writeLogToFile(Log.WARN, tag, msgStr, null);
        }
    }



    /**
     * 记录E级别日志 在记录E级别日志时调用, 如果日志配置为不记录日志或日志级别高于E, 不记录日志
     *
     * @param tag 日志tag
     * @param msg 日志信息, 支持动态传参可以是一个或多个(避免日志信息的+操作过早的执行)
     * @author heweiyun
     */
    public static void e(String tag, String... msg) {
        if (ADB && LOG_DEGREE <= Log.ERROR) {
            String msgStr = combineLogMsg(msg);

            Log.e(tag, msgStr);

            writeLogToFile(Log.ERROR, tag, msgStr, null);
        }
    }


    /**
     * 记录E级别日志 在记录E级别日志时调用, 如果日志配置为不记录日志或日志级别高于E, 不记录日志
     *
     * @param tag 日志tag
     * @param tr  异常对象
     * @param msg 日志信息, 支持动态传参可以是一个或多个(避免日志信息的+操作过早的执行)
     * @author heweiyun
     */
    public static void e(String tag, Throwable tr, String... msg) {
        if (ADB && LOG_DEGREE <= Log.ERROR) {
            String msgStr = combineLogMsg(msg);

            Log.e(tag, msgStr, tr);

            writeLogToFile(Log.ERROR, tag, msgStr, tr);

        }
    }



    /**
     * 组装动态传参的字符串 将动态参数的字符串拼接成一个字符串
     *
     * @param msg 动态参数
     * @return 拼接后的字符串
     * @author heweiyun
     */
    private static String combineLogMsg(String... msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Pid:").append(Thread.currentThread().getId()).append("]");
        sb.append(getCaller(3)).append(": ");
        if (null != msg) {
            for (String s : msg) {
                sb.append(s);
            }
        }
        return sb.toString();

    }

    private static String getCaller(int index) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = index; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName() + "-" + trace[i].getLineNumber();
                break;
            }

        }
        return caller;
    }

    /**
     * 锁对象, 在同步模块中使用
     */
    private static Object lock = new Object();

    /**
     * 待记录到文件的日志队列, 需要支持同步
     */
    private static Queue<String> Logs = new ConcurrentLinkedQueue<String>();

    /**
     * 日志级别与其对应的字符标签
     */
    private static SparseArray<String> degreeLabel = new SparseArray<String>();

    /**
     * 初始化日志级别与其对应的字符标签
     */
    static {
        degreeLabel.put(Log.VERBOSE, "V");
        degreeLabel.put(Log.DEBUG, "D");
        degreeLabel.put(Log.INFO, "I");
        degreeLabel.put(Log.WARN, "W");
        degreeLabel.put(Log.ERROR, "E");
    }

    /**
     * 记录日志到文件 如果配置成需要记录日志到文件, 需要将日志数据拼接成一条日志记录, 首先放入到待记录的日志队列中, 再由异步线程记录到文件中。
     * 日志格式定义为：yyyy-MM-dd HH:mm:ss.SSS, <D>degree, <T>tag,
     * <M>message,<E>exception info
     *
     * @param degree 日志级别
     * @param tag    日志标签
     * @param msg    日志信息
     * @param tr     异常
     * @author heweiyun
     */
    @SuppressLint("SimpleDateFormat")
    private static void writeLogToFile(int degree, String tag, String msg, Throwable tr) {
        if (IS_NEED_FILELOG) {
            StringBuffer sb = new StringBuffer();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sb.append(df.format(Calendar.getInstance().getTime())).append(", <D>").append(degreeLabel.get(degree))
                    .append(", <T>")
                    .append(tag).append(", <M>").append(msg);
            writeToFile(sb,tr);
        }
    }

    public static void logToFile(int degree, String tag, String msg, @Nullable Throwable tr){
        StringBuffer sb = new StringBuffer();
        sb.append("[Pid:").append(Thread.currentThread().getId()).append("]");
        sb.append(getCaller(2)).append(": ");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sb.append(df.format(Calendar.getInstance().getTime())).append(", <D>").append(degreeLabel.get(degree))
                .append(", <T>")
                .append(tag).append(", <M>").append(msg);
        writeToFile(sb,tr);
    }


    private static void writeToFile(StringBuffer sb, @Nullable Throwable tr){
        // 如果有异常信息, 需要拼接异常信息, 拼接所有的堆栈信息
        if (null != tr) {
            StackTraceElement[] stacks = tr.getStackTrace();
            if (null != stacks) {
                sb.append(", <E>").append(tr.getMessage()).append("\r\n");
                for (int i = 0; i < stacks.length; i++) {
                    sb.append("\t\tat ").append(stacks[i].getClassName()).append(".").append(stacks[i]
                            .getMethodName()).append("(")
                            .append(stacks[i].getClassName()).append(".java").append(" ").append(stacks[i]
                            .getLineNumber()).append(")")
                            .append("\r\n");
                }
            }
        }

        // 将日志信息增加到队列中
        Logs.add(sb.toString());

        // 通知日志线程写文件
        synchronized (lock) {
            lock.notify();
        }

        // 如果日志线程没有初始化, 需要初始化并启动
        if (null == logThread) {
            logThread = new LogThread();
            logThread.start();
        }
    }

    /**
     * 日志文件的输出流对象
     */
    private static BufferedWriter bw;

    /**
     * 日志线程对象
     */
    private static LogThread logThread = null;

    /**
     * 日志线程类 用于记录日志到文件的线程类
     *
     * @author heweiyun
     * @version [1.0.0.0, 2011-3-16]
     */
    private static class LogThread extends Thread {

        /**
         * 线程是否运行的标识位, 在start线程时置为true, 在终止线程(halt方法)时置为false
         */
        boolean isRunning = false;

        @Override
        public synchronized void start() {
            isRunning = true;

            // 启动日志线程时初始化文件输出流
            initWriter();

            super.start();
        }

        @Override
        public void run() {
            while (isRunning) {
                String log = null;

                if (null == bw) {
                    initWriter();
                }
                // writer初始化好了才记录日志
                else {

                    // 循环从日志队列中取出日志, 记录到文件中
                    while (null != (log = Logs.poll())) {

                        try {
                            bw.write(log);
                            bw.write("\r\n");
                            bw.flush();
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage(), e);

                            // 如果发生异常, 可能是sd卡不可用, 并尝试重新初始化输出流
                            initWriter();

                        }

                    }
                }

                // 处理完所有队列中的日志后, wait, 在有日志进入队列时被唤醒
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }

            }
        }

        /**
         * 终止线程时调用 用于终止线程, 将运行状态置为false
         *
         * @author heweiyun
         */
        @SuppressWarnings("unused")
        public synchronized void halt() {
            isRunning = false;
        }

    }

    /**
     * 初始化文件输出流对象 如果文件输出流已经初始化过, 需要先关闭输出流, 再创建新的输出流对象; 否则直接创建
     *
     * @author heweiyun
     */
    private static void initWriter() {

        // 只缓存允许的日志数目
        if (Logs.size() > MAX_CACHE_SIZE) {
            Logs.clear();
        }

        // 如果输出流初始化过, 需要先关闭输出流, 释放资源
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        try {

            @SuppressWarnings("unused")
            boolean mExternalStorageAvailable = false;
            boolean mExternalStorageWriteable = false;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                mExternalStorageAvailable = mExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                mExternalStorageAvailable = true;
                mExternalStorageWriteable = false;
            } else {
                // Something else is wrong. It may be one of many other states,
                // but all we need
                // to know is we can neither read nor write
                mExternalStorageAvailable = mExternalStorageWriteable = false;
            }

            if (mExternalStorageWriteable) {

                File dir = new File(LOGFILENAME.substring(0, LOGFILENAME.lastIndexOf("/")));
                dir.mkdirs();
                if (!dir.exists()) {
                    IS_NEED_FILELOG = false;
                    return;
                }

                // 创建文件输出流
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOGFILENAME, true), "UTF-8"));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }
}
