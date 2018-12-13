package com.android.superplayer.ui.activity.test;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.bluetooth.BlueTooth;
import com.android.superplayer.bluetooth.BlueToothServiceRealization;
import com.android.superplayer.bluetooth.SearchBlueToothListener;
import com.android.superplayer.config.LogUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BluetoothActivity extends BaseActivity {

    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> mDatas=new ArrayList<>();



    private BlueToothServiceRealization blueToothServiceRealization;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initViewAndData() {
        blueToothServiceRealization = new BlueToothServiceRealization();

        che();
    }


    private void che(){
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                    @Override
                    public void onAction(List<String> data) {
                        //initBLE();
                        try {
                            blueToothServiceRealization.searchBlueTooth(BluetoothActivity.this,
                                    new SearchBlueToothListener() {
                                @Override
                                public void startSearch() {

                                }

                                @Override
                                public void whileSearch(BluetoothDevice device) {
                                    LogUtil.e("device",device);

                                }

                                @Override
                                public void finishSearch(Map<String, List<BlueTooth>> blueToothMap) {
                                    LogUtil.e("blueToothMap",blueToothMap);

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

//    private BluetoothAdapter.LeScanCallback leScanCallback= new BluetoothAdapter.LeScanCallback() {
//        @Override
//        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//            LogUtil.e("BluetoothDevice<>"+device.getName());
//            mDatas.add(device);
//            //mAdapter.notifyDataSetChanged();
//            LogUtil.e("设备列表 leScanCallback");
//            LogUtil.e(mDatas);
//        }
//    };
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//    private void initBLE(){
//        BluetoothManager bluetoothManager=(BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
//        if(bluetoothManager==null){
//            return;
//        }
////        new BluetoothLeScanner(bluetoothManager)
//        bluetoothAdapter = bluetoothManager.getAdapter();
//        gdg();
//    }
//
//    //
//    private void gdg() {
//        if(bluetoothAdapter==null){
//            return;
//        }
//        if(!bluetoothAdapter.isEnabled()){
//            bluetoothAdapter.enable();//蓝牙设备开启
//        }
//
//        mDatas.clear();
//        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
//        if(bondedDevices!=null){
//            mDatas.addAll(bondedDevices);
//            //mAdapter.notifyDataSetChanged();
//            LogUtil.e("设备列表 dag");
//            LogUtil.e(mDatas);
//
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            bluetoothAdapter.startLeScan(leScanCallback);
//        }
//
//    }




}
