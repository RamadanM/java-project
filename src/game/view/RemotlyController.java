package game.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class RemotlyController {

    Data da;
    String mySym;
    public String players;
    ObservableList<String> data;
    Vector<String> myVector;
    DBConnection dbcon = new DBConnection();
    Thread th;
    public boolean flag = false;
    PlayWithUser user;
    public int X;
    public int O;
    Image OPic;
    Image XPic;
    private Button cells[];
    @FXML
    private Button backId;
    @FXML
    private Button c1;
    @FXML
    private Button c2;
    @FXML
    private Button c3;
    @FXML
    private Button c4;
    @FXML
    private Button c5;
    @FXML
    private Button c6;
    @FXML
    private Button saveId;
    @FXML
    private Button c7;
    @FXML
    private Button loadId;
    @FXML
    private Button c8;
    @FXML
    private Button c9;
    @FXML
    private ListView listView;
    @FXML
    private ToggleButton onOffId;
    @FXML
    private Button homeId;

    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    PrintStream ps;
    int currentLoc;
    String currentSym;
    String inputValue;
    GameBoard board;

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
    void toogleButton(ActionEvent event) throws IOException {
        Data data = new Data();
        // send the server info to set the player status to offline in db (T: online, F: offline)
        data.user = inputValue;
        if (onOffId.isSelected()) {
            ps.println("s: :" + data.user + ": :" + "T");
            System.out.println("on " + "s: :" + data.user + ": :" + "T");

        } else {
            ps.println("s: :" + data.user + ": :" + "F");
            System.out.println("off " + "s: :" + data.user + ": :" + "F");
        }
    }

    @FXML
    void showChooseModeScreen(ActionEvent event) throws IOException {

        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("ChooseModeScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Play With Pc Tic Tac Toe ");
        stage.show();

    }
    
    @FXML
   boolean isExist(String m){
        for(String is : myVector )
                if(is.equals(m))
                    return true ;
        return false;
    
        }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        da = new Data();
        inputValue = UserNameScreenController.getUserName();
        user = new PlayWithUser();
        OPic = new Image(getClass().getResourceAsStream("o.png"));
        XPic = new Image(getClass().getResourceAsStream("x.png"));
        PlayWithUser user = new PlayWithUser();
        board = new GameBoard();

        myVector = dbcon.selectOnlinePlayers();
        myVector.remove(UserNameScreenController.getUserName());
        //database
        data = FXCollections.observableArrayList(myVector);
        listView.setItems(data);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                ps.println("p: :" + UserNameScreenController.getUserName() + ": : :" + listView.getSelectionModel().getSelectedItem());
                System.out.println("p: :" + UserNameScreenController.getUserName() + ": : :" + listView.getSelectionModel().getSelectedItem());
                System.out.println(UserNameScreenController.getUserName());

            }
        });
        cells = new Button[]{c1, c2, c3, c4, c5, c6, c7, c8, c9};

        try {
            //inputValue = JOptionPane.showInputDialog("Please input a value");
            cs = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(cs.getInputStream());
            dos = new DataOutputStream(cs.getOutputStream());
            ps = new PrintStream(cs.getOutputStream());
            // hna
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            String msg = dis.readLine();
                            System.out.println("msg" + msg);
                            System.out.println("inputVlue" + inputValue);
                            if (msg.equals(" ")) {
                        //faddy el list
                                listView.setItems(null);
                                

                            //listView.setText(null);
                                //taUserList.append("\n"+ msg +"\n");	
                            } else if (msg.startsWith("request") && msg.endsWith(inputValue)) {
                                System.out.println("msg" + msg);
                                String[] parts = msg.split(":");
                                System.out.println("req sent");
                                int response = JOptionPane.showConfirmDialog(null, "Hello, " + inputValue + ". Do you Want to play with " + parts[1], "Tic Tac Toe is Fun Fun Fun", JOptionPane.YES_NO_OPTION);
                                switch (response) {
                                    case JOptionPane.YES_OPTION:
                                        System.out.println("Game Started");
                                        ps.println("acc:" + inputValue + ":to:" + parts[1]);
                                        // start auatomaic sending between players 1 and 2 till it is cut
                                        break;
                                    case JOptionPane.NO_OPTION:
                                        System.out.println("send sorry message, try play with another online player to sender ");
                                        ps.println("rej:" + inputValue + ":to:" + parts[1]);
                                        // parts[5] rejected parts[2]
                                        break;
                                    default:
                                        //  System.out.println("no response");
                                        break;
                                }
                            } else if (msg.startsWith("rej") && msg.endsWith(inputValue)) {
                                String[] parts = msg.split(":");
                                //"rej:" + user + ":to:"+ inputvalue)
                                JOptionPane.showMessageDialog(null, "sorry " + inputValue + " your request to " + parts[0] + "has been rejected");
                            } else if (msg.startsWith("acc")) {
                                String[] parts = msg.split(":");
                            //"rej:" + user + ":to:"+ inputvalue)
                                //handlers
                                players = parts[1] + ':' + parts[3] + ':' + parts[5] + ':' + parts[7];
                                System.out.println("players" + players);
                                if (inputValue.equals(parts[3])) {
                                    mySym = "X";
                                } else {
                                    mySym = "O";
                                }

                            } //   
                            else if (msg.startsWith("game")) {

                                String[] parts = msg.split(":");
                                currentLoc = Integer.parseInt(parts[3]);
                                currentSym = parts[4];
                                System.out.println(currentLoc + currentSym);
                                javafx.scene.image.Image OPic = new javafx.scene.image.Image(getClass().getResourceAsStream("o.png"));
                                new Thread(new Task<Void>() {
                                    @Override
                                    protected Void call() throws Exception {
//                                        while (true) {
//                                            dis.readLine();
                                        System.out.println("task" + currentLoc);
                                        // code to check what to do

                                        Platform.runLater(() -> {
//                                                @Override
//                                                public void run(){
                                            // while(true)
                                            // {
                                            System.out.println(msg);
                                            currentLoc = Integer.parseInt(parts[3]);
                                            currentSym = parts[4];
                                            javafx.scene.image.Image XPic = new javafx.scene.image.Image(getClass().getResourceAsStream("x.png"));
                                            javafx.scene.image.Image OPic = new javafx.scene.image.Image(getClass().getResourceAsStream("o.png"));
                                            if (currentSym.equals("O")) {
                                                cells[currentLoc].setGraphic(new ImageView(OPic));
                                            } else {
                                                cells[currentLoc].setGraphic(new ImageView(XPic));
                                            }
                                            System.out.println("dis" + dis);
                                            // }
                                        });

//                                        }
                                        return null;
                                    }
                                }).start();
                                
                                //                               javafx.scene.image.Image OPic =new javafx.scene.image.Image(getClass().getResourceAsStream("o.png")); ;
//                                cells[currentLoc].setGraphic(new ImageView(OPic));
                            }  else if(! isExist(msg)){
                                
                                myVector.add(msg);
                            }
                            else {
                                // amma el server yo2a3 

                                JOptionPane.showMessageDialog(null, "server is down", "error", JOptionPane.ERROR_MESSAGE);
                                System.exit(1);
                                break;
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "server is down", "error", JOptionPane.ERROR_MESSAGE);
                            System.exit(1);
                        }
                    }
                }
            }).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
  

