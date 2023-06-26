package com.example.client.collections;

import com.example.client.DataHolder;
import com.example.client.worldMap.WorldMapWindow;
import com.example.client.create.CityManagementWindow;
import com.example.client.login.LoginWindow;
import com.example.client.music.MusicWindow;
import com.example.client.utilities.AlertUtility;
import com.example.client.utilities.RequestHandler;
import com.example.client.utilities.ScriptReader;
import com.example.client.visualization.VisualizationWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.org.example.collection.City;
import main.org.example.collection.Climate;
import main.org.example.collection.Government;
import main.org.example.collection.StandardOfLiving;
import main.org.example.utility.CommandFactory;
import main.org.example.utility.Response;
import main.org.example.utility.Session;

import java.io.File;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionsWindowController {
    @FXML
    private Button sumOfMetersAboveSeaLevelButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button removeById;
    @FXML
    private TextField removeByIdTextField;
    @FXML
    private Button exitButton;

    @FXML
    private Button helpButton;


    @FXML
    private ToolBar commandsToolBar;
    @FXML
    private Button commandsButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button createButton;
    @FXML
    private Button visualizeButton;
    @FXML
    private Button worldMapButton;
    @FXML
    private Label filterByLabel;
    @FXML
    private Label citiesCountLabel;
    @FXML
    private Label citiesLabel;
    private ResourceBundle currentBundle;
    private Stage stage;

    private FileChooser fileChooser;
    private String scriptPath;
    private static Map<Integer, String> ownershipMap = new HashMap<>(); // Map of (city_id, client_name)
    private Map<String, Color> clientColorMap = new HashMap<>();
    private final List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("da"),
            new Locale("cs")
    );

    private int currentLocaleIndex = 0;
    private Set<City> collection;
    @FXML
    private TableView<City> table;
    @FXML
    private TableColumn<City, Integer> idColumn;
    @FXML
    private TableColumn<City, String> nameColumn;
    @FXML
    private TableColumn<City, Integer> coordXColumn;
    @FXML
    private TableColumn<City, Integer> coordYColumn;
    @FXML
    private TableColumn<City, String> creationColumn;
    @FXML
    private TableColumn<City, Float> areaColumn;
    @FXML
    private TableColumn<City, Long> populationColumn;
    @FXML
    private TableColumn<City, Double> metersAboveSeaLevelColumn;
    @FXML
    private TableColumn<City, String> governmentColumn;
    @FXML
    private TableColumn<City, String> standardsColumn;
    @FXML
    private TableColumn<City, String> climateColumn;
    @FXML
    private TableColumn<City, String> governorColumn;
    @FXML
    private TableColumn<City, String> authorColumn;

    @FXML
    private Text usernameText;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField filterByText;
    private String currentProperty;

    @FXML
    public void initialize() {
        currentBundle = ResourceBundle.getBundle("com.example.client.MessagesBundle", supportedLocales.get(currentLocaleIndex));
        updateUI();
        // init graphics stuff
        comboBox.getItems().addAll("id", "name", "coord X", "coord Y", "creation", "area", "population", "government", "standards", "climate", "governor");
        Font.loadFont(getClass().getResourceAsStream("/fonts/ZCOOLXiaoWei-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/YouSheBiaoTiHei Regular.ttf"), 14);
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        coordXColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCoordinates().getX()).asObject());
        coordYColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCoordinates().getY()).asObject());
        creationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getDate(cellData.getValue().getCreationDate())));
        areaColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getArea()).asObject());
        populationColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getPopulation()).asObject());
        metersAboveSeaLevelColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMetersAboveSeaLevel()).asObject());
        climateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClimate().toString()));
        governmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGovernment().toString()));
        standardsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStandardOfLiving().toString()));
        governorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGovernor() != null ? getDate(cellData.getValue().getGovernor().getBirthday()) : ""));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        idColumn.setPrefWidth(30);
        governorColumn.setPrefWidth(120);
        creationColumn.setPrefWidth(135);
        climateColumn.setPrefWidth(60);
        coordXColumn.setPrefWidth(55);
        coordYColumn.setPrefWidth(55);
        standardsColumn.setPrefWidth(70);
        nameColumn.setPrefWidth(120);
        metersAboveSeaLevelColumn.setPrefWidth(70);
        authorColumn.setPrefWidth(70);

        // init username
        String currentUsername = RequestHandler.getInstance().getSession().getName();
        usernameText.setText(currentUsername);
        loadOwnershipMap();
        table.setRowFactory(tv -> new TableRow<City>() {
            @Override
            public void updateItem(City city,  boolean empty){
                super.updateItem(city, empty);
                if (city == null) {
                    setStyle("");
                }else {

                    Color color = clientColorMap.get(city.getAuthor());
                    String rgb = String.format("#%02X%02X%02X",
                            (int)(color.getRed() * 255),
                            (int)(color.getGreen() * 255),
                            (int)(color.getBlue() * 255));
                    setStyle("-fx-border-color: " + rgb + ";");
                }
            }
                });
        filterByText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty() && !comboBox.getSelectionModel().isEmpty())  {
                String selectedValue = comboBox.getSelectionModel().getSelectedItem();
                currentProperty = selectedValue;
                filterCities(selectedValue, newValue);
            } else {
                // If the TextField is empty, show all cities
                table.setItems(FXCollections.observableArrayList(collection));
            }
        });


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> loadCollection()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
    private void loadOwnershipMap() {
        Session session = RequestHandler.getInstance().getSession();
        CommandFactory commandFactory = new CommandFactory("show", null);
        Response response = RequestHandler.getInstance().send(commandFactory);
        collection = response.getCollection();
        if (collection != null) {
            for (City city : collection) {
                if (city.getAuthor().equals(session.getName())) {
                    clientColorMap.put(city.getAuthor(), Color.GREEN);
                    ownershipMap.put(city.getId(), city.getAuthor());
                } else {
                    if (!clientColorMap.containsKey(city.getAuthor())) {
                        Color randomColor = Color.color(Math.random(), Math.random(), Math.random());
                        clientColorMap.put(city.getAuthor(), randomColor);
                        ownershipMap.put(city.getId(), city.getAuthor());
                    }
                }
            }
        }
    }

    private void loadCollection() {
        // Retrieve data from DataHolder
        loadOwnershipMap();
        CommandFactory commandFactory = new CommandFactory("show", null);
        Response response = RequestHandler.getInstance().send(commandFactory);
        setCollection(response.getCollection());

    }
    public void setCollection(Set<City> collection) {
        this.collection = collection;

        if (collection != null) {
            table.setItems(FXCollections.observableArrayList(collection));
            if (!filterByText.getText().isEmpty()  && !comboBox.getSelectionModel().isEmpty()) {
                String selectedValue = comboBox.getSelectionModel().getSelectedItem();
                filterCities(selectedValue, filterByText.getText());
            }
//            always sort TableView by id (by default), if i want to bypass the TreeSet sorting
//            idColumn.setSortType(TableColumn.SortType.ASCENDING);
//            table.getSortOrder().add(idColumn);

            table.refresh();
            citiesCountLabel.setText(Integer.toString(collection.size()));
        }
    }
    public static Map<Integer, String> getOwnershipMap(){
        return ownershipMap;
    }

