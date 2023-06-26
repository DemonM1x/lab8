package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.utility.TypeOfAnswer;
import main.org.example.Receiver;
import main.org.example.utility.Response;
import main.org.example.collection.City;

public class AddIfMaxCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public AddIfMaxCommand(Receiver receiver) {
        super("add_if_max", "add a new element to the collection if its value is greater than the value of the largest element in this collection" , "{element}" , true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        City city = request.getCommand().getCity();
        TypeOfAnswer status = receiver.addIfMax(city);
        return new Response(status);
    }
}
