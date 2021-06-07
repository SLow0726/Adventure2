package com.slow.adventure;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    SharedPreferences sharedPreferences;
    Context context;

    public Preferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(
                context.getPackageName() + "_Preferences", Context.MODE_PRIVATE);
    }

    public void record(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }

}
