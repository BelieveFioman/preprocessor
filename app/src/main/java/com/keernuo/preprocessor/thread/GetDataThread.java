package com.keernuo.preprocessor.thread;

import android.os.Handler;
import android.os.Message;

import com.keernuo.dn9000.mode.jni.ModbusJni;
import com.keernuo.preprocessor.consts.GlobalConst;
import com.keernuo.preprocessor.entity.Sensor;
import com.keernuo.preprocessor.manager.CmdManager;

/**
 * Created by Fioman on 2016/12/20 0020.
 * Description:线程,一直循环的从下位机读取数据
 */

public class GetDataThread extends Thread{
    private ModbusJni modbusJni = new ModbusJni();
    private Handler handler;
    private Sensor sensor;

    public GetDataThread(Handler handler, Sensor sensor) {
        this.handler = handler;
        this.sensor = sensor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msg = new Message();
                msg.what = GlobalConst.HANDLER_MESSAGE_UPDATE_DATA;
                CmdManager.getDensityWarningState(modbusJni, sensor);
                CmdManager.getTemperatureAndHumidity(modbusJni, sensor);
                msg.obj = sensor;
                handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
