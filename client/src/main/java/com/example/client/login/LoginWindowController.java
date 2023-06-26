package com.example.client.login;

import com.example.client.collections.CollectionsWindow;
import com.example.client.utilities.AlertUtility;
import com.example.client.utilities.RequestHandler;
import com.example.client.utilities.UTF8Control;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.org.example.utility.Response;
import main.org.example.utility.Session;
import main.org.example.utility.TypeOfAnswer;
import main.org.example.utility.TypeOfSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginWindowController {
    private Response sessionStatus;

    private ResourceBundle currentBundle;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label signUpLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label accountLabel;
    private final List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("da"),
            new Locale("cs")
    );
    private int currentLocaleIndex = 0;

    @FXML
    public void initialize() {
        currentBundle = ResourceBundle.getBundle("com.example.client.MessagesBundle", supportedLocales.get(currentLocaleIndex));
        updateUI();
    }

    private void updateUI() {
        accountLabel.setText(currentBundle.getString("accountLabel"));
        welcomeLabel.setText(currentBundle.getString("welcomeLabel"));
        detailsLabel.setText(currentBundle.getString("detailsLabel"));
        signInButton.setText(currentBundle.getString("signInButton"));
        signUpLabel.setText(currentBundle.getString("signUpLabel"));
        usernameLabel.setText(currentBundle.getString("usernameLabel"));
        passwordLabel.setText(currentBundle.getString("passwordLabel"));
    }

    @FXML
    protected void onGeoIconClick() {
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        currentBundle = ResourceBundle.getBundle("com.example.client.MessagesBundle", supportedLocales.get(currentLocaleIndex));
        updateUI();
    }

    @FXML
    protected void onSignInButtonClick() {
        try {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getText().trim().toCharArray();
            Session session = new Session(username, new String(password), TypeOfSession.Login);
            try {
                sessionStatus = AuthorizeSession(session);
            } catch (IOException e) {
                AlertUtility.errorAlert("Client can't get authorization on server, try again!");
                return;
            }
            if (sessionStatus.getStatus() == TypeOfAnswer.SUCCESSFUL) {
                Stage stage = (Stage) signInButton.getScene().getWindow();
                stage.close();

                CollectionsWindow collectionsWindow = new CollectionsWindow();
                collectionsWindow.show();
            }
            else {
                AlertUtility.errorAlert("There is no user with this name, or password is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSignUpLabelClick() {
        try {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getText().trim().toCharArray();
            Session session = new Session(username, new String(password), TypeOfSession.Register);
            try {
                sessionStatus = AuthorizeSession(session);
            } catch (IOException e) {
                AlertUtility.errorAlert("Client can't get authorization on server, try again!");
                return;
            }
            if (sessionStatus.getStatus() == TypeOfAnswer.SUCCESSFUL) {
                Stage stage = (Stage) signInButton.getScene().getWindow();
                stage.close();

                CollectionsWindow collectionsWindow = new CollectionsWindow();
                collectionsWindow.show();
            }
            else {
                AlertUtility.errorAlert("There is no user with this name, or password is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Response AuthorizeSession(Session session) throws IOException {
        if (session.getTypeOfSession().equals(TypeOfSession.Register)) {
            return RequestHandler.getInstance().register(session);
        } else {
            return RequestHandler.getInstance().login(session);
        }
    }
}