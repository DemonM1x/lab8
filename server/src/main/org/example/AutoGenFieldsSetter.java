package main.org.example;

import main.org.example.collection.City;
import main.org.example.utility.Request;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

public class AutoGenFieldsSetter {
    public static Request setFields(Request aRequest) {
        City city = aRequest.getCommand().getCity();
        String author = aRequest.getSession().getName();

        if (city != null) {
            city.setCreationDate(Date.from(Instant.now()));
            city.setAuthor(author);
        }
        return aRequest;
    }
}
