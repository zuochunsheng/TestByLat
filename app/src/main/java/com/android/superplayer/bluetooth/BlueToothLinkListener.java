package com.android.superplayer.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface BlueToothLinkListener {

	public void disconnected(BluetoothDevice device);

	public void connected(BluetoothDevice device);
}
