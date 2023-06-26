package main.org.example.utility;

public enum DataInOutStatus {
    FAILED ("Failed"),
    SUCCESSFULLY("Successfully"),
    WRONGARGS ("Wrong args"),
    NOCOMMAND ("No command");

    private final String name;

    public String getName(){
        return name;
    }

    DataInOutStatus(String message) {
        name = message;
    }

}
