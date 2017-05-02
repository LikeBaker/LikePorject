package com.like26th.likeproject.util.preference.CrashLog;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by liuzhen000 on 2016/5/30
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    /**
     * CrashHandler实例
     */

    private static CrashHandler instance;

    /**
     * 获取CrashHandler实例 ,单例模式
     */

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context ctx) {
        //mContext = ctx;
        //mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
//        ScreenManager.getScreenManager().popActivity(Pay.activity);
//         arg0.stop();
//         arg0.destroy();

        String logdir;
        if (Environment.getExternalStorageDirectory() != null) {
            logdir = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "likeLog" + File.separator + "log";


            File file = new File(logdir);
            boolean mkSuccess;
            if (!file.isDirectory()) {
                mkSuccess = file.mkdirs();
                if (!mkSuccess) {
                    mkSuccess = file.mkdirs();
                }
            }
            try {
                FileWriter fw = new FileWriter(logdir + File.separator + "error.log", true);
                fw.write(new Date(System.currentTimeMillis()) + "\n");
                StackTraceElement[] stackTrace = arg1.getStackTrace();
                fw.write(arg1.getMessage() + "\n");
                for (int i = 0; i < stackTrace.length; i++) {
                    fw.write("file:" + stackTrace[i].getFileName() + " class:" + stackTrace[i].getClassName()
                            + " method:" + stackTrace[i].getMethodName() + " line:" + stackTrace[i].getLineNumber()
                            + "\n");
                }
                fw.write("\n");
                fw.close();
            } catch (IOException e) {
                Log.e("crash handler", "load file failed...", e.getCause());
            }
        }
        arg1.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
