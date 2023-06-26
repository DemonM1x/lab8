package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;
import main.org.example.collection.City;

public class AddCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;

    public AddCommand(Receiver receiver) {
        super("add", "add a new element to the collection", "{element}", true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        City city = request.getCommand().getCity();
        System.out.println(city);
        return new Response(receiver.add(city));
    }

}
