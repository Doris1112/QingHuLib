package com.qhsd.library.utils;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class FileUtils {

    /**
     * 获取文件夹大小
     *
     * @param file 指定文件夹
     * @return long 文件夹大小
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                if (f.isDirectory()) {
                    size = size + getFolderSize(f);
                } else {
                    size = size + f.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size 文件大小
     * @return 输入文件大小带单位
     */
    public static String getFormatSize(long size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    /**
     * 删除文件
     *
     * @param file 指定删除文件
     * @return 删除成功：true，删除失败：false
     */
    public static boolean deleteFile(File file) {
        return file != null && file.exists() && file.delete();
    }

    /**
     * 清理文件
     * @param files 需要清理文件集合
     */
    public static void clearFiles(File[] files) {
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f.isFile()) {
                    deleteFile(f);
                } else {
                    clearFiles(f.listFiles());
                }
            }
        }
    }

    /**
     * 文件是否存在
     *
     * @param filePath 文件路径
     * @param name 文件名称
     * @return 存在：返回文件，不存在：返回null
     */
    public static File fileNameExists(String filePath, String name) {
        File file = new File(filePath, name);
        if (file.exists()) {
            return file;
        }
        return null;
    }
}
