package com.example.client.utilities;

import java.util.LinkedList;
import java.util.List;

/**
 * this class communicates with the user
 */
public class MessageHandler {
    private final LinkedList<String> logs;
    {
        logs = new LinkedList<>();
    }
    /** displays the message to user */
    public static void displayToUser(String message){
        System.out.println(message);
    }

    /**
     *  gets the logs collection
     * @return list of logs
     */
    public List<String> getLogs(){
        return logs;
    }
}
