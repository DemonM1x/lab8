package main.org.example.utility;

import main.org.example.collection.City;

import java.io.Serializable;
import java.util.ArrayList;

public class CommandFactory implements Serializable {
    private final String commandName;
    private final ArrayList<String> argName;
    private City city;

    public CommandFactory(String aCommand, ArrayList<String> aArgs) {
        commandName = aCommand;
        argName = aArgs;
        city = null;
    }

    public CommandFactory addCity(City city) {
        this.city = city;
        return this;
    }

    public String getCommand() {
        return commandName;
    }

    public ArrayList<String> getArg() {
        return argName;
    }

    public City getCity() {
        return city;
    }


    @Override
    public String toString() {
        return commandName + " "
                + (argName != null ? argName : "")
                + (city != null ? city : "");
    }

}
