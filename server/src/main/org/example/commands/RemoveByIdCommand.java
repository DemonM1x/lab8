package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;

import java.util.ArrayList;

public class RemoveByIdCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public RemoveByIdCommand(Receiver receiver) {
        super("remove_by_id", "remove element from collection by its id" , "id" , true);
        this.receiver = receiver;

    }

    @Override
    public Response execute(Request request) {
        System.out.println(request);
        String username = request.getSession().getName();
        ArrayList<String> anArg = request.getCommand().getArg();
        Integer anId = Integer.parseInt(anArg.get(0));
        return new Response(receiver.removeID(username, anId));
    }
}
