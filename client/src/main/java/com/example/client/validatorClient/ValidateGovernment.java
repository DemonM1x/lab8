package com.example.client.validatorClient;

import main.org.example.collection.Government;

public class ValidateGovernment extends ValidateAbstract<Government> {
    public ValidateGovernment() {
        super("City.Government" , "");
    }

    @Override
    public Class<Government> getType() {
        return Government.class;
    }

    @Override
    public boolean validate(String value) {
        try {
            var valueEnum = Government.valueOf(value);
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean validate(String[] value) {
        return false;
    }
}
