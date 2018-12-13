package com.android.superplayer.bluetooth;

import android.os.ParcelUuid;

public class BlueTooth {

	public final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // SPP服务UUID号
	public final static int SEARCH_BLUETOOTH_SIGNALS = 4;

	public final static int SIGNAL_STRONG = 3;
	public final static int SIGNAL_MODER = 2;
	public final static int SIGNAL_WEAK = 1;

	private String name;
	private String address;
	private int type;
	private ParcelUuid[] uuid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ParcelUuid[] getUuid() {
		return uuid;
	}

	public void setUuid(ParcelUuid[] uuid) {
		this.uuid = uuid;
	}

	public static String getMyUuid() {
		return MY_UUID;
	}

}
