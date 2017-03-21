package game.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class PCController {

    PlayWithUser user;
    Image OPic;
    Image XPic;

    GameBoard board;
    DBConnection con;
    boolean flag;
    public int X;
    public int O;
    private Button cells[];
    UserNameScreenController u;
    ArrayList<String> ar;
    ArrayList<Character> places;
    String anotherPlayerName;
    @FXML
    private Button loadGamePc;
    @FXML
    private Button saveGamePc;
    @FXML
    private Label playerName;
    @FXML
    private Label player2Name;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;

    @FXML
    private Button b4;

    @FXML
    private Button b1;

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
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("ChooseModeScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(" Play Tic Tac Toe On Line ");
        stage.show();

    }

    @FXML
    void saveAction(ActionEvent event) throws IOException {
        String gamePattern = con.getMatchSeq(con.getUserId(playerName.getText()), anotherPlayerName);
        for (String s : ar) {
            gamePattern += s;
            System.out.println(gamePattern);
        }
        int uId = con.getUserId(playerName.getText());
        con.updateSeq(gamePattern, con.getUnCompletedMatchId(uId, anotherPlayerName));

    }

    @FXML
    void loadSeq(ActionEvent event) throws IOException {
        String s = con.getMatchSeq(con.getUserId(playerName.getText()), player2Name.getText());
        for (int i = 0; i < s.length(); i++) {
            places.add(s.charAt(i));
        }

        int counter = 0;
        for (char p : places) {
            if (counter % 2 == 0) {
                cells[Character.getNumericValue(p)].setGraphic(new ImageView(XPic));
                cells[Character.getNumericValue(p)].setUserData(true);      
                flag=true;
            } else {
                cells[Character.getNumericValue(p)].setGraphic(new ImageView(OPic));
                cells[Character.getNumericValue(p)].setUserData(true);
            }
            System.out.println(Character.getNumericValue(p));
            counter++;

        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        user = new PlayWithUser();
        board = new GameBoard();
        con = new DBConnection();
        anotherPlayerName = JOptionPane.showInputDialog("Please enter the second player name");
        playerName.setText(UserNameScreenController.getUserName());
        player2Name.setText(anotherPlayerName);

        if (!con.haveAGame(con.getUserId(playerName.getText()), anotherPlayerName)) {
            con.isGameDataInserted("", 0, con.getUserId(playerName.getText()), 0, anotherPlayerName);
        }

        ar = new ArrayList<String>();
        places = new ArrayList<Character>();

        X = 1;
        O = 2;
        ComputerPlayer ai = new ComputerPlayer();
        cells = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9};
        for (Button cell : cells) {
            cell.setUserData(false);
        }
        OPic = new Image(getClass().getResourceAsStream("o.png"));
        XPic = new Image(getClass().getResourceAsStream("x.png"));

        for (Button c : cells) {

            c.setOnMouseClicked(event -> {
                if (((boolean) c.getUserData()) == false && flag == false) {
                    c.setGraphic(new ImageView(XPic));
                    int index = -1;
                    for (int i = 0; i < cells.length; ++i) {
                        if (c == cells[i]) {
                            index = i;
                            System.out.println(i);

                            ar.add(Integer.toString(i));
                            flag = true;
                            c.setUserData(true);
                        }
                    }

                    user.placeAMove(new Point(index / 3, index % 3), X);

                } else if (((boolean) c.getUserData()) == false && flag == true) {
                    c.setGraphic(new ImageView(OPic));
                    int index = -1;
                    for (int i = 0; i < cells.length; ++i) {
                        if (c == cells[i]) {
                            index = i;
                            System.out.println(i);
                            ar.add(Integer.toString(i));
                            flag = false;
                            c.setUserData(true);

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
                        board.clearBoard();
                        Parent parent;
                        try {
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                            parent = FXMLLoader.load(getClass().getResource("PC.fxml"));
                            Stage window = new Stage();
                            Scene scene = new Scene(parent);
                            window.setScene(scene);
                            window.setTitle("");
                            window.show();
                            stage.hide();
                        } catch (IOException ex) {
                            Logger.getLogger(PCController.class.getName()).log(Level.SEVERE, null, ex);
                        }
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

}
