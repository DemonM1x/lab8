package main.org.example;

import main.org.example.utility.Request;
import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;
import main.org.example.commands.*;

import java.util.HashMap;

/**
 * this class adds commands to the commandsСontainer and checks for the existence of a command in the container
 */
public class CommandManager{
    private static HashMap<String, AbstractCommand> commandsMap;
    private final Receiver receiver;
    /**
     * the constructor accepts commands as input and adds them to the commandsContainer
     */
    public CommandManager(Receiver receiver){
        this.receiver = receiver;
        commandsMap = new HashMap<>();
        var helpCommand = new HelpCommand(commandsMap.values());
        var infoCommand = new InfoCommand(receiver);
        var clearCommand = new ClearCommand(receiver);
        var showCommand = new ShowCommand(receiver);
        var addCommand = new AddCommand(receiver);
        var updateCommand = new UpdateIdCommand(receiver);
        var executeCommand = new ExecuteScriptCommand();
        var addMaxCommand = new AddIfMaxCommand(receiver);
        var addMinCommand = new AddIfMinCommand(receiver);
        var removeGreaterCommand = new RemoveGreaterCommand(receiver);
        var minById = new MinByIdCommand(receiver);
        var filterStartsWithName = new StartsNameCommand(receiver);
        var removeLowerCommand = new RemoveLowerCommand(receiver);
        var removeById = new RemoveByIdCommand(receiver);
        var register = new RegisterUserCommand(receiver);
        var login = new LoginUserCommand(receiver);
        commandsMap.put("add",addCommand);
        commandsMap.put("clear",clearCommand);
        commandsMap.put("remove_greater", removeGreaterCommand);
        commandsMap.put("add_if_max", addMaxCommand);
        commandsMap.put("add_if_min", addMinCommand);
        commandsMap.put("remove_by_id", removeById);
        commandsMap.put("remove_lower", removeLowerCommand);
        commandsMap.put("update", updateCommand);
        commandsMap.put("show", showCommand);
        commandsMap.put("min_by_id", minById);
        commandsMap.put("help", helpCommand);
        commandsMap.put("info", infoCommand);
        commandsMap.put("filter_starts_with_name", filterStartsWithName);
        commandsMap.put("execute_script", executeCommand);
        commandsMap.put("register", register);
        commandsMap.put("login", login);
    }
    public Response execute(Request request) {

        String command = request.getCommand().getCommand();
        String username = request.getSession().getName();
        String password = request.getSession().getPassword();
        return (!commandsMap.get(command).getAuthorizationStatus()) ? commandsMap.get(command).execute(request)
                : (command.equals("register") || receiver.loginUser(username, password))
                ?commandsMap.get(command).execute(request)
                :new Response(TypeOfAnswer.NOTMATCH);
    }
    public static HashMap<String, AbstractCommand> getCommandsMap(){
        return commandsMap;
    }
}
