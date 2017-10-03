package pl.Krzysiek.testProject.models;

import javafx.scene.control.Alert;

public class Utils {
    public static void createSimpleDialog(String name, String header,String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(name);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();    }
}
