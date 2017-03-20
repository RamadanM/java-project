package game.view;

    class Point {

    int ROW, COL;

    public Point(int row, int col) {
        this.ROW = row;
        this.COL = col;
    }
}
public class GameBoard {



    Player ai = new Player() {};

    public void clearBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ai.board[i][j] = 0;
            }
        }
    }

    
    
    
    public void replayLastGame() {
        clearBoard();
    }
}
