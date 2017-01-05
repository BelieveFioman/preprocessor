package com.keernuo.preprocessor.utils;

/**
 * Created by Fioman on 2016/12/20 0020.
 * Description:各种数据格式转换工具类
 */

public class DataFormat {
    /**
     * 根据小数点位数,计算其相应的要显示的值
     *
     * @param density     气体对应的浓度值
     * @param pointNumber 小数点位数
     * @return 返回一个double
     */
    public static double getDensity(int density, int pointNumber) {
        double dDendity = 0;
        switch (pointNumber) {
            case 0:
                dDendity = (double) density;
                break;
            case 1:
                dDendity = (double) density / 10;
                break;
            case 2:
                dDendity = (double)density / 100;
                break;
            case 3:
                dDendity = (double)density / 1000;
                break;
        }
        return dDendity;
    }

    public static String getUnit(int gasUnit) {
        String unit = null;
        switch (gasUnit) {
            case 0:
                unit = "ppm";
                break;
            case 1:
                unit = "%VOL";
                break;
            case 2:
                unit = "%LEL";
                break;
        }
        return unit;
    }
}
