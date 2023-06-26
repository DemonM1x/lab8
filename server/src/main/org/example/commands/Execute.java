package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.utility.Response;

public interface Execute {
    Response execute(Request aRequest);
}
