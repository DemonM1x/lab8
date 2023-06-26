package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.Receiver;
import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;


public class InfoCommand extends AbstractCommand implements Execute {
    private final Receiver receiver;
    public InfoCommand(Receiver receiver) {
        super("info", "print information about the collection to the standard output stream (initialization date, number of elements, etc)" , "", false);
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size of collection - " + receiver.getMainCollection().size() + "\n");
        stringBuilder.append("Data initialization - " + receiver.getDateOfInitialization() + "\n");
        stringBuilder.append("Data of last change - " + receiver.getDateOfLastChange());
        return new Response(stringBuilder.toString(), TypeOfAnswer.SUCCESSFUL);
    }
}
