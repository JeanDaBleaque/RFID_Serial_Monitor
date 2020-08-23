package com.jean.rfid;

import java.util.Arrays;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx);
    String getName();
    default String[] getPermissions() {
        String[] permissions = {"User", "Moderator", "Administrator"};
        return permissions;
    };
    default List<String> getAliases() {
        return Arrays.asList();
    };
}
