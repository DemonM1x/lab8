package com.example.client.factores;

import main.org.example.collection.Human;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HumanFactory {
    public Human createHuman(String args){
    ;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = format.parse(args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    Human newHuman = new Human(birthday);
    return newHuman;
    }
}
