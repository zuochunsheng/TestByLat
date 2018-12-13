package com.android.superplayer.bluetooth;

public interface BluetoothSignalListsner {
	public void startSearchBluetooth();

	public void whileGetSignalIntensity(short signalIntensity);

	public void finishSearch();
}
