package com.example.client.validatorClient;


import java.util.ArrayList;
import java.util.LinkedHashMap;

/**Class to store all validators.
 * Call the required validator by field name
 */

public class ValidatorManager {
    private LinkedHashMap<String, ValidateAbstract<?>> validatorList;
    private ArrayList<String> validatorNameList;

    public ValidatorManager() {
        validatorList = new LinkedHashMap<String, ValidateAbstract<?>>();
        validatorList.put("id", null);
        ValidateName validateName = new ValidateName();
        validatorList.put(validateName.getName(), validateName);
        ValidateCoordinatesX coordinatesX = new ValidateCoordinatesX();
        validatorList.put(coordinatesX.getName(), coordinatesX);
        ValidatorCoordinatesY coordinatesY = new ValidatorCoordinatesY();
        validatorList.put(coordinatesY.getName(), coordinatesY);
        validatorList.put("creationDate", null);
        ValidateArea validateArea = new ValidateArea();
        validatorList.put(validateArea.getName(), validateArea);
        ValidatePopulation validatePopulation = new ValidatePopulation();
        validatorList.put(validatePopulation.getName(), validatePopulation);
        ValidateMetersAboveSeaLevel validateMetersAboveSeaLevel = new ValidateMetersAboveSeaLevel();
        validatorList.put(validateMetersAboveSeaLevel.getName(), validateMetersAboveSeaLevel);
        ValidateClimate validateClimate = new ValidateClimate();
        validatorList.put(validateClimate.getName(), validateClimate);
        ValidateGovernment validateGovernment = new ValidateGovernment();
        validatorList.put(validateGovernment.getName(), validateGovernment);
        ValidateStandardOfLiving validateStandardOfLiving = new ValidateStandardOfLiving();
        validatorList.put(validateStandardOfLiving.getName(), validateStandardOfLiving);
        var humanBirthday = new ValidateBirthday();
        validatorList.put(humanBirthday.getName(), humanBirthday);
    }

    public ValidateAbstract<?> getValidator(String fildName) {
        return validatorList.get(fildName);
    }
}

