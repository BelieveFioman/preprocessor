package com.keernuo.dn9000.mode.jni;

public class ZfuncJni {
	static {
		System.loadLibrary("zfunc");
	}
	public native byte[] print(byte[] input);
	public native byte[] sendDataBySMS(byte[] input);
	
}
