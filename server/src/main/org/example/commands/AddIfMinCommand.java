package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;
import main.org.example.collection.City;

public class AddIfMinCommand extends AbstractCommand implements Execute{
    private final Receiver receiver;
    public AddIfMinCommand(Receiver receiver){
        super("add_if_min","add new element to the collection if element is minimal","{element}", true);
        this.receiver = receiver;
    }
    public Response execute(Request request){
        City city = request.getCommand().getCity();
        TypeOfAnswer status = receiver.addIfMin(city);
        return new Response(status);
    }
}
