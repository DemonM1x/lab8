package com.example.client.validatorClient;

public class ValidateArea extends ValidateAbstract<Float> {
    public ValidateArea() {
        super("City.area" , "> 0");
    }

    @Override
    public Class<Float> getType() {
        return Float.class;
    }

    @Override
    public boolean validate(String value) {
        Float val;
        try {
            val = Float.parseFloat(value);
            if (val > 0) {
                return true;
            }

            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validate(String[] value) {
        return false;
    }

}
