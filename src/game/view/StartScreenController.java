package game.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class StartScreenController {

    @FXML
    private Button newId;

    @FXML
    private Button exitId;

    @FXML
    private Button aboutId;

    @FXML
    void about(ActionEvent event) throws IOException {
        String text = "Developers" + "\n" + "Muhammed Ehab(C-P) " + "\n" + "Ramadan Muhammed(C-P) " + "\n"
                + "Mariam Osama(IOT) " + "\n" + "Reem Essam(IOT) ";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tic Tac Toe About");
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    void clickme(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("UserNameScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Enter Yor Name ");
        stage.show();

    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tic Tac To Confirm....");
        alert.setContentText("Are you sure that you want to exit ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Exiting...");
                System.exit(1);
            }
        });

    }

}
