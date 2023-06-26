package com.example.client.utilities;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AvailableCommands {
    public final Set<String> noArgumentCommands = new HashSet<>();
    public final Set<String> numArgumentCommands = new HashSet<>();
    public final Set<String> stringArgumentCommands = new HashSet<>();
    public final Set<String> objectArgumentCommands = new HashSet<>();
    public final String scriptArgumentCommand;
    public final String objAndNumArgumentCommand;

    public AvailableCommands() {

        Collections.addAll(noArgumentCommands,
                "help",
                "info",
                "show",
                "clear",
                "exit",
                "min_by_id");

        Collections.addAll(numArgumentCommands,
                "remove_by_id");

        Collections.addAll(stringArgumentCommands,
                "filter_starts_with_name");

        Collections.addAll(objectArgumentCommands,
                "add",
                "add_if_max",
                "add_if_min",
                "remove_greater",
                "remove_lower");

        scriptArgumentCommand = "execute_script";

        objAndNumArgumentCommand = "update";
    }
}
