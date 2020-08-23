package com.jean.rfid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SerialCommunicationObject {
    private static SimpleBluetoothDeviceInterface deviceInterface;
    private final BluetoothManager bluetoothManager = BluetoothManager.getInstance();;
    private Context context;
    private TextView txtMonitor;
    private BluetoothDevice bDevice;
    public SerialCommunicationObject(TextView txtMonitor) {
        context = MainActivity.getActivity().getApplicationContext();
        this.txtMonitor = txtMonitor;
    }
    @SuppressLint("CheckResult")
    public void connectDevice(BluetoothDevice connectedDevice) {
        bDevice = connectedDevice;
        bluetoothManager.openSerialDevice(connectedDevice.getAddress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {
        txtMonitor.append("\nSerial connection successful -> " + bDevice.getName());
        deviceInterface = connectedDevice.toSimpleDeviceInterface();
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
    }

    public static void sendMessage(String message) {
        deviceInterface.sendMessage(message);
    }

    public void closeConnection() {
        txtMonitor.append("\nSerial disconnection successful -> " + bDevice.getName());
        bluetoothManager.closeDevice(bDevice.getAddress());
    }

    private void onMessageSent(String message) {
        txtMonitor.append("\nClient: " + message);
        Toast.makeText(context, "Sent a message! Message was: " + message, Toast.LENGTH_SHORT).show(); // Replace context with your context instance.
    }

    private void onMessageReceived(String message) {
        txtMonitor.append("\nDevice: " + message);
        Toast.makeText(context, "Received a message! Message was: " + message, Toast.LENGTH_SHORT).show(); // Replace context with your context instance.
    }

    private void onError(Throwable error) {
        Toast.makeText(context, "Received a error! Error: " + error.getMessage(), Toast.LENGTH_SHORT);
    }
}
