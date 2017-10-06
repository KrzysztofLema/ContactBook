package pl.Krzysiek.testProject.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pl.Krzysiek.testProject.models.UserSession;
import pl.Krzysiek.testProject.models.Utils;
import pl.Krzysiek.testProject.models.dao.UserDao;
import pl.Krzysiek.testProject.models.dao.impl.UserDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    JFXTextField textFieldLogin, textFieldLoginRegistration;
    @FXML
    JFXPasswordField passwordFieldLoginPass, passwordFieldRegistrationPass, passwordFieldRegistrationPassRepeat;
    @FXML
    JFXButton buttonLogin, buttonRegistration;

    private UserSession userSession = UserSession.getInstance();
    private UserDao userDao = new UserDaoImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonLogin.setOnMouseClicked(s -> tryLogin());
        buttonLogin.setOnKeyPressed(event -> tryLogin());
        buttonRegistration.setOnMouseClicked(s -> tryRegistration());
    }

    private boolean checkLoginData() {
        String login = textFieldLogin.getText();
        String password = passwordFieldLoginPass.getText();
        if (login.isEmpty() || password.isEmpty()) {
            Utils.createSimpleDialog("Błąd logowania", "", "Pola nie mogą być puste");
            return false;
        }
        if (login.length() <= 5 || password.length() <= 5) {
            Utils.createSimpleDialog("Błąd logowania", "", "Pola wymagają conajmniej 5 liter");
            return false;
        }
        return true;
    }

    private void tryLogin() {

        String login = textFieldLogin.getText();
        String password = passwordFieldLoginPass.getText();
        if (!checkLoginData()) {
            return;
        }
        if (userDao.login(login, password)) {
            userSession.setUserName(login);
            userSession.setLogin(true);
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainView.fxml"));
                Stage stageRoot = (Stage) buttonLogin.getScene().getWindow();
                stageRoot.setScene(new Scene(root, 350, 270));

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Utils.createSimpleDialog("Logowanie", "", "Logowanie niepoprawne");
        }

    }

    private void tryRegistration() {
        String name = textFieldLoginRegistration.getText();
        String password = passwordFieldRegistrationPass.getText();

        if (!checkRegisterData()) {
            return;
        }
        if (userDao.register(name, password)) {
            Utils.createSimpleDialog("Rejestracja", "", "Zarejestrowałeś się poprawnie");
        } else {
            Utils.createSimpleDialog("Rejestracja", "", "Login jest już zajęty");
        }
    }

    private boolean checkRegisterData() {
        String login = textFieldLoginRegistration.getText();
        String password = passwordFieldRegistrationPass.getText();
        String passwordRepeat = passwordFieldRegistrationPassRepeat.getText();

        if (!password.equals(passwordRepeat)) {
            Utils.createSimpleDialog("Rejestracja", "", "Hasła muszą być takie same!");
            return false;
        }
        if (login.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
            Utils.createSimpleDialog("Błąd logowania", "", "Pola nie mogą być puste");
            return false;
        }
        if (password.length() <= 5) {
            Utils.createSimpleDialog("Błąd logowania", "", "Hasło nie moze być krótsze niz 5 znaków");
        }
        return true;
    }

}
