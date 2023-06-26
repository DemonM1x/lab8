package com.example.client.create;

import com.example.client.DataHolder;
import com.example.client.utilities.AlertUtility;
import com.example.client.utilities.HumanFactory;
import com.example.client.utilities.RequestHandler;
import com.example.client.validatorClient.ValidateAbstract;
import com.example.client.validatorClient.ValidatorManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.org.example.collection.*;
import main.org.example.utility.CommandFactory;
import main.org.example.utility.Response;
import main.org.example.utility.TypeOfAnswer;

import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class CityManagementWindowController implements Initializable {
    @FXML
    private Label actionLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField coordXField;
    @FXML
    private TextField coordYField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField populationField;
    @FXML
    private TextField metersAboveSeaLevelField;
    @FXML
    private ChoiceBox<String> climateChoiceBox;
    @FXML
    private ChoiceBox<String> governmentChoiceBox;
    @FXML
    private ChoiceBox<String> standardsChoiceBox;
    @FXML
    private TextField governorField;

    private Map<String, Boolean> validationState;
    private String actionText;
    private Map<Long, String> ownershipMap; // Map of (city_id, client_name)
    private City selectedCity;

    @Override
    public void initialize(URL url, ResourceBundle locale) {
        actionLabel.setText(actionText);
        Arrays.stream(Climate.values()).forEach(value -> climateChoiceBox.getItems().add(value.name()));
        Arrays.stream(Government.values()).forEach(value -> governmentChoiceBox.getItems().add(value.name()));
        Arrays.stream(StandardOfLiving.values()).forEach(value -> standardsChoiceBox.getItems().add(value.name()));
        validationState = new HashMap<>();
        validation();
    }

    public void populateFields(City city) {
        if (city != null) {
            nameField.setText(city.getName());
            // Convert numeric types to String before setting the text
            coordXField.setText(String.valueOf(city.getCoordinates().getX()));
            coordYField.setText(String.valueOf(city.getCoordinates().getY()));
            areaField.setText(String.valueOf(city.getArea()));
            populationField.setText(String.valueOf(city.getPopulation()));

            // Check if metersAboveSeaLevel is not null before converting it to String
            if (city.getMetersAboveSeaLevel() != null) {
                metersAboveSeaLevelField.setText(String.valueOf(city.getMetersAboveSeaLevel()));
            }

            // Populate choice boxes
            climateChoiceBox.setValue(city.getClimate().toString());
            governmentChoiceBox.setValue(city.getGovernment().toString());
            standardsChoiceBox.setValue(city.getStandardOfLiving().toString());

            // Check if governor is not null before getting its name
            if (city.getGovernor() != null) {
                governorField.setText(city.getGovernor().getBirthday().toString());
            }
        }
    }

    private void updateUI() {
        actionLabel.setText(actionText);
    }

    /**
     * This method is invoked when the "Create" button is clicked.
     * It validates the input fields and, if valid, creates a new City object and sends it to the server.
     * If the fields are not valid, it shows an error message.
     */
    @FXML
    protected void onSaveButtonClick() {
        if (validationState.values().stream().allMatch(valid -> valid) &&
                climateChoiceBox.getValue() != null &&
                governmentChoiceBox.getValue() != null &&
                standardsChoiceBox.getValue() != null) {

            java.util.Date creationDate;
            Integer id;
            if (actionText.split(" ")[0].equals("Editing")) {
                 id = selectedCity.getId();
                creationDate = selectedCity.getCreationDate();
            } else {
                creationDate = java.sql.Date.valueOf(LocalDate.now());
                 id = 0;
            }

            String name = nameField.getText();
            Coordinates coordinates = new Coordinates(Integer.parseInt(coordXField.getText()), Integer.parseInt(coordYField.getText()));
            Float area = Float.valueOf(areaField.getText());
            Long population = Long.parseLong(populationField.getText());
            Double metersAboveSeaLevel = Double.valueOf(metersAboveSeaLevelField.getText());
            Climate climate = climateChoiceBox.getValue() != null ? Climate.valueOf(climateChoiceBox.getValue()) : null;
            Government government = governmentChoiceBox.getValue() != null ? Government.valueOf(governmentChoiceBox.getValue()) : null;
            StandardOfLiving standards = standardsChoiceBox.getValue() != null ? StandardOfLiving.valueOf(standardsChoiceBox.getValue()) : null;
            Human human = new HumanFactory().createHuman(governorField.getText());
            String author = RequestHandler.getInstance().getSession().getName();
            City city = new City(id, name, coordinates, creationDate, area, population,
                    metersAboveSeaLevel, climate, government, standards, human, author);

            try {


                if (actionText.split(" ")[0].equals("Editing")) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(id.toString());
                    CommandFactory commandFactory = new CommandFactory("remove_by_id", list);
                    Response response = RequestHandler.getInstance().send(commandFactory);
                    DataHolder.getInstance().setResponse(response);
                } else {
                    CommandFactory commandFactory = new CommandFactory("add", null);
                    Response response = RequestHandler.getInstance().send(commandFactory, city);
                    DataHolder.getInstance().setResponse(response);
                }

            }catch (Exception e){
                AlertUtility.errorAlert("Can't load commands from server. Please wait until the server will come back");
            }
            Response response = DataHolder.getInstance().getResponse();
                if (response.getStatus() == TypeOfAnswer.PERMISSIONDENIED){
                    AlertUtility.infoAlert(response.toString());
                }
                else if (response.getStatus() == TypeOfAnswer.SUCCESSFUL && actionText.split(" ")[0].equals("Editing")){
                    try {
                        CommandFactory commandFactory = new CommandFactory("add", null);
                        RequestHandler.getInstance().send(commandFactory, city);
                    }catch (Exception e){
                        AlertUtility.errorAlert("Can't load commands from server. Please wait until the server will come back");
                    }
                }
                else if (response.getStatus() == TypeOfAnswer.SUCCESSFUL){
                    AlertUtility.infoAlert("City added successfully");
                }
                else {
                    AlertUtility.errorAlert("idk why but you're object is not added, or server is just taking a nap");
                }

            ((Stage) nameField.getScene().getWindow()).close();
        }
    }

    /**
     * This method is invoked when the "Cancel" button is clicked.
     * It closes the Create Window.
     */
    @FXML
    protected void onCancelButtonClick() {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private void validation() {
        ValidatorManager validatorManager = new ValidatorManager();
        ValidateAbstract<?> validator1 = validatorManager.getValidator("City.name");
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = validator1.validate(newValue);
            validationState.put("name", valid);
            updateValidationState(nameField, valid, "Name is not valid. " + validator1.getRestriction());
        });


       ValidateAbstract<?> validator2 = validatorManager.getValidator("City.Coordinates.x");
        coordXField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("\\d+") && validator2.validate(newValue);
                validationState.put("coordX", valid);
                updateValidationState(coordXField, valid, "CoordX is not valid. " + validator2.getRestriction());
            } catch (NumberFormatException e) {
                updateValidationState(coordXField, false, "CoordX is not valid. It should be a number.");
            }
        });

        ValidateAbstract<?> validator3 = validatorManager.getValidator("City.Coordinates.y");
        coordYField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && validator3.validate(newValue);
                validationState.put("coordY", valid);
                updateValidationState(coordYField, valid, "CoordY is not valid. " + validator3.getRestriction());
            } catch (NumberFormatException e) {
                updateValidationState(coordYField, false, "CoordY is not valid. It should be a number.");
            }
        });

       ValidateAbstract<?> validator4 = validatorManager.getValidator("City.area");
        areaField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && validator4.validate(newValue);
                validationState.put("area", valid);
                updateValidationState(areaField, valid, "Area is not valid. " + validator4.getRestriction());
            } catch (NumberFormatException e) {
                updateValidationState(areaField, false, "Area is not valid. It should be a number.");
            }
        });
       ValidateAbstract<?> validator5 = validatorManager.getValidator("City.population");
        populationField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("\\d+") && validator5.validate(newValue);
                validationState.put("population", valid);
                updateValidationState(populationField, valid, "Population is not valid. " + validator5.getRestriction());
            } catch (NumberFormatException e) {
                updateValidationState(populationField, false, "Population is not valid. It should be a number.");
            }
        });
        ValidateAbstract<?> validator6 = validatorManager.getValidator("City.metersAboveSeaLevel");
        metersAboveSeaLevelField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && validator6.validate(newValue);
                validationState.put("seaLevel", valid);
                updateValidationState(metersAboveSeaLevelField, valid, "Meters Above Sea Level is not valid. " + validator6.getRestriction());
            } catch (NumberFormatException e) {
                updateValidationState(metersAboveSeaLevelField, false, "Meters Above Sea Level is not valid. It should be a number.");
            }
        });

        climateChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = newValue != null && Arrays.stream(Climate.values()).map(Enum::name).toList().contains(newValue);
            validationState.put("climate", valid);
            updateValidationState(climateChoiceBox, valid, "Climate is not valid. It should be one of " + Arrays.toString(Climate.values()));
        });

        governmentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = newValue != null && Arrays.stream(Government.values()).map(Enum::name).toList().contains(newValue);
            validationState.put("government", valid);
            updateValidationState(governmentChoiceBox, valid, "Government is not valid. It should be one of " + Arrays.toString(Government.values()));
        });

        standardsChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = newValue != null && Arrays.stream(StandardOfLiving.values()).map(Enum::name).toList().contains(newValue);
            validationState.put("standards", valid);
            updateValidationState(standardsChoiceBox, valid, "Standards is not valid. It should be one of " + Arrays.toString(StandardOfLiving.values()));
        });
        ValidateAbstract<?> validator7 = validatorManager.getValidator("City.Human.birthday");
        governorField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = validator7.validate(newValue);
            validationState.put("governor", valid);
            updateValidationState(governorField, valid, "Birthday is not valid. " + validator7.getRestriction());
        });
    }

    private void updateValidationState(TextField field, boolean valid, String message) {
        if (valid) {
            field.setStyle("-fx-background-color: #00ff00;"); //green color
            field.setTooltip(null);
        } else {
            field.setStyle("-fx-background-color: #ff0000;"); //red color
            field.setTooltip(new Tooltip(message));
        }
    }

    private void updateValidationState(ChoiceBox<String> choiceBox, boolean valid, String message) {
        if (valid) {
            choiceBox.setStyle("-fx-background-color: #00ff00;"); //green color
            choiceBox.setTooltip(null);
        } else {
            choiceBox.setStyle("-fx-background-color: #ff0000;"); //red color
            choiceBox.setTooltip(new Tooltip(message));
        }
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
        updateUI();
    }

    public void setEditingCity(City city) {
        this.selectedCity = city;
        populateFields(selectedCity);
    }
}
