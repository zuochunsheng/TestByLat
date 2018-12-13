package com.android.superplayer.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.superplayer.config.LogUtil;

/**
 * 用于管理蓝牙设备的一些常用功能
 * 
 * @author ����
 * 
 */
public class BlueToothServiceRealization implements BlueToothServiceInterface {

	public final static String CONNECTED_BLUETOOTHS = "connectedBlueTooths";
	public final static String NEW_BLUETOOTHS = "newBlueTooths";

	private Context context;

	private List<BlueTooth> connectedBlueTooths;
	private List<BlueTooth> newBlueTooths;

	private BluetoothAdapter BTadapter;
	private SearchBlueToothListener mSearchListener;
	private BlueToothLinkListener listener_Link;
	private MakePariBlueToothListener mMakePariListener;
	private boolean mSearchReceiver_isRegister = false;

//	周边的蓝牙设备一般会有两种，一种是未配对的、还有就是已配对的，Android系统中以BondState绑定状态，来辨别蓝牙是否已经配对，
//	所以我这里创建两个集合：connectedBlueTooths、newBlueTooths来保存搜索的蓝牙设备。
	private BroadcastReceiver mSearchReceiver = new BroadcastReceiver() {

		@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String action = arg1.getAction();
			LogUtil.e("action ="+action);
			switch (action) {
			case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
				connectedBlueTooths.clear();
				newBlueTooths.clear();
				mSearchListener.startSearch();
				break;
			case BluetoothDevice.ACTION_FOUND:
				BluetoothDevice device = arg1
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				BlueTooth blueTooth = new BlueTooth();
				blueTooth.setName(device.getName());
				blueTooth.setAddress(device.getAddress());
				blueTooth.setType(device.getType());
				blueTooth.setUuid(device.getUuids());
				if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
					for (BlueTooth blueToothPul : connectedBlueTooths) {
						if (blueToothPul.getAddress().equals(
								blueTooth.getAddress())) {
							return;
						}
					}
					connectedBlueTooths.add(blueTooth);
				} else {
					for (BlueTooth blueToothPul : newBlueTooths) {
						if (blueToothPul.getAddress().equals(
								blueTooth.getAddress())) {
							return;
						}
					}
					newBlueTooths.add(blueTooth);
				}
				mSearchListener.whileSearch(device);
				break;
			case BluetoothAdapter.ACTION_DISCOVERY_FINISHED://最后接收到搜索结束广播
				Map<String, List<BlueTooth>> blueToothMap = new HashMap<String, List<BlueTooth>>();
				blueToothMap.put(CONNECTED_BLUETOOTHS, connectedBlueTooths);
				blueToothMap.put(NEW_BLUETOOTHS, newBlueTooths);
				mSearchListener.finishSearch(blueToothMap);
				break;
			case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
				device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				switch (device.getBondState()) {
				case BluetoothDevice.BOND_BONDING:// 正在配对
					mMakePariListener.whilePari(device);
					break;
				case BluetoothDevice.BOND_BONDED://配对结束
					mMakePariListener.pairingSuccess(device);
					break;
				case BluetoothDevice.BOND_NONE:// 取消配对/未配对
					mMakePariListener.cancelPari(device);
				default:
					break;
				}
				break;

			case BluetoothDevice.ACTION_ACL_CONNECTED:
				BluetoothDevice device1 = arg1
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				listener_Link.connected(device1);
				break;
			case BluetoothDevice.ACTION_ACL_DISCONNECTED:
				BluetoothDevice device2 = arg1
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				listener_Link.disconnected(device2);
				break;

			default:
				break;
			}
		}
	};

	/**
	 *
	 *   上下文
	 *
	 *    BlueToothAdapter对象
	 */
	public BlueToothServiceRealization() {
		// TODO Auto-generated constructor stub
		this.BTadapter = BluetoothAdapter.getDefaultAdapter();
		connectedBlueTooths = new ArrayList<BlueTooth>();
		newBlueTooths = new ArrayList<BlueTooth>();
	}

	@Override
	public void enableBlueTooth() throws Exception {
		if (BTadapter == null) {
			throw new Exception("设备上没有发现有蓝牙设备");
		}
		if (!BTadapter.isEnabled()) {
			BTadapter.enable();
		}
	}

	@Override
	public void makePair(Context context, String address,
			MakePariBlueToothListener mMakePariListener) throws Exception {
		// TODO Auto-generated method stub
		this.mMakePariListener = mMakePariListener;
		this.context = context;
		IntentFilter iFilter = new IntentFilter(
				BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		context.registerReceiver(mSearchReceiver, iFilter);
		mSearchReceiver_isRegister = true;

		enableBlueTooth();
		BluetoothDevice device = BTadapter.getRemoteDevice(address);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			device.createBond();
		}
	}

	@Override
	public void searchBlueTooth(Context context,
			SearchBlueToothListener mSearchListener) throws Exception {
		// TODO Auto-generated method stub
		this.mSearchListener = mSearchListener;
		this.context = context;
		enableBlueTooth();
		if (BTadapter.isDiscovering()) {
			BTadapter.cancelDiscovery();
		}
		BTadapter.startDiscovery();

		IntentFilter iFilter = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		context.registerReceiver(mSearchReceiver, iFilter);
		mSearchReceiver_isRegister = true;

		// 创建一个查找蓝牙设备的广播意图
		iFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		// 注册一个广播接收者，开启查找蓝牙设备意图后将结果以广播的形式返回
		context.registerReceiver(mSearchReceiver, iFilter);

		// 创建一个结束查找蓝牙设备结束的广播意图

		iFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		context.registerReceiver(mSearchReceiver, iFilter);

	}

	@Override
	public BluetoothSocket getBluetoothSocket(String address)
			throws IOException {
		// TODO Auto-generated method stub
		BluetoothDevice device = BTadapter.getRemoteDevice(address);
		BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID
				.fromString(BlueTooth.MY_UUID));
		return socket;
	}

	@Override
	public void closeBlueToothService() {
		// TODO Auto-generated method stub
		if (mSearchReceiver_isRegister) {
			context.unregisterReceiver(mSearchReceiver);
			mSearchReceiver_isRegister = false;
		}
	}
}
