package com.slow.adventure.ui.ActivityCollector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishOneActivity(String activityName) {

        for (Activity activity : activities) {
            String name = activity.getClass().getName();//activity的包名+类名
            if (name.equals(activityName)) {
                if (activity.isFinishing()) {
                    activities.remove(activity);
                } else {
                    activity.finish();
                }
            }
        }
    }

}