//Game Logic
        int next = user.returnNextMove();
        if (next != -1) //If the game isn't finished yet!
        {
            for (Button cell : cells) {
                //  symbol = 'X';
                cell.setOnMouseClicked(event -> {
                    //  if (((boolean) cell.getUserData()) == false  && flag==false) 
                    // if (((boolean) cell.getUserData()) == false && mySym == "X") {
                    if (true) {
                        cell.setGraphic(new ImageView(XPic));
                        int index = -1;
                        for (int i = 0; i < cells.length; ++i) {
                            //cell.setUserData(true);
                            if (cell == cells[i]) {
                                index = i;
                                System.out.println(i);
                                flag = true;
                                //   cell.setUserData(true);

                                // x - flag false
                                ps.println("game:" + players + ":" + i + ":" + mySym);
                            }

                        }
                        user.placeAMove(new Point(index / 3, index % 3), X);

                    } //else if (((boolean) cell.getUserData()) == false  && flag==true) {
                    else if (((boolean) cell.getUserData()) == false && mySym == "O") {
                        cell.setGraphic(new ImageView(OPic));
                        int index = -1;
                        for (int i = 0; i < cells.length; ++i) {
                            if (cell == cells[i]) {
                                index = i;
                                System.out.println(i);
                                flag = false;
                                cell.setUserData(true);
                                ps.println("game:" + players + ":" + i + ":" + mySym);
                            }
                        }
                        user.placeAMove(new Point(index / 3, index % 3), O);

                    }

                    if (user.isGameOver()) {
                        Stage stage = new Stage();
                        GridPane g = new GridPane();
                        g.setPadding(new Insets(20, 20, 20, 20));
                        g.setVgap(20);
                        g.setHgap(20);

                        Label label1 = new Label();

                        if (user.hasWon(X)) {
                            label1.setText("X won");
                            stage.setTitle("x Wone");
                        } else if (user.hasWon(O)) {
                            label1.setText("O Won!");
                            stage.setTitle("O Won!");
                        } else {
                            label1.setText("It is Tie");
                            stage.setTitle("It's a draw!");
                        }

                        g.add(label1, 0, 0, 2, 1);
                        Button onceMore = new Button("play again!");
                        Button exit = new Button("Exit");
                        g.add(onceMore, 1, 1, 1, 1);
                        g.add(exit, 2, 1, 1, 1);

                        onceMore.setOnMouseClicked(q -> {
                            stage.close();
                            stage.close();
//                            board.clearBoard();
                            //start(new Stage());
                        });

                        exit.setOnMouseClicked(q -> {
                            System.exit(0);
                        });

                        Scene scene = new Scene(g);
                        stage.setScene(scene);
                        stage.setOnCloseRequest(q -> {
                            stage.close();
                        });
                        stage.show();
                    }
                });
            }
        }

        //top panel (chat area and online users list
    }
}
