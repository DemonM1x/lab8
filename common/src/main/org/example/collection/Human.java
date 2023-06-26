package main.org.example.collection;


import java.io.Serializable;
import java.util.Date;

public class Human implements Serializable {
    public Human(){

    }

    private Date birthday;

    public Human(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }
}
