package com.common;

import android.app.Activity;
import android.app.Application;

import com.leo.base.application.LApplication;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huisou on 2015/11/28.
 */
public class AboutActivitySy extends Application {
    private List<Activity> activityList = new LinkedList<>();
    private static AboutActivitySy instance;

    private AboutActivitySy() {
    }

    // 单例模式中获取唯一的MyApplication实例
    public static AboutActivitySy getInstance() {
        if (null == instance) {
            instance = new AboutActivitySy();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}