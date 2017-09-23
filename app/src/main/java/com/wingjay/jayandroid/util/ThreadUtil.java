package com.wingjay.jayandroid.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * ThreadUtil
 *
 * @author wingjay
 * @date 2017/09/11
 */
public class ThreadUtil {
    public static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());
    private static String processName = "";

    /**
     * 判断当前线程是否为主线程
     *
     * @return
     */
    public static boolean isUIThread() {
        Looper mLooper = Looper.myLooper();
        if (mLooper != null && mLooper == Looper.getMainLooper()) {
            return true;
        }
        return false;
    }

    public static void throwIfUIThread() {
        if (isUIThread()) {
            throw new RuntimeException("u can not do this action in UI Thread!");
        }
    }

    //public static String getCurProcessName(Context context) {
    //    if (!StringUtils.isEmpty(processName)) {
    //        return processName;
    //    }
    //    int pid = android.os.Process.myPid();
    //    android.app.ActivityManager mActivityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    //    List<RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
    //    if (processInfos != null && processInfos.size() > 0) {
    //        for (android.app.ActivityManager.RunningAppProcessInfo appProcess : processInfos) {
    //            if (appProcess.pid == pid) {
    //                processName = appProcess.processName;
    //                break;
    //            }
    //        }
    //    }
    //    if(StringUtils.isEmpty(processName)){
    //        processName = getCurProcessName();
    //    }
    //    return processName;
    //}

    //public static boolean isInUIProcess(Context context) {
    //    String curProcessName = getCurProcessName(context);
    //    if (!StringUtils.isEmpty(processName)) {
    //        return curProcessName.equals(context.getApplicationContext().getPackageName());
    //    }
    //    return false;
    //}


    public static Handler startHandlerThread(String threadName){
        HandlerThread thread  = new HandlerThread(threadName);
        thread.start();
        return new Handler(thread.getLooper());
    }

    private static String getCurProcessName() {
        BufferedReader cmdlineReader = null;
        try {
            cmdlineReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                    "/proc/" + android.os.Process.myPid() + "/cmdline"),
                "iso-8859-1"));
            int c;
            StringBuilder processName = new StringBuilder();
            while ((c = cmdlineReader.read()) > 0) {
                processName.append((char) c);
            }
            return processName.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cmdlineReader != null) {
                try {
                    cmdlineReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
