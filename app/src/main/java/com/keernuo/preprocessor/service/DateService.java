package com.keernuo.preprocessor.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.keernuo.preprocessor.consts.GlobalConst;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fioman on 2016/12/23 0023.
 * Description:更新日期的Service,主界面上面
 */

public class DateService extends Service {
    private Timer timer = null;
    private Intent timeIntent = null;
    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timeIntent = new Intent();
        //定时器发送广播
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeIntent.setAction(GlobalConst.ACTION_TIME_UPDATE);
                //发送广播,通知UI层时间改变了
                sendBroadcast(timeIntent);
            }
        },1000,1000); //延时1秒执行,run方法.而后是1秒一个周期的去循环执行run方法
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
