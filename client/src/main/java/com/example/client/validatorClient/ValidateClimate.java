package com.example.client.validatorClient;

import main.org.example.collection.Climate;

public class ValidateClimate extends ValidateAbstract<Climate>{
    public ValidateClimate() {
        super("City.Climate" , "not null");
    }

    @Override
    public Class<Climate> getType() {
        return Climate.class;
    }

    @Override
    public boolean validate(String value) {
        try {
            var valueEnum = Climate.valueOf(value);
            if(valueEnum != null) {
                return true;
            }
            return false;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean validate(String[] value) {
        return false;
    }
}
