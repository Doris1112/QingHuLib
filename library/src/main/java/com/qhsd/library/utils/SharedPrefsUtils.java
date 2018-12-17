package com.qhsd.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class SharedPrefsUtils {

    private static SharedPrefsUtils prefsUtil;
    private Context context;

    public static SharedPrefsUtils getInstance(Context context){
        if (prefsUtil == null){
            prefsUtil = new SharedPrefsUtils(context);
        }
        return prefsUtil;
    }

    private SharedPrefsUtils(Context context){
        this.context = context;
    }

    /**
     * 使用SharedPreferences保存数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param value 保存值
     */
    public void putValue(String spName, String key, int value) {
        Editor sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        sp.putInt(key, value);
        sp.apply();
    }


    /**
     * 使用SharedPreferences保存数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param value 保存值
     */
    public void putValue(String spName, String key, Long value) {
        Editor sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        sp.putLong(key, value);

        sp.apply();
    }

    /**
     * 使用SharedPreferences保存数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param value 保存值
     */
    public void putValue(String spName, String key, boolean value) {
        Editor sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        sp.putBoolean(key, value);
        sp.apply();
    }

    /**
     * 使用SharedPreferences保存数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param value 保存值
     */
    public void putValue(String spName, String key, String value) {
        Editor sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.apply();
    }
    /**
     * 使用SharedPreferences读取数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param defValue 默认值
     * @return 获取值
     */
    public int getValue(String spName, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * 使用SharedPreferences读取数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param defValue 默认值
     * @return 获取值
     */
    public long getValue(String spName, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    /**
     * 使用SharedPreferences读取数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param defValue 默认值
     * @return 获取值
     */
    public boolean getValue(String spName, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 使用SharedPreferences读取数据
     * @param spName SharedPreferenceName
     * @param key key
     * @param defValue 默认值
     * @return 获取值
     */
    public String getValue(String spName, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 删除某个数据
     * @param spName SharedPreferenceName
     * @param key key
     */
    public void remove(String spName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        //提交当前数据
        editor.apply();
    }

    /**
     * 删除所有数据
     * @param spName SharedPreferenceName
     */
    public void removeAll(String spName) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    /**
     * 判断某个KEY对应的偏好设置是否存在
     * @param spName SharedPreferenceName
     * @param key key
     * @return 存在：true，不存在：false
     */
    public boolean existed(String spName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        if ("".equals(sp.getString(key, ""))) {
            return false;
        } else {
            return true;
        }
    }
}
