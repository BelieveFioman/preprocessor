package com.keernuo.preprocessor.app;

import android.app.Application;

import com.keernuo.preprocessor.entity.Sensor;
import com.keernuo.preprocessor.entity.KenDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:app类
 */

public class KenApplication extends Application{
    private static KenApplication instance;
    /**
     * 开机扫描到的全部的传感器设备的集合
     */
    public List<KenDevice> kenDevices = new ArrayList<>();
    /**
     * 要显示的通道数据集合
     */
    public List<Sensor> sensors = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static KenApplication getInstance() {
        return instance;
    }
}
