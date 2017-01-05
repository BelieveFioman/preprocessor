package com.keernuo.preprocessor.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:连接上设备的传感器的实体类
 */

public class KenDevice {
    private List<Sensor> sensorList = new ArrayList<>(); //传感器是几合1设备,目前都是1合1设备
    private byte site;//设备地址
    private byte channelAccount;  //单个设备上通道的个数(也即是传感器的个数)
    private int oneLevelWarningType;//00 常规报警、01区间报警
    private int twoLevelWarningType;//00 常规报警、01区间报警
    private int oneLevelWarningMode;//一级常规点报警方式
    private int twoLevelWarningMode;//二级常规点报警方式
    private double oneLevelRoutineWarningValue;//一级常规报警值
    private double twoLevelRoutineWarningValue;//二级常规报警值
    private double OneLevelWarningLowValue;//一级低报警
    private double twoLevelWarningLowValue;//二级低报警
    private double oneLevelWarningHighValue;//一级高报警
    private double twoLevelWarningHighValue;//二级高报警

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    public byte getSite() {
        return site;
    }

    public void setSite(byte site) {
        this.site = site;
    }

    public void setChannelAccount(byte channelAccount) {
        this.channelAccount = channelAccount;
        for (byte i = 0; i < channelAccount; i++) {
            Sensor sensor = new Sensor(site, i);
            sensorList.add(sensor);
        }
    }

    public byte getChannelAccount() {
        return channelAccount;
    }

    public int getOneLevelWarningType() {
        return oneLevelWarningType;
    }

    public void setOneLevelWarningType(int oneLevelWarningType) {
        this.oneLevelWarningType = oneLevelWarningType;
    }

    public int getTwoLevelWarningType() {
        return twoLevelWarningType;
    }

    public void setTwoLevelWarningType(int twoLevelWarningType) {
        this.twoLevelWarningType = twoLevelWarningType;
    }

    public int getOneLevelWarningMode() {
        return oneLevelWarningMode;
    }

    public void setOneLevelWarningMode(int oneLevelWarningMode) {
        this.oneLevelWarningMode = oneLevelWarningMode;
    }

    public int getTwoLevelWarningMode() {
        return twoLevelWarningMode;
    }

    public void setTwoLevelWarningMode(int twoLevelWarningMode) {
        this.twoLevelWarningMode = twoLevelWarningMode;
    }

    public double getOneLevelRoutineWarningValue() {
        return oneLevelRoutineWarningValue;
    }

    public void setOneLevelRoutineWarningValue(double oneLevelRoutineWarningValue) {
        this.oneLevelRoutineWarningValue = oneLevelRoutineWarningValue;
    }

    public double getTwoLevelRoutineWarningValue() {
        return twoLevelRoutineWarningValue;
    }

    public void setTwoLevelRoutineWarningValue(double twoLevelRoutineWarningValue) {
        this.twoLevelRoutineWarningValue = twoLevelRoutineWarningValue;
    }

    public double getOneLevelWarningLowValue() {
        return OneLevelWarningLowValue;
    }

    public void setOneLevelWarningLowValue(double oneLevelWarningLowValue) {
        OneLevelWarningLowValue = oneLevelWarningLowValue;
    }

    public double getTwoLevelWarningLowValue() {
        return twoLevelWarningLowValue;
    }

    public void setTwoLevelWarningLowValue(double twoLevelWarningLowValue) {
        this.twoLevelWarningLowValue = twoLevelWarningLowValue;
    }

    public double getOneLevelWarningHighValue() {
        return oneLevelWarningHighValue;
    }

    public void setOneLevelWarningHighValue(double oneLevelWarningHighValue) {
        this.oneLevelWarningHighValue = oneLevelWarningHighValue;
    }

    public double getTwoLevelWarningHighValue() {
        return twoLevelWarningHighValue;
    }

    public void setTwoLevelWarningHighValue(double twoLevelWarningHighValue) {
        this.twoLevelWarningHighValue = twoLevelWarningHighValue;
    }
}
