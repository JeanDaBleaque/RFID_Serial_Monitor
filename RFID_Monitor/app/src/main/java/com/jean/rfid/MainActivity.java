package com.jean.rfid;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static TextView txtMonitor;
    private TextView txtCommand;
    private Button btnSend;
    private Button btnConnect;
    private Button btnSettings;
    private Button btnDisconnect;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice selectedDevice;
    private static MainActivity instance;
    private static SerialCommunicationObject serialObject;
    private RFCommandManager commandManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedDevice = null;
        txtCommand = findViewById(R.id.txtCommand);
        txtMonitor = findViewById(R.id.txtMonitor);
        btnSend = findViewById(R.id.btnSend);
        btnConnect = findViewById(R.id.btnConnect);
        btnSettings = findViewById(R.id.btnSettings);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        btnSend.setOnClickListener(commandListener);
        btnSettings.setOnClickListener(settingsListener);
        btnConnect.setOnClickListener(connectListener);
        btnDisconnect.setOnClickListener(disconnectListener);
        txtMonitor.setMovementMethod(new ScrollingMovementMethod());
        commandManager = new RFCommandManager();
        instance = this;
    }
    public View.OnClickListener connectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (selectedDevice != null) {
                serialObject = new SerialCommunicationObject(txtMonitor);
                serialObject.connectDevice(selectedDevice);
                btnConnect.setVisibility(View.INVISIBLE);
                btnDisconnect.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Please check bluetooth device connection on settings menu!", Toast.LENGTH_SHORT);
            }
        }
    };
    public View.OnClickListener disconnectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (serialObject != null) {
                serialObject.closeConnection();
                serialObject = null;
                btnDisconnect.setVisibility(View.INVISIBLE);
                btnConnect.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Please check bluetooth device connection on settings menu!", Toast.LENGTH_SHORT);
            }
        }
    };

    public View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (bAdapter == null) {
                Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                if (!bAdapter.isEnabled()) {
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
                    Toast.makeText(getApplicationContext(), "Bluetooth Turned ON", Toast.LENGTH_SHORT).show();
                }
            }
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
    };
    public View.OnClickListener commandListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (serialObject != null) {
                String message = txtCommand.getText().toString();
                //serialObject.sendMessage(message);
                txtMonitor.append("\nClient: " + "button!");
                commandManager.handle(message);
            } else {
                Toast.makeText(getApplicationContext(), "Please check bluetooth device connection on settings menu!", Toast.LENGTH_SHORT);
            }
        }
    };

    public static MainActivity getActivity() {
        return instance;
    }

    public static TextView getMonitor() {
        return txtMonitor;
    }

    public void setSelectedDevice(BluetoothDevice selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    public BluetoothDevice getSelectedDevice() {
        return selectedDevice;
    }

    public static SerialCommunicationObject getSerialObject() {
        return serialObject;
    }
}