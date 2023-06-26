package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;

public class ClearCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public ClearCommand(Receiver receiver) {
        super("clear", "clear the collection" , "", true);
        this.receiver = receiver;
    }


    @Override
    public Response execute(Request request) {
        return new Response(receiver.clearCollection(request.getSession().getName()));
    }
}
