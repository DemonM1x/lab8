package main.org.example.commands;

import main.org.example.collection.City;
import main.org.example.utility.Request;
import main.org.example.utility.TypeOfAnswer;
import main.org.example.Receiver;
import main.org.example.utility.Response;

import java.util.Set;
import java.util.stream.Collectors;

public class StartsNameCommand extends AbstractCommand implements Execute {

    /**
     * this class represents the filter_starts_with_name command,
     * which, display to user the collection elements whose name begins with the specified substring
     */
    private final Receiver receiver;
    public StartsNameCommand(Receiver receiver) {
        super("filter_starts_with_name", "output elements whose name field value starts"+
                "with the specified substring", "name", false);
        this.receiver = receiver;
    }

    /**
     * the method checks the collection for emptiness and outputs all elements whose name begins with the specified substring
     */
    @Override
    public Response execute(Request request) {
        String substring = request.getCommand().getArg().get(0);
        Set<City> collection = receiver.getMainCollection();
        Set<City> SetOfCity = collection.stream().
                filter(city -> city.getName().startsWith(substring)).
                collect(Collectors.toSet());
        if (collection == null)
            return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        else if (SetOfCity == null)
            return new Response(TypeOfAnswer.OBJECTNOTEXIST);
        else return new Response(SetOfCity, TypeOfAnswer.SUCCESSFUL);

    }
}
