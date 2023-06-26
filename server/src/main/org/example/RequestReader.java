package main.org.example;

import main.org.example.utility.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class RequestReader implements Supplier<Request> {

    private final SocketChannel socketChannel;
    private final Logger logger;
    public RequestReader(SocketChannel socketChannel, Logger logger){
        this.socketChannel = socketChannel;
        this.logger = logger;
    }
    @Override
    public Request get(){
        ByteBuffer readBuffer = ByteBuffer.allocate(4096);
        try {
            socketChannel.read(readBuffer);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(readBuffer.array()));
            Request request = AutoGenFieldsSetter.setFields((Request) ois.readObject());
            readBuffer.clear();
            return request;

        }catch (IOException e) {
            logger.info("Клиент отключился.");
            try {
                socketChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        } catch (ClassNotFoundException e) {
            logger.info("Попытка десериализовать неправильный объект.");
            return null;
        }
    }

}
