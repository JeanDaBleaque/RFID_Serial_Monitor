package com.jean.rfid;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jean.rfid.Commands.AddCard;
import com.jean.rfid.Commands.DeleteCard;
import com.jean.rfid.Commands.TestLed;
import com.jean.rfid.Commands.UpdateCard;
import com.jean.rfid.Commands.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RFCommandManager {
    private final List<ICommand> commands = new ArrayList<>();
    public RFCommandManager() {
        addCommand(new AddCard());
        addCommand(new DeleteCard());
        addCommand(new UpdateCard());
        addCommand(new TestLed());
        addCommand(new test());
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    private void addCommand(ICommand cmd) {
        boolean isFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));
        if (isFound) {
            throw new IllegalArgumentException("A command with this name is already present!");
        }
        commands.add(cmd);
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();
        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }
        return null;
    }

    void handle(String message) {
        String[] split = message
                .replaceFirst("(?i)" + Pattern.quote("."), "")
                .split("\\s+");
        String invoke = split[0].toLowerCase();
        TextView txtMonitor = MainActivity.getMonitor();
        txtMonitor.append("\nClient: " + invoke);
        ICommand cmd = this.getCommand(invoke);
        if (cmd != null) {
            txtMonitor.append("\nClient: " + cmd.getName());
                List<String> args = Arrays.asList(split).subList(1, split.length);
                CommandContext ctx = new CommandContext(args);
                cmd.handle(ctx);
                return;
        }
    }
}
