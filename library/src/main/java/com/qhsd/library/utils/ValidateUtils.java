package com.qhsd.library.utils;

import android.text.TextUtils;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ValidateUtils {

    /**
     * 判断手机号码是否正确
     *
     * @return 正确：true，不正确：false
     */
    public static boolean checkPhone(String mobile) {
        return mobile.matches("(13[0-9]|16[0-9]|19[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$");
    }

    /**
     * 检查字符在某个区间
     * @param text 需要检查的字符串
     * @param min 最小区间（包含）
     * @param max 最大区间（包含）
     * @return 正确：true，不正确：false
     */
    public static boolean checkLengthInterval(String text, int min, int max){
        return !(TextUtils.isEmpty(text) || text.length() < min || text.length() > max);
    }
}
