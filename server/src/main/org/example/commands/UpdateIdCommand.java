package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;
import main.org.example.collection.City;

public class UpdateIdCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public UpdateIdCommand(Receiver receiver) {
        super("update", "update the value of the collection element whose" +
                " id is equal to the given one", "id {element}" , true);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
       City city = request.getCommand().getCity();
       Integer id = Integer.parseInt(request.getCommand().getArg().get(0));

        return new Response(receiver.updateId(city, id));
    }
}

