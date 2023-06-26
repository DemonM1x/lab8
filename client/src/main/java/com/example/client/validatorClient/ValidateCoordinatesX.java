package com.example.client.validatorClient;



public class ValidateCoordinatesX extends ValidateAbstract<Integer> {

    public ValidateCoordinatesX() {
        super("City.Coordinates.x" , "");
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public boolean validate(String value) {
        Integer val;
        try {
            val = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validate(String[] value) {
        return false;
    }
}