package com.example.client.utilities;

import main.org.example.utility.DataInOutStatus;

import java.util.ArrayList;
import java.util.Arrays;

/**Class to separate name and arguments for a command*/
public class CommandValidator {
    private final ArrayList<String> commandArguments = new ArrayList<>();

    public DataInOutStatus validate(String inputData) {
        String[] splitInputData = inputData.split(" ");
        String commandName = splitInputData[0];
        commandArguments.addAll(Arrays.asList(splitInputData).subList(1, splitInputData.length));
        return new CommandChecker().checkCorrectnessOfCommand(commandName, commandArguments);
    }

}
