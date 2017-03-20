package game.view;
import java.io.IOException;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PCHumanController {

    boolean flag;
    public int X;
    public int O;
    private Button cells[];

    @FXML
    private Button saveId;
    @FXML
    private Button loadId;
    
    @FXML
    private Button b1;

    @FXML
    private Button b3;

    @FXML
    private Button b2;

    @FXML
    private Button b5;

    @FXML
    private Button b4;

    @FXML
    private Button b7;

    @FXML
    private Button b6;

    @FXML
    private Button b9;

    @FXML
    private Button b8;

    @FXML
    private ImageView imageViewHuman;

    @FXML
    private Button homeHumanId;

    @FXML
    private Button exitHumanId;

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
    void exit(ActionEvent event) throws IOException {
        System.exit(1);
    }

    @FXML
    void save(ActionEvent event) throws IOException {
    
    }
    
    @FXML
    void load(ActionEvent event) throws IOException {
    
    }
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        X = 1;
        O = 2;
        ComputerPlayer ai = new ComputerPlayer();
        Image OPic;
        Image XPic;
        cells = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9};
        for (Button cell : cells) {
            // cell.setMinSize(75, 100);
            cell.setUserData(false);
        }

        OPic = new Image(getClass().getResourceAsStream("o.png"));
        XPic = new Image(getClass().getResourceAsStream("x.png"));

        GameBoard board = new GameBoard();
        for (Button cell : cells) {

            cell.setOnMouseClicked(event -> {
                if (((boolean) cell.getUserData()) == false) {
                    cell.setGraphic(new ImageView(XPic));
                    System.out.println("Button No " + cell.getId());
                    int index = -1;
                    for (int i = 0; i < cells.length; ++i) {
                        if (cell == cells[i]) {
                            index = i;
                        }
                    }
                    ai.placeAMove(new Point(index / 3, index % 3), X);

                    boolean mark = true;
                    int next = ai.returnNextMove();
                    if (next != -1) //If the game isn't finished yet!
                    {
                        int indexCell = next;

                        cells[indexCell].setGraphic(new ImageView(OPic));
                        cells[indexCell].setUserData(mark); //Used!
                        ai.placeAMove(new Point(indexCell / 3, indexCell % 3), O);
                        cell.setUserData(mark);
                    }
                    if (ai.isGameOver()) {
                        Stage stage = new Stage();
                        GridPane g = new GridPane();
                        g.setPadding(new Insets(20, 20, 20, 20));
//                        g.setVgap(20);
//                        g.setHgap(20);

                        Label label1 = new Label();

                        if (ai.hasWon(X)) {
                            label1.setText("X won!");
                            stage.setTitle("X won!");
                        } else if (ai.hasWon(O)) {
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
                                parent = FXMLLoader.load(getClass().getResource("PCHuman.fxml"));
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
                }
            });
        }

    }

}
