package com.example.client.validatorClient;


public class ValidatorCoordinates extends ValidateAbstract<String[]> {

    public ValidatorCoordinates() {
        super("Coordinates" , "");
    }

    @Override
    public Class<String[]> getType() {
        return String[].class;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }

    @Override
    public boolean validate(String[] args) {
        try {
            if (new ValidateCoordinatesX().validate(args[0]) && new ValidatorCoordinatesY().validate(args[1])) {
                return true;
            } else {
                return false;
            }
        }catch (IndexOutOfBoundsException e) {
            return false;
        }

    }
}
