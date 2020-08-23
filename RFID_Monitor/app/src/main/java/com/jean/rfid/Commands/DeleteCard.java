package com.jean.rfid.Commands;

import com.jean.rfid.CommandContext;
import com.jean.rfid.ICommand;
import com.jean.rfid.MainActivity;
import com.jean.rfid.SerialCommunicationObject;

public class DeleteCard implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        SerialCommunicationObject serialObject = MainActivity.getSerialObject();
        serialObject.sendMessage("delete");
    }

    @Override
    public String getName() {
        return "deletecard";
    }
}
