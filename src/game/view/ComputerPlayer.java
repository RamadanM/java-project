/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mosama
 */
public class ComputerPlayer extends Player
{ 





  Point computersMove;

  public int minimax(int level, int turn) {
      if (hasWon(turn)) {
          return 1;
      }
      if (hasWon(turn)) {
          return -1;
      }

      List<Point> pointsAvailable = getAvailableStates();

      if (pointsAvailable.isEmpty()) {
          return 0;
      }

      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;

      for (int i = 0; i < pointsAvailable.size(); i++) {
          Point point = pointsAvailable.get(i);

          if (turn == 1) {
              placeAMove(point, 1);
              int currentScore = minimax(level + 1, 2);
              max = Math.max(currentScore, max);
              if (currentScore >= 0) {
                  if (level == 0) {
                      computersMove = point;
                  }
              }
              if (currentScore == 1) {
                  board[point.ROW][point.COL] = 0;
                  break;
              }
              if (i == pointsAvailable.size() - 1 && max < 0) {
                  if (level == 0) {
                      computersMove = point;
                  }
              }
          } else if (turn == 2) {
              placeAMove(point, 2);
              int currentScore = minimax(level + 1, 1);
              min = Math.min(currentScore, min);

              if (min == -1) {
                  board[point.ROW][point.COL] = 0;
                  break;
              }
          }
          board[point.ROW][point.COL] = 0; //Reset this point
      }
      return turn == 1 ? max : min;
  }

  public int returnNextMove() {
      if (isGameOver()) {
          return -1;
      }
      minimax(0, 1);
      //return 1;
      return computersMove.ROW * 3 + computersMove.COL;
  }

}
