


package game.view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChooseModeController {

    @FXML
    private Button bachHomeId;
    
    @FXML
    private Button pcId;
    
    @FXML
    private Button samePcId;
    
    @FXML
    private Button chooseId;
    
    @FXML
    void showHomeScreen(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Home ");
        stage.show();

    }
    
        @FXML
    void showPcScreen(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("PC.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Play With Pc Tic Tac Toe ");
        stage.show();

    }
    
            @FXML
    void showPcHumanScreen(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("PCHuman.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Play With Pc Tic Tac Toe ");
        stage.show();

    }
    

    @FXML
    void showRemotlyScreen(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Remotly.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Play Tic Tac Toe On Line");
        stage.show();

    }
}
