package game.view;

import java.io.IOException;
import java.net.InetAddress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class UserNameScreenController {

    DBConnection con;
   
    static String userName ;
    @FXML
    private Button backId;

    @FXML
    private TextField nameId;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserNameScreenController.userName = userName;
    }

    
    @FXML
    void showHomeScreen(ActionEvent event) throws IOException {
        
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Tic Tac Toe ");
        stage.show();
        
    }

    @FXML
    void showChooseModeScreen(ActionEvent event) throws IOException {
        InetAddress myIP=InetAddress.getLocalHost();
        if(! con.isIpExist(myIP.getHostAddress().toString(), nameId.getText() )){
        setUserName(nameId.getText());
        con.isInserted(nameId.getText(),1,0,0,myIP.getHostAddress().toString());
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("ChooseModeScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Choose Your Mode TicTac Toe ");
        stage.show();
        
        }
        else{
            
            Alert alert = new Alert(Alert.AlertType.WARNING) ;
            alert.setContentText("May be the name is used or You are in another machine");
            alert.showAndWait();                        
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        con = new DBConnection();
        

    }

}
