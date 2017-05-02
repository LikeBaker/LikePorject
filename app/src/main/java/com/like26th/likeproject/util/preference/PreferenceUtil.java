package com.like26th.likeproject.util.preference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.like26th.likeproject.App;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by liuzhen000 on 2016/5/30
 */
public class PreferenceUtil {
    private static PreferenceUtil instance;

    private SharedPreferences sharedPreferences;

    private PreferenceUtil() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.context);
    }

    public static PreferenceUtil getInstance() {
        if (instance == null) {
            instance = new PreferenceUtil();
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, Boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void putBoolean(String key, Boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public float getFloat(String key, Float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public void putFloat(String key, Float value) {
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public long getLong(String key, Long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public void putLong(String key, Long value) {
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void pubMap(Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            Editor editor = sharedPreferences.edit();
            for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                Object value = map.get(key);
                if (value != null) {
                    if (value instanceof Integer) {
                        editor.putInt(key, (Integer) value);
                    } else if (value instanceof Long) {
                        editor.putLong(key, (Long) value);
                    } else if (value instanceof Boolean) {
                        editor.putBoolean(key, (Boolean) value);
                    } else if (value instanceof String) {
                        editor.putString(key, (String) value);
                    } else if (value instanceof Float) {
                        editor.putFloat(key, (Float) value);
                    }
                }
            }
            editor.commit();
        }
    }
}
