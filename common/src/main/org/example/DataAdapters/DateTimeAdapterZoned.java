package main.org.example.DataAdapters;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * this class formats ZonedDateTime for parsing
 */
public class DateTimeAdapterZoned  {
    DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public String marshal(ZonedDateTime v){
        return v.format(ISO_FORMATTER);
    }
    public ZonedDateTime unmarshal(String v) {
        return ZonedDateTime.parse(v);
    }
}
