package com.example.client.utilities;


import main.org.example.collection.City;
import main.org.example.utility.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class RequestHandler {
    private static RequestHandler instance;
    private InetSocketAddress socketAddress;
    private SocketChannel socketChannel;
    private Session session;

    private boolean socketStatus;

    public static RequestHandler getInstance(){
        if(instance == null) instance = new RequestHandler();
        return instance;
    }

    private RequestHandler() {

    }

    public Response send(CommandFactory commandFactory) {
        try {
            Request request = new Request(commandFactory, session);
            ClientWorker clientWorker = new ClientWorker(socketChannel);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(request);
            return clientWorker.sendRequest(byteArrayOutputStream.toByteArray());
        }  catch (IOException e) {
            return new Response(TypeOfAnswer.NOTSERIALIZED);
        }
    }

    public Response send(CommandFactory commandFactory , City city){
        if(city != null) {
            commandFactory.addCity(city);
            return send(commandFactory);
        }
        return new Response(TypeOfAnswer.OBJECTNOTEXIST);
    }

    public void setRemoteHostSocketAddress(InetSocketAddress aSocketAddress){
        socketAddress = aSocketAddress;
    }
    public InetSocketAddress getRemoteHostSocketAddress(){
        return socketAddress;
    }
    public void setRemoteHostSocketChannel(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }
    public String getInformation(){
        return "Connection\n" +  "remote host address: " + socketAddress;
    }

    public void setSocketStatus(boolean b) {
        socketStatus = b;
    }

    public boolean getSocketStatus(){
        return socketStatus;
    }

    public void setSession(Session aSession) {
        session = aSession;
    }
    public Session getSession(){
        return this.session;
    }
    public Response register(Session aSession) {
        setSession(aSession);
        return send(new CommandFactory("register", new ArrayList<String>()));
    }
    public Response login(Session aSession) {
        setSession(aSession);
        return send(new CommandFactory("login", new ArrayList<>()));
    }
}
