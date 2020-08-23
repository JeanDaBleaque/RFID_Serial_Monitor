package com.jean.rfid.Commands;

import android.widget.Toast;

import com.jean.rfid.CommandContext;
import com.jean.rfid.ICommand;
import com.jean.rfid.MainActivity;
import com.jean.rfid.SerialCommunicationObject;

import java.util.List;

public class UpdateCard implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> cmdArgs = ctx.getArgs();
        if (ctx.getArgs().size() == 3) {
                SerialCommunicationObject serialObject = MainActivity.getSerialObject();
                serialObject.sendMessage("update " + cmdArgs.get(0) + " " + cmdArgs.get(1) + " " + cmdArgs.get(2));
        } else {
            Toast.makeText(MainActivity.getActivity().getApplicationContext(), "Usage -> updateCard Name Surname Permission", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public String getName() {
        return "updatecard";
    }
}
