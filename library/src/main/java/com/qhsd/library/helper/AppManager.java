package com.qhsd.library.helper;

import android.app.Activity;
import android.os.Process;

import java.util.List;
import java.util.Stack;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() { }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null){
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try {
            if (activityStack == null){
                return;
            }
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 结束指定的多个Activity
     */
    public void finishActivityList(List<Class<?>> clsList) {
        for (int j = 0; j < clsList.size(); j++) {
            Class<?> cls = clsList.get(j);
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    activityStack.get(i).finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public boolean isAlive(Class<?> cls) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i).getClass().getName().equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            //退出程序
            Process.killProcess(Process.myPid());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}