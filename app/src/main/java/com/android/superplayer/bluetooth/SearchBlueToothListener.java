package com.android.superplayer.bluetooth;

import java.util.List;
import java.util.Map;

import android.bluetooth.BluetoothDevice;

public interface SearchBlueToothListener {
	public void startSearch();

	public void whileSearch(BluetoothDevice device);

	public void finishSearch(Map<String, List<BlueTooth>> blueToothMap);
}
