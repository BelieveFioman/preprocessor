package com.keernuo.preprocessor.entity;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:Sensor传感器数据参数封装
 */

public class Sensor {
    public byte ip;//地址
    public byte channelNumber;//管道编号
    public int gasDensity; //浓度值
    public int gasUnit;//气体单位00 ppm,01%VOL,02%LEL
    public int warningState; //报警状态,00正常,01低报警,02高报警,03低报警+高报警
    public int decimalPointNumber;//小数点位数
    public int temperature;//温度
    public int humidity; //湿度
    public String molecularFormula;//分子式
    public boolean answerState; //设备应答状态,有可能设备连上了,但是没有应答.设备死机,或者是设备连接出问题,都有可能发生这种情况.

    public Sensor() {
    }

    public Sensor(byte ip, byte channelNumber) {
        this.ip = ip;
        this.channelNumber = channelNumber;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "ip=" + ip +
                ", channelNumber=" + channelNumber +
                ", gasDensity=" + gasDensity +
                ", gasUnit=" + gasUnit +
                ", warningState=" + warningState +
                ", decimalPointNumber=" + decimalPointNumber +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", molecularFormula='" + molecularFormula + '\'' +
                '}';
    }
}
