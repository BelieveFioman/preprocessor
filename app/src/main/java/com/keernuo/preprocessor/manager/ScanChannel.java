package com.keernuo.preprocessor.manager;

import com.keernuo.dn9000.mode.jni.ModbusJni;
import com.keernuo.preprocessor.entity.KenDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:扫描设备的工具类,这个类主要是对设备进行扫描用的,设置为单例的.
 */

public class ScanChannel {
    private static ScanChannel scanChannel;
    public static List<KenDevice> kenDevices = new ArrayList<>();

    private ScanChannel() {
    }

    public static ScanChannel getInstance() {
        if (scanChannel == null) {
            scanChannel = new ScanChannel();
            return scanChannel;
        }
        return scanChannel;
    }

    public void scan(ModbusJni modbusJni) {
        for (byte i = 1; i < 10; i++) {
            byte[] send = Command.queryChannelAmount(i);

            System.out.println("查询通道数: " + Hex.bytesToHexString(send));

            byte[] accept = modbusJni.query_channel(2, send);
            if (accept != null) {
                System.out.println("查询通道数响应成功,结果是->: " + Hex.bytesToHexString(accept));
            } else {
                System.out.println("查询通道数无响应:");
            }

            if (accept != null && accept.length > 0) {
                byte[] channeyAccount = new byte[2];
                channeyAccount[0] = accept[3];
                channeyAccount[1] = accept[4];
                KenDevice kenDevice = new KenDevice();
                kenDevice.setSite(i);
                kenDevice.setChannelAccount(Byte.parseByte(BinaryConvert.binary(channeyAccount,10)));
                kenDevices.add(kenDevice);
            } else {
                System.out.println("通道"+i+"无响应");
            }
        }
    }
}
