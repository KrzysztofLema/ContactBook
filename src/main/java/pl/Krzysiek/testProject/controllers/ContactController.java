package pl.Krzysiek.testProject.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.Krzysiek.testProject.models.UserSession;
import pl.Krzysiek.testProject.models.dao.ContactDao;
import pl.Krzysiek.testProject.models.dao.impl.ContactDaoImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {


    @FXML
    JFXListView<String> listViewNumberList;

    @FXML
    JFXTextField labelName, labelNumber;
    @FXML
    Button buttonAddContact, buttonLogOut, buttonRemoveContact;
    @FXML
    TextField textFieldName, textFieldNumber;

    private ObservableList contactItems;

    private UserSession userSession = UserSession.getInstance();
    ContactDao contactDao = new ContactDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelName.setEditable(false);
        labelNumber.setEditable(false);

        loadContacts();
        listViewNumberList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            labelName.setText(newValue);
            labelNumber.setText(contactDao.getNumber(newValue));
        });

        buttonLogOut.setOnMouseClicked(event -> logOut());

        buttonAddContact.setOnMouseClicked(event -> {
            addContact();
            removeContact();
        });

        updateActions();
        removeContact();

    }

    private void removeContact() {
        buttonRemoveContact.setOnMouseClicked(event -> {
            contactDao.removeContact(listViewNumberList.getSelectionModel().getSelectedItem());
            loadContacts();
        });
    }

    private void logOut() {
        userSession.setId(0);
        userSession.setLogin(false);
        userSession.setUserName(null);

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("loginView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) buttonLogOut.getScene().getWindow();
        stage.setScene(new Scene(root, 350, 270));
    }

    private void loadContacts() {
        contactItems = FXCollections.observableArrayList(contactDao.getAllContactNames(userSession.getUserName()));
        listViewNumberList.setItems(contactItems);
    }

    private void addContact() {

        contactDao.addContact(textFieldName.getText(), textFieldNumber.getText());
        loadContacts();
    }

    private void updateActions() {
        labelName.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                labelName.setEditable(true);
            }
        });
        labelName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                contactDao.editContactName(labelName.getText(), labelNumber.getText(), listViewNumberList.getSelectionModel().getSelectedItem());
                loadContacts();
            }
        });
        labelNumber.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                labelNumber.setEditable(true);
            }
        });
        labelNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                contactDao.editContactName(listViewNumberList.getSelectionModel().getSelectedItem(), labelNumber.getText(), listViewNumberList.getSelectionModel().getSelectedItem());
                loadContacts();
            }
        });
    }
}
