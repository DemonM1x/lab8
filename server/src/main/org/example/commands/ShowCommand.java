package main.org.example.commands;

import main.org.example.collection.City;
import main.org.example.utility.Request;
import main.org.example.utility.TypeOfAnswer;
import main.org.example.Receiver;
import main.org.example.utility.Response;

import java.util.Set;

public class ShowCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public ShowCommand(Receiver receiver) {
        super("show", "print to standard output all elements of the collection in string representation" , "", false );
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        Set<City> cities = receiver.show();
        if (cities == null)return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        else return new Response(cities, TypeOfAnswer.SUCCESSFUL);
    }
}
