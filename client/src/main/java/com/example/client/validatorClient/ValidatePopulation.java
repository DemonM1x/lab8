package com.example.client.validatorClient;

public class ValidatePopulation extends ValidateAbstract<Long>{
    public ValidatePopulation() {
        super("City.population" , "> 0");
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public boolean validate(String value) {
        Long val;
        try {
            val = Long.parseLong(value);
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
