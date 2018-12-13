package com.android.superplayer.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

public interface BlueToothServiceInterface {

	public void enableBlueTooth() throws Exception;

	public void searchBlueTooth(Context context,
                                SearchBlueToothListener mSearchListener) throws Exception;

	public BluetoothSocket getBluetoothSocket(String address)
			throws IOException;

	public void makePair(Context context, String address,
                         MakePariBlueToothListener mMakePariListener) throws Exception;

	public void closeBlueToothService();
}
