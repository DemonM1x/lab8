package com.example.client.validatorClient;

public class ValidateHuman extends ValidateAbstract<String>{
    public ValidateHuman() {
        super("City.Human" , "");
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }

    @Override
    public boolean validate(String[] value) {
        try {
            var birthdayValidate = new ValidateBirthday();
            return birthdayValidate.validate(value);
        }catch (IndexOutOfBoundsException e) {
            return false;
        }

    }
}
