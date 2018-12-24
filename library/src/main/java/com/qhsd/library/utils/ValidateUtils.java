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

    /**
     * 金额转换
     * @param str 金额：如：1000
     * @return 金额：如：1千
     */
    public static String getUpperMoney(String str){
        StringBuilder sb = new StringBuilder();
        try {
            Integer.parseInt(str);
            int len = str.length();
            if (len >= 3){
                String flag = str.substring(0, 1);
                sb.append(flag);
                switch (len) {
                    case 3:
                        sb.append("百");
                        break;
                    case 4:
                        sb.append("千");
                        break;
                    case 5:
                        sb.append("万");
                        break;
                    case 6:
                        // 十万
                        sb.append("十万");
                        break;
                    case 7:
                        // 百万
                        sb.append("百万");
                        break;
                    case 9:
                        // 千万
                        sb.append("千万");
                        break;
                    case 10:
                        // 亿
                        sb.append("亿");
                        break;
                    default:
                        break;
                }
                flag = str.substring(1, 2);
                if (!flag.equals("0")){
                    sb.append(flag);
                }
            } else {
                return str;
            }
        } catch (Exception e) {
            return str;
        }
        return sb.toString();
    }
}
