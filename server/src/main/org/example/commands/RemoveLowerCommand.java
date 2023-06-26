package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;
import main.org.example.collection.City;

public class RemoveLowerCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public RemoveLowerCommand(Receiver receiver) {
        super("remove_lower", "remove from the collection all elements lower than the given", "{element}", true);
        this.receiver = receiver;

    }
    @Override
    public Response execute(Request request){
        City city = request.getCommand().getCity();
        String username = request.getSession().getName();
        return new Response(receiver.removeLower(city, username));
    }
}