//    /**
//     * method for loading commands from the old Main class
//     */
//    private void loadCommands() {
//        boolean commandsNotLoaded = true;
//        int waitingCount = 4000;
//        while (commandsNotLoaded) {
//            try {
//                SingleCommandExecutor executor = new SingleCommandExecutor(CommandDescriptionHolder.getInstance().getCommands(), CommandMode.GUIMode);
//                commandsNotLoaded = false;
//
//                executor.executeCommand();
//            } catch (CommandsNotLoadedException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText(null);
//                alert.setContentText("We couldn't get commands from server last time, so now we'll try to do this again...");
//                alert.showAndWait();
//
//                AtomicInteger secondsRemained = new AtomicInteger(waitingCount / 1000 - 1);
//
//                javax.swing.Timer timer = new Timer(1000, (x) -> alert.setContentText("Re-attempt in " + secondsRemained.getAndDecrement() + " seconds. You may interrupt awaiting by hitting Enter."));
//                alert.showAndWait();
//
//                timer.start();
//
//                CountDownLatch latch = new CountDownLatch(1);
//
//                int finalWaitingCount = waitingCount;
//                Runnable wait = () -> {
//                    try {
//                        Thread.sleep(finalWaitingCount);
//                        latch.countDown();
//                    } catch (InterruptedException ex) {
//                        alert.setContentText("Continuing...");
//                        alert.showAndWait();
//                    }
//                };
//
//                Thread tWait = new Thread(wait);
//                //Thread tForceInt = new Thread(forceInterrupt);
//
//                tWait.start();
//                //tForceInt.start();
//
//                try {
//                    latch.await();
//                    timer.stop();
//                    tWait.interrupt();
//                } catch (InterruptedException ex) {
//                    alert.setContentText("Interrupted loading");
//                    alert.showAndWait();
//                }
//
//                waitingCount += 2000;
//            }
//        }
//    }

    @FXML
    protected void onCreateButtonClick() {
        CityManagementWindow createWindow = new CityManagementWindow("Creating City");
        createWindow.show();
    }

    @FXML
    protected void onEditButtonClick() {
        City selectedCity = table.getSelectionModel().getSelectedItem();
        if (selectedCity != null) {
            CityManagementWindow cityManagementWindow = new CityManagementWindow("Editing City");
            cityManagementWindow.show();
            cityManagementWindow.setCity(selectedCity);
        } else {
            AlertUtility.infoAlert("Please, select any city to edit it!)");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        City selectedCity = table.getSelectionModel().getSelectedItem();

        if (selectedCity != null) {
            table.getItems().remove(selectedCity);
            try {
               ArrayList<String> list = new ArrayList<>();
               list.add(selectedCity.getId().toString());
               CommandFactory commandFactory = new CommandFactory("remove_by_id", list);
               DataHolder.getInstance().setResponse(RequestHandler.getInstance().send(commandFactory));
            } catch (Exception e) {
                AlertUtility.errorAlert("Can't load commands from server. Please wait until the server will come back");
            }

            Platform.runLater(() -> {
                Response response = DataHolder.getInstance().getResponse();
                if (response != null) {
                    AlertUtility.infoAlert("Element deleted successful");
                } else {
                    AlertUtility.errorAlert("something wrong with execute_script command. It's suddenly silent -_-");
                }
            });
        } else {
            AlertUtility.infoAlert("Please, select any city to delete it!)");
        }
    }

    @FXML
    protected void onVisualizeButtonClick() {
        VisualizationWindow visualizationWindow = new VisualizationWindow(collection);
        visualizationWindow.show();
        visualizationWindow.loadColorMap(clientColorMap, ownershipMap);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> loadCollectionToVisualizationWindow(visualizationWindow)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    protected void onCommandsButtonClick() {}

    public void onWorldMapButtonClick(ActionEvent actionEvent) {
        WorldMapWindow visualizationWindow = new WorldMapWindow(collection);
        visualizationWindow.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> loadCollectionToWorldMapWindow(visualizationWindow)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void loadCollectionToWorldMapWindow(WorldMapWindow worldMapWindow) {
        worldMapWindow.loadCities(collection);
    }

    private void loadCollectionToVisualizationWindow(VisualizationWindow visualizationWindow) {
        visualizationWindow.loadCollection(collection);
    }

    public void onGeoIconClick(MouseEvent mouseEvent) {
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        currentBundle = ResourceBundle.getBundle("com.example.client.MessagesBundle", supportedLocales.get(currentLocaleIndex));
        updateUI();
    }

    public void onMusicIconClick(MouseEvent mouseEvent) {
        MusicWindow musicWindow = new MusicWindow();
        musicWindow.show();
    }


    public void onClearButtonClick(ActionEvent actionEvent) {
        try {
            CommandFactory commandFactory = new CommandFactory("clear", null);
            Response response = RequestHandler.getInstance().send(commandFactory);
        } catch (Exception e){
            AlertUtility.errorAlert("Can't load commands from server. Please wait until the server will come back");
        }
    }

    public void onExecuteScriptButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ScriptReader.setFile(selectedFile.getName());
            try {
                ScriptReader.execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void onExitButtonClick(ActionEvent actionEvent) {
        stage.close();
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.show();
    }

    public void onHelpButtonClick(ActionEvent actionEvent) {
        try {
            CommandFactory commandFactory = new CommandFactory("help", null);
            DataHolder.getInstance().setResponse(RequestHandler.getInstance().send(commandFactory));
        }catch (Exception e){
            AlertUtility.errorAlert("Can't load commands from server. Please wait until the server will come back");
        }
        Platform.runLater(() -> {
           Response response = DataHolder.getInstance().getResponse();
           if (response != null){
               AlertUtility.infoAlert(response.getInformation());
           }else {
               AlertUtility.errorAlert("idk why clearing the collection isn't done, maybe because there is no wage for that lab8");
           }
        });
    }


    private void updateUI() {
        // TableView
        idColumn.setText(currentBundle.getString("id"));
        nameColumn.setText(currentBundle.getString("name"));
        coordXColumn.setText(currentBundle.getString("coordX"));
        coordYColumn.setText(currentBundle.getString("coordY"));
        creationColumn.setText(currentBundle.getString("creation"));
        areaColumn.setText(currentBundle.getString("area"));
        populationColumn.setText(currentBundle.getString("population"));
        metersAboveSeaLevelColumn.setText(currentBundle.getString("metersAboveSeaLevel"));
        climateColumn.setText(currentBundle.getString("climate"));
        governmentColumn.setText(currentBundle.getString("government"));
        standardsColumn.setText(currentBundle.getString("standards"));
        governorColumn.setText(currentBundle.getString("governor"));
        // buttons & labels
        citiesLabel.setText(currentBundle.getString("citiesCountLabel"));
        filterByLabel.setText(currentBundle.getString("filterByLabel"));
        ObservableList<String> localizedItems = FXCollections.observableArrayList(
                currentBundle.getString("id"),
                currentBundle.getString("name"),
                currentBundle.getString("coordX"),
                currentBundle.getString("coordY"),
                currentBundle.getString("creation"),
                currentBundle.getString("area"),
                currentBundle.getString("population"),
                currentBundle.getString("metersAboveSeaLevel"),
                currentBundle.getString("climate"),
                currentBundle.getString("government"),
                currentBundle.getString("standards"),
                currentBundle.getString("governor"),
                currentBundle.getString("author")
        );
        comboBox.getItems().setAll(localizedItems);
        commandsButton.setText(currentBundle.getString("commandsButton"));
        deleteButton.setText(currentBundle.getString("deleteButton"));
        editButton.setText(currentBundle.getString("editButton"));
        createButton.setText(currentBundle.getString("createButton"));
        visualizeButton.setText(currentBundle.getString("visualizeButton"));
        worldMapButton.setText(currentBundle.getString("worldMapButton"));
        clearButton.setText(currentBundle.getString("clearButton"));
        executeScriptButton.setText(currentBundle.getString("executeScriptButton"));
        exitButton.setText(currentBundle.getString("exitButton"));
        helpButton.setText(currentBundle.getString("helpButton"));
    }

    private String getDate(Date date) {
        if (date == null) return "null";
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL, currentBundle.getLocale());
        return formatter.format(date);
    }
    private void filterCities(String property, String value) {
        Stream<City> cityStream = collection.stream();
        switch (property) {
            case "id":
                int id = Integer.parseInt(value);
                cityStream = cityStream.filter(city -> city.getId() == id);

                break;
            case "creationDate":
                DateTimeFormatter formatter;
                switch (currentBundle.getLocale().toString()) {
                    case "en_NZ":
                        formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", currentBundle.getLocale()); // for English (NZ) locale
                        break;
                    case "da":
                        formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy.", currentBundle.getLocale()); // for Danish locale
                        break;
                    case "ru":
                        formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'г.'", currentBundle.getLocale()); // for Russian locale
                        break;
                    case "cs":
                        formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy", currentBundle.getLocale()); // for Czech locale
                        break;
                    default:
                        formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US); // default to US locale
                        break;
                }
                LocalDate date = LocalDate.parse(value, formatter);
                cityStream = cityStream.filter(city -> {
                    String cityCreationDateString = getDate(city.getCreationDate());
                    LocalDate cityCreationDate = LocalDate.parse(cityCreationDateString, formatter);
                    return cityCreationDate.equals(date);
                });
                break;
            case "population":
                long population = Long.parseLong(value);
                cityStream = cityStream.filter(city -> city.getPopulation() == population);
                break;
            case "area":
                Float area = Float.parseFloat(value);
                cityStream = cityStream.filter(city -> city.getArea() == area);
                break;
            case "metersAboveSeaLevel":
                Double metersAboveSeaLevel = Double.parseDouble(value);
                cityStream = cityStream.filter(city -> city.getMetersAboveSeaLevel().equals(metersAboveSeaLevel));
                break;
            case "name":
                cityStream = cityStream.filter(city -> city.getName().equals(value));
                break;
            case "climate":
                Climate climate = Climate.valueOf(value);
                cityStream = cityStream.filter(city -> city.getClimate().equals(climate));
                break;
            case "government":
                Government government = Government.valueOf(value);
                cityStream = cityStream.filter(city -> city.getGovernment().equals(government));
                break;
            case "standards":
                StandardOfLiving standards = StandardOfLiving.valueOf(value);
                cityStream = cityStream.filter(city -> city.getStandardOfLiving().equals(standards));
                break;
            case "governor":
                switch (currentBundle.getLocale().toString()) {
                    case "en_NZ":
                        formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", currentBundle.getLocale()); // for English (NZ) locale
                        break;
                    case "da":
                        formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy.", currentBundle.getLocale()); // for Danish locale
                        break;
                    case "ru":
                        formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'г.'", currentBundle.getLocale()); // for Russian locale
                        break;
                    case "cs":
                        formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy", currentBundle.getLocale()); // for Czech locale
                        break;
                    default:
                        formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US); // default to US locale
                        break;
                }
                date = LocalDate.parse(value, formatter);
                cityStream = cityStream.filter(city -> {
                    String cityCreationDateString = getDate(city.getGovernor().getBirthday());
                    LocalDate cityCreationDate = LocalDate.parse(cityCreationDateString, formatter);
                    return cityCreationDate.equals(date);
                });
                break;
            case "coord X":
                int coordX = Integer.parseInt(value);
                cityStream = cityStream.filter(city -> city.getCoordinates().getX() == coordX);
                break;
            case "coord Y":
                double coordY = Double.parseDouble(value);
                cityStream = cityStream.filter(city -> city.getCoordinates().getY() == coordY);
                break;
            default:
                break;
        }
        table.setItems(FXCollections.observableArrayList(cityStream.collect(Collectors.toList())));
    }
}
