package com.example.client.validatorClient;

public class ValidateMetersAboveSeaLevel extends ValidateAbstract<Double> {
    public ValidateMetersAboveSeaLevel() {
        super("City.metersAboveSeaLevel" , "");
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }

    @Override
    public boolean validate(String value) {
        Double val;
        try {
            val = Double.parseDouble(value);
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
