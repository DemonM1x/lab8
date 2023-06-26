package main.org.example.commands;

import main.org.example.utility.Request;
import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;

import java.util.Collection;

public class HelpCommand extends AbstractCommand implements Execute {
    Collection<AbstractCommand> collectionOfCommands;

    public HelpCommand(Collection<AbstractCommand> collectionOfCommands) {
        super("help", "display help on available commands"  , "", false );
        this.collectionOfCommands = collectionOfCommands;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder execution = new StringBuilder();
        for (Execute command : collectionOfCommands) {
            execution.append(command.toString()).append("\n");
        }
        execution.delete(execution.toString().length() - 1, execution.toString().length());
        return new Response(execution.toString(), TypeOfAnswer.SUCCESSFUL);
    }
}
