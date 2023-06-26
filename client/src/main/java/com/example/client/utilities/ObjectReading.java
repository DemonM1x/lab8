package com.example.client.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**The class from which we get data for the collection element*/

public class ObjectReading {
    public ArrayList<String> objread(String command , LinkedHashMap<String, String> fields) {
        AvailableCommands availableCommands = new AvailableCommands();
        ArrayList<String> extraArgs = new ArrayList<>();
        try {
            if (!ScriptReader.getExecuteStatus()) {

            } else {
                if (ScriptReader.getReadiedCommands().size() - ScriptReader
                        .getCurrentCommand() < 10) {
                    return new ArrayList<>();
                }
                int startValue = ScriptReader.getCurrentCommand() + 1;
                for (int iter1 = startValue; iter1 < startValue + 10; iter1++) {
                    extraArgs.add(ScriptReader.getReadiedCommands().get(iter1).trim());
                    ScriptReader.setCurrentCommand(ScriptReader.getCurrentCommand() + 1);
                }
            }
        } catch (NullPointerException e) {
            AlertUtility.errorAlert("\nInterrupting input stream.\n");
            extraArgs = new ArrayList<>();
        }
        return extraArgs;
    }
}