package com.keernuo.preprocessor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.keernuo.preprocessor.app.KenApplication;


/**
 * Created by Fioman on 2016/12/14 .
 * Description: 偏好设置的工具类
 */

public class SharedPreferencesUtils {
    private static SharedPreferences getSharedPreferences() {
        return KenApplication.getInstance().getSharedPreferences("cl100n_config", Context.MODE_PRIVATE);
    }

    public static String getIpAddress() {
        return getSharedPreferences().getString("ip_address", "255.255.255.255");
    }

    public static void  setIpAddress(String ipAddress) {
        getSharedPreferences().edit().putString("ip_address", ipAddress);
    }

    public static int getBaudRate() {
        return getSharedPreferences().getInt("baud_rate", 9600);
    }

    public static void setBaudRate(int baudRate) {
        getSharedPreferences().edit().putInt("baud_rate", baudRate);
    }

    public static String getPassword() {
        return getSharedPreferences().getString("password", "");
    }
    public static void setPassword(String password) {
        getSharedPreferences().edit().putString("password", password);
    }
}
