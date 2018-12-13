package com.android.superplayer.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface MakePariBlueToothListener {

	public void whilePari(BluetoothDevice device);

	public void pairingSuccess(BluetoothDevice device);

	public void cancelPari(BluetoothDevice device);
}
