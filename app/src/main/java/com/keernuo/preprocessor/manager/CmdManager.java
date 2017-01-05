package com.keernuo.preprocessor.manager;


import android.util.Log;

import com.keernuo.dn9000.mode.jni.ModbusJni;
import com.keernuo.preprocessor.entity.Sensor;
import com.keernuo.preprocessor.entity.KenDevice;

import java.util.List;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:根据指令去查询的静态工具类
 */

public class CmdManager {
    public static List<Sensor> getChannels(ModbusJni modbusJni, KenDevice kenDevice) {
        List<Sensor> list = kenDevice.getSensorList();
        Log.i("MyTag", "getChannels() ->channel.size() = "+list.size());
        for (Sensor sensor : list) {
            getFormula(modbusJni, sensor);
            getPointAndUnit(modbusJni, sensor);
            getDensityWarningState(modbusJni, sensor);
        }
        return list;
    }

    //获取以及设置气体分子式,也即是传感器的名称,把对象传进去,返回一个字符串
    public static Sensor getFormula(ModbusJni modbusJni, Sensor sensor) {
        byte[] send = Command.getSensorName(sensor.ip);
        byte[] accept = modbusJni.read_485(2, send);
        Log.i("MyTag", "getFormula() ->获取气体分子式" + Hex.bytesToHexString(send));

        if (accept != null) {
            Log.i("MyTag", "getFormula() ->获取气体分子式响应结果是:" + Hex.bytesToHexString(accept));
        } else {
            Log.i("MyTag", "getFormula() ->获取气体分子式无响应");
        }

        if (accept != null && accept.length > 0 && send[1] == accept[1]) {
            //获取气体分子式
            char[] formular = new char[8];
            for (int i = 0; i < 8; i++) {
                formular[i] = (char) (accept[3 + i]);
            }
            String formulaName = new String(formular);
            sensor.molecularFormula = formulaName;
            System.out.println("获取到的气体分子式是:" + formulaName);
        }

        return sensor;
    }

    //获取浓度和报警状态
    public static Sensor getDensityWarningState(ModbusJni modbusJni, Sensor sensor) {
        byte[] send = Command.getDensityWraningState(sensor.ip);
        Log.i("MyTag", "getDensityWarningState() ->获取浓度和报警状态的指令: " + Hex.bytesToHexString(send));

        byte[] acccept = modbusJni.read_485(2, send);
        if (acccept != null) {
            Log.i("MyTag", "getDensityWarningState() ->浓度和报警状态响应: " + Hex.bytesToHexString(acccept));
        } else {
            Log.i("MyTag", "getDensityWarningState() ->浓度和报警状态无响应:");
        }

        if (acccept != null && acccept.length > 0 && send[1] == acccept[1]) {
            byte size = acccept[2];
            //获取报警状态
            byte[] warnningStatebytes = new byte[2];
            warnningStatebytes[0] = acccept[7];
            warnningStatebytes[1] = acccept[8];
            sensor.warningState = Integer.parseInt(BinaryConvert.binary(warnningStatebytes, 10));
            //获取浓度值
            byte[] density = new byte[4];
            for (int i = 0; i < 4; i++) {
                density[i] = acccept[i + 3];
            }
            sensor.gasDensity = Integer.parseInt(BinaryConvert.binary(density, 10));
            Log.i("MyTag", "getDensityWarningState() ->浓度值是->" + sensor.gasDensity + "报警状态: " + sensor.warningState);
        }
        return sensor;
    }

    //获取小数点位数,气体单位
    public static Sensor getPointAndUnit(ModbusJni modbusJni, Sensor sensor) {
        byte[] send = Command.getDensityDecimalPointAndUnit(sensor.ip);
        Log.i("MyTag", "getPointAndUnit() ->获取小数点位数和气体单位的指令: " + Hex.bytesToHexString(send));

        byte[] accept = modbusJni.read_485(2, send);
        if (accept != null) {
            Log.i("MyTag", "getPointAndUnit() ->获取小数点位数和气体单位响应: " + Hex.bytesToHexString(accept));
        } else {
            Log.i("MyTag", "getPointAndUnit() -> 获取小数点位数和气体单位无响应");
        }

        if (accept != null && send[1] == accept[1]) {
            //获取小数点位数
            byte[] pointNumber = new byte[2];
            pointNumber[0] = accept[3];
            pointNumber[1] = accept[4];

            sensor.decimalPointNumber = Integer.parseInt(BinaryConvert.binary(pointNumber, 10));

            //获取气体单位
            byte[] densityUnit = new byte[2];
            densityUnit[0] = accept[5];
            densityUnit[1] = accept[6];
            sensor.gasUnit = Integer.parseInt(BinaryConvert.binary(densityUnit, 10));
        }

        return sensor;
    }

    //获取温度,湿度
    public static Sensor getTemperatureAndHumidity(ModbusJni modbusJni, Sensor sensor) {
        byte[] send = Command.getTemperatureAndHumidity(sensor.ip);
        Log.i("MyTag", "getTemperatureAndHumidity() ->获取温度湿度发送指令: " + Hex.bytesToHexString(send));

        byte[] accept = modbusJni.read_485(2, send);


        if (accept != null) {
            Log.i("MyTag", "getTemperatureAndHumidity() ->获取温度湿度成功返回: " + Hex.bytesToHexString(accept));
        } else {
            Log.i("MyTag", "getTemperatureAndHumidity() ->获取温度湿度无响应");
        }

        if (accept != null && send[1]==accept[1]) {
            //获取温度
            byte[] temperature = new byte[2];
            temperature[0] = accept[3];
            temperature[1] = accept[4];
            sensor.temperature = Integer.parseInt(BinaryConvert.binary(temperature, 10));

            //获取湿度
            byte[] humidity = new byte[2];
            humidity[0] = accept[5];
            humidity[1] = accept[6];

            sensor.humidity = Integer.parseInt(BinaryConvert.binary(humidity, 10));
            Log.i("MyTag", "getTemperatureAndHumidity() ->获取到的温度值: " + sensor.temperature + "湿度: " + sensor.humidity);
        }

        return sensor;
    }

    //获取用户修正的温度和湿度值
    public static Sensor getUserNotifyTemperatureAndHumidity(ModbusJni modbusJni, Sensor sensor) {
        byte[] send = Command.getUserNotityTemperatureAndHumidity(sensor.ip);
        Log.i("MyTag", "getTemperatureAndHumidity() ->获取温度湿度发送指令: " + Hex.bytesToHexString(send));

        byte[] accept = modbusJni.read_485(2, send);


        if (accept != null) {
            Log.i("MyTag", "getTemperatureAndHumidity() ->获取温度湿度成功返回: " + Hex.bytesToHexString(accept));
        } else {
            Log.i("MyTag", "getTemperatureAndHumidity() ->获取温度湿度无响应");
        }

        if (accept != null && send[1]==accept[1]) {
            //获取温度
            byte[] temperature = new byte[2];
            temperature[0] = accept[3];
            temperature[1] = accept[4];
            sensor.temperature = Integer.parseInt(BinaryConvert.binary(temperature, 10));

            //获取湿度
            byte[] humidity = new byte[2];
            humidity[0] = accept[5];
            humidity[1] = accept[6];

            sensor.humidity = Integer.parseInt(BinaryConvert.binary(humidity, 10));
            Log.i("MyTag", "getTemperatureAndHumidity() ->获取到的温度值: " + sensor.temperature + "湿度: " + sensor.humidity);
        }

        return sensor;
    }

}
