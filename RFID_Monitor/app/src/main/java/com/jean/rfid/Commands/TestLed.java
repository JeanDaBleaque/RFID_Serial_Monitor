package com.jean.rfid.Commands;

import android.widget.Toast;

import com.jean.rfid.CommandContext;
import com.jean.rfid.ICommand;
import com.jean.rfid.MainActivity;
import com.jean.rfid.SerialCommunicationObject;

public class TestLed implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        int ledStatus = 0;
        SerialCommunicationObject serialObject = MainActivity.getSerialObject();
        try {
            ledStatus = Integer.parseInt(ctx.getArgs().get(0));
        } catch (Exception e) {
            Toast.makeText(MainActivity.getActivity().getApplicationContext(), "Led status must be 0 or 1.", Toast.LENGTH_SHORT);
        }
        if (ledStatus == 0) {
            if (serialObject != null) {
                serialObject.sendMessage("led false");
            }
        } else if (ledStatus == 1) {
            if (serialObject != null) {
                serialObject.sendMessage("led true");
            }
        }
    }

    @Override
    public String getName() {
        return "led";
    }
}
