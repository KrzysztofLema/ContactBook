package pl.Krzysiek.testProject.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pl.Krzysiek.testProject.models.UserSession;
import pl.Krzysiek.testProject.models.dao.ContactDao;
import pl.Krzysiek.testProject.models.dao.impl.ContactDaoImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {


    @FXML
    JFXListView <String> listViewNumberList;

    @FXML
    Label labelName, labelNumber;

    private ObservableList contactItems;

    private UserSession userSession = UserSession.getInstance();
    ContactDao contactDao = new ContactDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadContacts();
        listViewNumberList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelName.setText(newValue);
                labelNumber.setText(contactDao.getNumber(newValue));
            }
        });
    }

    private void loadContacts() {
        contactItems = FXCollections.observableArrayList(contactDao.getAllContactNames(userSession.getUserName()));
        listViewNumberList.setItems(contactItems);
    }
}
