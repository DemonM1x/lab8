package com.example.client.utilities;

import main.org.example.collection.Human;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class HumanFactory {
    public Human createHuman(String args){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(args);
            Human newHuman = new Human(date);
            return newHuman;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
