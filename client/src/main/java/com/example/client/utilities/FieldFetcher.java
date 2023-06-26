package com.example.client.utilities;

import main.org.example.annotation.Complex;
import main.org.example.annotation.Date;
import main.org.example.collection.City;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class FieldFetcher {
    /**
     * A class that collects all fields into an array
     * fetchFields method
     */
    public LinkedHashMap<String, String> fetchFields() {
        var resultHashMap = new LinkedHashMap<String, String>();
        Field[] cityClass = City.class.getDeclaredFields();
        for (Field cityField : cityClass) {
            if (cityField.isAnnotationPresent(Complex.class)) {
                resultHashMap.putAll(this.fetchFromField(cityField, "City", new StringBuilder()));
            } else {
                if(cityField.isAnnotationPresent(Date.class)){
                    resultHashMap.put(cityField.getDeclaringClass().getSimpleName() + "." + cityField.getName(),
                            cityField.getType().getSimpleName() + " Format: YYYY-MM-DD");

                }
                else {
                    resultHashMap.put(cityField.getDeclaringClass().getSimpleName() + "." + cityField.getName(),
                            cityField.getType().getSimpleName());
                }

            }
        }
        return resultHashMap;
    }

    public LinkedHashMap<String, String> fetchFromField(Field fetchField, String keyValue, StringBuilder enumValues) {
        var resultHashMap = new LinkedHashMap<String, String>();
        Field[] fetchedClass = fetchField.getType().getDeclaredFields();
        keyValue += "." + fetchField.getType().getSimpleName();
        for (Field fetchedFieldInClass : fetchedClass) {
            if (fetchedFieldInClass.isAnnotationPresent(Complex.class)) {
                resultHashMap.putAll(this.fetchFromField(fetchedFieldInClass, keyValue, enumValues));
            } else {
                if (fetchedFieldInClass.getType().isEnum()) {
                    enumValues.append(fetchedFieldInClass.getName() + ", ");
                } else {
                    if (fetchedFieldInClass.getName().equals("ENUM$VALUES")
                            || fetchedFieldInClass.getName().equals("$VALUES")) {
                        resultHashMap.put(keyValue,
                                "Enum. Values: " + enumValues.delete(enumValues.length() - 2, enumValues.length()).toString());
                    } else {
                        resultHashMap.put(keyValue + "." + fetchedFieldInClass.getName(),
                                fetchedFieldInClass.getType().getSimpleName());
                    }
                }
            }
        }
        return resultHashMap;
    }
}