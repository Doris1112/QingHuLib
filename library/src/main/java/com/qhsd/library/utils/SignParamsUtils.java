package com.qhsd.library.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class SignParamsUtils {

    /**
     * 签名
     * @param map 签名数据
     * @param key 签名key
     * @return 签名好的字符串
     */
    public static String sign(Map<String, Object> map, String key) {
        List<Map.Entry<String, Object>> infoIds =
                new ArrayList<>(map.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> stringObjectEntry, Map.Entry<String, Object> t1) {
                return (stringObjectEntry.getKey()).toString().compareTo(t1.getKey());
            }
        });
        String sss = "";
        for (int i = 0; i < infoIds.size(); i++) {
            sss = sss + infoIds.get(i).toString();
        }
        String fin = sss + "p=" + key;
        MD5Utils.getMd5Value(fin);
        return MD5Utils.getMd5Value(MD5Utils.getMd5Value(fin));
    }

}
