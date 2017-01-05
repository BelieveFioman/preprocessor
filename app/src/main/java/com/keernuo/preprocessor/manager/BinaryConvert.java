package com.keernuo.preprocessor.manager;

import java.math.BigInteger;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:将byte[]数组转换为各种进制的字符串
 */
public class BinaryConvert {
    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

}
