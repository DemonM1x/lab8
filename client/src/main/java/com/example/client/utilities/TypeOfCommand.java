package com.example.client.utilities;

public enum TypeOfCommand {
    Help("Help"),
    Info("Info"),
    Show("Show"),
    Clear("Clear"),
    Add("Add"),
    Add_if_max("Add_if_max"),
    Add_if_min("Add_if_min"),
    Remove_greater("Remove_greater"),
    Remove_lower("Remove_lower"),
    Execute_script("Execute_script"),
    Update("Update"),
    Register("Register"),
    Login("Login"),
    Exit("Exit");
    private final String value;

    TypeOfCommand(String aValue) {
        value = aValue;
    }

    public String getValue() {
        return value;
    }

    public static TypeOfCommand getEnum(String value) {
        if (value != null) {
            for (TypeOfCommand v : values())
                if (v.getValue().equalsIgnoreCase(value)) return v;
        }
        return null;
    }
}
