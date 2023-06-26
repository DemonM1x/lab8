package com.example.client.validatorClient;

/**Abstract class for validating entered data from the console */
public abstract class ValidateAbstract<E> {
    private String name;
    private String restriction;

    public ValidateAbstract(String name , String restriction) {
        this.name = name;
        this.restriction = restriction;
    }

    public String getName() {
        return name;
    }

    public String getRestriction(){
        return restriction;
    }

    public abstract Class<E> getType();
    public abstract boolean validate(String value);
    public abstract boolean validate(String[] value);
}