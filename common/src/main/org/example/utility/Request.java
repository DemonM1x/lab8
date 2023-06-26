package main.org.example.utility;

import java.io.Serializable;

public class Request implements Serializable {

    private final CommandFactory command;
    private Session session;

    public Request(CommandFactory commandFactory, Session aSession) {
        command = commandFactory;
        this.session = aSession;

    }

    public CommandFactory getCommand(){
        return command;
    }

    public Session getSession(){
        return session;
    }

    @Override
    public String toString() {
        return command.toString() + "from (" + session.toString() + ")";
    }

}
