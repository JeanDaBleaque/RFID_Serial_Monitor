package com.jean.rfid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harrysoft.androidbluetoothserial.BluetoothManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    BluetoothManager bluetoothManager;
    BluetoothDevice selectedDevice = null;
    List<BluetoothDevice> pairedDevices;
    ArrayList<DeviceObject> deviceButtons;
    TextView txtDevice;
    private static SettingsActivity instance;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        instance = this;
        deviceButtons = new ArrayList<>();
        bluetoothManager = BluetoothManager.getInstance();
        txtDevice = findViewById(R.id.txtDevice);
        if (selectedDevice == null) {
            txtDevice.setText("No device selected!");
        } else {
            txtDevice.setText("Selected Device: " + MainActivity.getActivity().getSelectedDevice().getName());
        }
        if (bluetoothManager == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth not available.", Toast.LENGTH_LONG).show();
            finish();
        }
        checkPairedDevices();
        addButtons(deviceButtons);
    }

    private void checkPairedDevices() {
        pairedDevices = bluetoothManager.getPairedDevicesList();
        for (BluetoothDevice curDevice : pairedDevices) {
            DeviceObject curBtn = new DeviceObject(this, curDevice);
            deviceButtons.add(curBtn);
        }
    }

    private void addButtons(ArrayList<DeviceObject> objects) {
        Iterator<DeviceObject> it = objects.iterator();
        while (it.hasNext()) {
            LinearLayout layout = findViewById(R.id.layoutButton);
            layout.addView(it.next());
        }
    }

    public static SettingsActivity getActivity() {
        return instance;
    }

}