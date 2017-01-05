package com.keernuo.dn9000.mode.jni;


public class ModbusJni {
	
	static {
		System.loadLibrary("keernuo");
	}
	
	public native byte[] read_485(int seria_number,byte[] input);
	
	public native byte[] read_host(int seria_number,byte[] input,int timeoutMill,int isSend);
	
	public native byte[] query_channel(int seria_number,byte[] input);
	
	/**
	 * 设置led灯状态
	 * @param id
	 * @param state
	 */
	public native void led_set(int id,int state);
	
	/**
	 * 获取led灯状态
	 * @param id
	 */
	public native int led_get(int id);
	
	/**
	 * 4-20MA 输入采样  channel 为通道号  1 到 48 （如果接的话）
	 * @param channel
	 * @return
	 */
	public native int mcp3208_get(int channel);
	
	
	public native void setBaudrate(int baudrateCode);
	
}
