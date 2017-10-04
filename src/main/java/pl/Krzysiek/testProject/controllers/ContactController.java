package pl.Krzysiek.testProject.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.Krzysiek.testProject.models.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {


    @FXML
    JFXListView listViewNumberList;

    @FXML
    Label labelName, labelNumber;

    private UserSession userSession = UserSession.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
