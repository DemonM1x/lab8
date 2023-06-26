package main.org.example;

import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Deliver implements Consumer<Response> {
    private final SelectionKey key;
    private final Set<SelectionKey> workingKeys;
    private final Logger logger;


    public Deliver(SelectionKey key, Set<SelectionKey> workingKeys, Logger logger) {

        this.key = key;
        this.workingKeys = workingKeys;
        this.logger = logger;
    }
    @Override
    public void accept(Response response){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {

            if (response == null) {
                response = new Response("Сервер не смог обработать запрос.", TypeOfAnswer.SUCCESSFUL);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
            byte[] b = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(b);
            socketChannel.write(buffer);
        }catch (IOException e){
            try {
                socketChannel.close();
            }catch (IOException e1){
                logger.info("Ошибка в закрытии сокета");
            }

        }
        logger.info("Сервер отправил ответ клиенту");
        workingKeys.remove(key);
    }
}
