package game.view;

import java.util.ArrayList;
import java.util.List;


 public abstract class Player {
     
     
  List<Point> availablePoints;
  int[][] board = new int[3][3];

   
  
  
  public boolean isGameOver() {
      //Game is over is someone has won, or board is full (draw)
      return (hasWon(1) || hasWon(2)  || getAvailableStates().isEmpty());
  }

  
  
  
  public boolean hasWon(int symbole) {
      if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == symbole) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == symbole)) {
          //System.out.println("X Diagonal Win");
          return true;
      }
      for (int i = 0; i < 3; i++) {
          if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == symbole)
                  || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == symbole))) {
              // System.out.println("X Row or Column win");
              return true;
          }
      }
      return false;
  }

  
 
  public List<Point> getAvailableStates() {
      availablePoints = new ArrayList<>();

      for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
              if (board[i][j] == 0) {
                  availablePoints.add(new Point(i, j));
              }
          }
      }
      return availablePoints;
  }

  
  
  
  
  public void placeAMove(Point point, int player) {
      board[point.ROW][point.COL] = player;   
  }

  
  
  

  
 
  


 }
