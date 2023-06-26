package com.example.client.utilities;


import java.util.LinkedHashMap;


/**A class that contains the fields of the main class and commands*/
public class MetaInfoCommand {
    private  static LinkedHashMap<String, String> fields;

    public MetaInfoCommand() {
        fields = new FieldFetcher().fetchFields();
    }

    public static LinkedHashMap<String, String> getFields() {
        return fields;
    }
}
