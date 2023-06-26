package main.org.example.commands;

import main.org.example.collection.City;
import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;

public class RemoveGreaterCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public RemoveGreaterCommand(Receiver receiver) {
        super("remove_greater", "remove from the collection all elements greater than the given", "{element}", true);
        this.receiver = receiver;

    }
    @Override
    public Response execute(Request request) {
        City city = request.getCommand().getCity();
        return new Response(receiver.removeGreater(city, request.getSession().getName()));
    }
}
