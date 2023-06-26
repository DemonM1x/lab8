package main.org.example.DataAdapters;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * this class formats localDateTime for parsing
 */
public class DateTimeAdapterLocal  {
    DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public String marshal(LocalDateTime v){
        return v.format(ISO_FORMATTER);
    }
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v);
    }
}
