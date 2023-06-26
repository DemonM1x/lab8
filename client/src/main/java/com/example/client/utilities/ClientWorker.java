package com.example.client.utilities;

import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientWorker {

    private SocketChannel socketChannel;
    private final ResponseHandler responseHandler;

    public ClientWorker(SocketChannel socketChannel) {
        responseHandler = ResponseHandler.getInstance();
        this.socketChannel = socketChannel;

    }

    public Response sendRequest(byte[] dataToSend) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(dataToSend);
            socketChannel.write(buffer);
            return receiveAnswer();
        } catch (IOException exception) {
            RequestHandler.getInstance().setSocketStatus(false);
            return new Response(TypeOfAnswer.COMMANDNOTGO);

        }
    }

    public Response receiveAnswer() {
        long timeStart = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        try {
            socketChannel.read(buffer);
            if (buffer.position() != 0) {
                return responseHandler.receive(buffer);
            }
        } catch (IOException ignored) {
            RequestHandler.getInstance().setSocketStatus(false);
            return new Response(TypeOfAnswer.SERVERNOTAVAILABLE);
        }
        return new Response(TypeOfAnswer.NETPROBLEM);
    }
}