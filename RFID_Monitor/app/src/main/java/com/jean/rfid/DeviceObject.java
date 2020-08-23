package com.jean.rfid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("AppCompatCustomView")
public class DeviceObject extends Button {
    private BluetoothDevice device;
    public DeviceObject(Context context, BluetoothDevice device) {
        super(context);
        this.setOnClickListener(btnListener);
        this.device = device;
        setProperties();
    }
    @SuppressLint("SetTextI18n")
    private void setProperties() {
        this.setText("Device Name: " + device.getName() +
                "\nMAC Address: " + device.getAddress());
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity main = MainActivity.getActivity();
            SettingsActivity settings = SettingsActivity.getActivity();
            main.setSelectedDevice(device);
            settings.txtDevice.setText("Selected Device: " + device.getName());
            Toast.makeText(settings.getApplicationContext(), device.getName() + " selected!", Toast.LENGTH_LONG).show();
        }
    };
}
