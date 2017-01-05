package com.keernuo.preprocessor.entity;

/**
 * Created by Fioman on 2016/12/13 0013.
 * Description:设备的相关参数数据
 */
public class DeviceData {
    /**
     * 设备地址
     */
    private int deviceAddress;
    /**
     * 设备的传感器名称
     */
    private String sensorName;
    /**
     * 一级报警
     */
    private String oneAlert;
    /**
     * 二级报警
     */
    private String twoAlert;
    /**
     * 三级报警
     */
    private String threeAlert;
    /**
     * 计量单位
     */
    private String measureUnit;
    /**
     * 报警输出的方式
     */
    private String alertOut;

    /**
     * 设备的浓度值
     */
    private double devAoel;


    public double getDevAoel() {
        return devAoel;
    }

    public void setDevAoel(double devAoel) {
        this.devAoel = devAoel;
    }

    public int getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(int deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getOneAlert() {
        return oneAlert;
    }

    public void setOneAlert(String oneAlert) {
        this.oneAlert = oneAlert;
    }

    public String getTwoAlert() {
        return twoAlert;
    }

    public void setTwoAlert(String twoAlert) {
        this.twoAlert = twoAlert;
    }

    public String getThreeAlert() {
        return threeAlert;
    }

    public void setThreeAlert(String threeAlert) {
        this.threeAlert = threeAlert;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getAlertOut() {
        return alertOut;
    }

    public void setAlertOut(String alertOut) {
        this.alertOut = alertOut;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "deviceAddress=" + deviceAddress +
                ", sensorName='" + sensorName + '\'' +
                ", oneAlert='" + oneAlert + '\'' +
                ", twoAlert='" + twoAlert + '\'' +
                ", threeAlert='" + threeAlert + '\'' +
                ", measureUnit='" + measureUnit + '\'' +
                ", alertOut='" + alertOut + '\'' +
                ", devAoel='" + devAoel + '\'' +
                '}';
    }
}
