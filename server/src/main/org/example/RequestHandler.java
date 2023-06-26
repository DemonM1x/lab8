package main.org.example;

import main.org.example.utility.Request;
import main.org.example.utility.Response;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.function.Function;
import java.util.logging.Logger;

public class RequestHandler implements Function<Request, Response> {
    private final CommandManager commandManager;
    private final SocketChannel socketChannel;
    private final Logger logger;

    public RequestHandler(CommandManager commandManager, SocketChannel socketChannel, Logger logger){
        this.commandManager = commandManager;
        this.socketChannel = socketChannel;
        this.logger = logger;
    }
    @Override
    public Response apply(Request request){
        System.out.println(request);
        if (request != null) {
            if (request.getCommand().getCommand().equals("exit")){
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    logger.info("Ошибка в закрытии сокета");
                }
            }
            Task task = new Task(commandManager, request);
            return task.call();
        }else return null;
    }
}
