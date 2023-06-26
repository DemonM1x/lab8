package com.example.client.utilities;

import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public class ResponseHandler {

    private static ResponseHandler instance;

    private ResponseHandler() {}


    public static ResponseHandler getInstance(){
        if(instance == null) instance = new ResponseHandler();
        return instance;
    }

    public Response receive(ByteBuffer buffer) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return (Response) inputStream.readObject();
        } catch (ClassNotFoundException | InvalidClassException e){
            return new Response(TypeOfAnswer.ANOTHERVERSION);
        }catch (IOException e){
            return new Response(TypeOfAnswer.NETPROBLEM);
        }
    }

    public String receive(String information){
        return information;
    }
}

