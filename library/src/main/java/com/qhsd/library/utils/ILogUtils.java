package com.qhsd.library.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public abstract class ILogUtils {

    /**
     * 日志保存路径
     */
    protected abstract String getLogSavePath();
    /**
     * 日志开关
     */
    private static final boolean LOG_SWITCH = true;

    /**
     * 插入日志
     *
     * @param msg 需要插入日志的内容
     */
    public void writeLog(String msg) {
        if (msg == null) {
            return;
        }
        if (LOG_SWITCH) {
            File file = checkLogFileIsExist();
            if (file == null) {
                return;
            }
            FileOutputStream fos = null;
            try {
                msg = new Date().toLocaleString() + "	" + msg + "\r\n";
                fos = new FileOutputStream(file, true);
                fos.write(msg.getBytes("GBK"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
                file = null;
            }
        }
    }

    /**
     * 插入日志
     *
     * @param msg 需要插入日志的内容
     * @param ex 异常内容
     */
    public void writeLog(String msg, Throwable ex) {
        if (msg == null) {
            return;
        }
        ex.printStackTrace();
        if (LOG_SWITCH) {
            File file = checkLogFileIsExist();
            if (file == null) {
                return;
            }
            msg += "\r\n";
            msg += getExceptionInfo(ex);
            FileOutputStream fos = null;
            try {
                msg = new Date().toLocaleString() + "	" + msg + "\r\n";
                fos = new FileOutputStream(file, true);
                fos.write(msg.getBytes("GBK"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
                file = null;
            }
        }
    }

    /**
     * 获取异常信息
     *
     * @param ex 异常内容
     * @return 异常信息
     */
    private String getExceptionInfo(Throwable ex) {
        String result = null;
        try {
            Writer info = new StringWriter();
            PrintWriter printWrite = new PrintWriter(info);
            ex.printStackTrace(printWrite);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWrite);
                cause = cause.getCause();
            }
            result = info.toString();
            printWrite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 检查日志文件是否存在，不存在则创建
     */
    private File checkLogFileIsExist() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        String dateStr = sdf.format(new Date());
        File file = new File(getLogSavePath() + dateStr + ".txt");
        if (!isLogExist(file)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sdf = null;
        return file;
    }

    /**
     * 检查当天日志文件是否存在
     *
     * @param file 当天日志文件文件
     * @return 存在：true，不存在：false
     */
    private boolean isLogExist(File file) {
        try {
            File tempFile = new File(getLogSavePath());
            File[] files = tempFile.listFiles();
            if (files == null) {
                return false;
            }
            for (File item : files) {
                String name = item.getName().trim();
                if (name.equalsIgnoreCase(file.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
