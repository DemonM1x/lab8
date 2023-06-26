package com.example.client.validatorClient;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class ValidateBirthday extends ValidateAbstract<LocalDateTime> {
    public ValidateBirthday() {
        super("City.Human.birthday" , "not null");
    }

    @Override
    public Class<LocalDateTime> getType() {
        return LocalDateTime.class;
    }

    @Override
    public boolean validate(String variable) {
        try {
            String[] args = variable.split("-");
            LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), 0, 0, 0, 0);
            return variable != null && args.length==3;
        } catch (IndexOutOfBoundsException | DateTimeException | NullPointerException | NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validate(String[] value) {
        return false;
    }
}
