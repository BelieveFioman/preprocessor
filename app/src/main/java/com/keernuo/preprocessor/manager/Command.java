package com.keernuo.preprocessor.manager;

import java.io.UnsupportedEncodingException;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:发送指令,主要是发送一些获取数据的指令
 */

public class Command {
    /**
     * 发送命令:
     * 0x00,0x03,0x00,0x12,0x00,0x01,0xff,0xff
     * 主机发送给下位机的指令的格式:
     *0x00设备地址->1个字节
     * 0x03功能码->1个字节
     * 0x00 0x12->寄存器地址 2个字节
     * 0x00,0x01->寄存器的个数 2个字节
     *
     * 响应的结果:
     * 0x00,0x03,0x02,0x01,0x02,0xff,0xff
     * 0x00:设备地址-> 1个字节
     * 0x03:功能码 -> 1个字节
     * 0x02:返回的数据的字节个数 ->1个字节
     * 0x01,0x02:返回的数据的具体内容,由前面的0x02决定.0xff,0xff校验码
     */
    //获取通道数
    public static byte[] queryChannelAmount(byte site){
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x12;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x01;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }
    //根据当前设备的地址,获取当前设备的名称
    public static byte[] getSensorName(byte site) {
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x00;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x04;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }
    /**
     * 获取当前浓度值高字、当前浓度值低字、当前报警状态
     * @param site
     * @return
     */
    public static byte[] getDensityWraningState(byte site){
        //根据通道数，读取字节数
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x09;
        bts[3] = (byte)0xd0;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x03;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }
    /**
     * 查询小数点、浓度单位
     * @param site
     * @param
     * @return
     */
    public static byte[] getDensityDecimalPointAndUnit(byte site){
        //根据通道数，读取字节数
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x05,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x05;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询温湿度
     * @param site
     * @param
     * @return
     */
    public static byte[] getTemperatureAndHumidity(byte site){
        //根据通道数，读取字节数
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)0x09, (byte) 0xCE,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x0A;
        bts[3] = (byte)0x07;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 获取用户修正的温度和湿度
     * @param site
     * @return
     */
    public static byte[] getUserNotityTemperatureAndHumidity(byte site){
        //根据通道数，读取字节数
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x09;
        bts[3] = (byte)0xA0;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询一级报警类型
     * @param site
     * @param
     * @return
     */
    public static byte[] getOneLevelWarningType(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x54,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x09;
        bts[3] = (byte)0x54;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x01;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询二级报警类型
     * @param site
     * @param
     * @return
     */
    public static byte[] getTwoLevelWarningType(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x5C,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x5C;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x01;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询一级常规报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] getOneLevelRoutineWarningValue(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)0x00, (byte) 0x55,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x55;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }


    /**
     * 设置一级常规报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] setOneLevelRoutineWarningValue(byte site,int value){
        byte[] bts = new byte[]{(byte) 0x00,(byte)0x05,(byte)00, (byte) 0x55,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xff,(byte)0xff};
        bts[0] = site;
        byte[] values = Hex.intToBytes(value);
        bts[4] = values[0];
        bts[5] = values[1];
        bts[6] = values[2];
        bts[7] = values[3];
        CRC16.checkCommand(bts);
        return bts;
    }



    /**
     * 查询二级常规报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] getTwoLevelRoutineWarningValue(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x5D,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x5D;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询一级区间低报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] getOneLevelWarningLowValue(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x58,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x58;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询一级区间高报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] getOneLevelWarningHighValue(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x5A,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x5A;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询二级区间低报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] getTwoLevelWarningLowValue(byte site){
//		byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x60,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
        byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x60;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询二级区间高报警值
     * @param site
     * @param
     * @return
     */
    public static byte[] getTwoLevelWarningHighValue(byte site){
        byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x62,(byte)0x00,(byte)0x02,(byte)0xff,(byte)0xff};
//		byte[] bts = new byte[256];
        bts[0] = site;
        bts[1] = (byte)0x03;
        bts[2] = (byte)0x00;
        bts[3] = (byte)0x62;
        bts[4] = (byte)0x00;
        bts[5] = (byte)0x02;
        bts[6] = (byte)0xff;
        bts[7] = (byte)0xff;
        CRC16.checkCommand(bts);
        return bts;
    }

    /**
     * 查询报警信息
     * @param site
     * @return
     */
    public static byte[] getWarningInfo(boolean isTcp,long site){
        if(isTcp){
            byte[] bts = new byte[]{(byte) 0x00,(byte)0x03,(byte)00, (byte) 0x54,(byte)0x00,(byte)0x10,(byte)0xff,(byte)0xff};
            CRC16.checkCommand(bts);
            return bts;
        }else{
            byte[] bts = new byte[384];
            bts[0] = Hex.hexStringToBytes(Long.toHexString(site))[0];
            bts[1] = (byte)0x03;
            bts[2] = (byte)0x00;
            bts[3] = (byte)0x54;
            bts[4] = (byte)0x00;
            bts[5] = (byte)0x10;
            bts[6] = (byte)0xff;
            bts[7] = (byte)0xff;
            CRC16.checkCommand(bts);
            return bts;
        }
    }

    /**
     * 查询打印机状态  0正常 4无纸
     * @return
     */
    public static byte[] getPrinterState() {
        return new byte[]{(byte) 0x1b,(byte)0x76};//ESC v
    }

    /**
     * 初始化打印机
     * @return
     */
    public static byte[] initPrinter() {
        return new byte[]{(byte) 0x1b,(byte)0x4c};//ESC @
    }

    /**
     * 打印内容
     * @return
     */
    public static byte[] getPrinterContent(String pContent) {
        try {
            return pContent.getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getEndChar(){
        return new byte[]{(byte)0x0A,(byte)0x0D};
    }

    public static byte[] getSMSInitData() {
        try {
            return "AT+CMGF=1".getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
