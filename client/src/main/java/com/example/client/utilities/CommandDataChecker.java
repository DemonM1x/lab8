package com.example.client.utilities;

import main.org.example.utility.DataInOutStatus;

import java.util.ArrayList;

/**Class that validate entered data and send fields to @ObjectReading*/
public class CommandDataChecker {
    private ArrayList<String> extraArgs = new ArrayList<>();

    public ArrayList<String> getExtraArgs() {
        return extraArgs;
    }

    public DataInOutStatus checkInputCommand(String command) {
        var fields = MetaInfoCommand.getFields();
        extraArgs = new ObjectReading().objread(command, fields);
        if (extraArgs.size() == 0) {
            return DataInOutStatus.FAILED;
        }
        return DataInOutStatus.SUCCESSFULLY;
    }
}
