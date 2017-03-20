/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;

/**
 *
 * @author mohamed
 */
public class PlayWithUser extends Player{
    
     
      public int returnNextMove() {
      if (isGameOver()) {
          return -1;
      }
      //minimax(0, 1);
      return 1;
    //  return computersMove.ROW * 3 + computersMove.COL;
  }
    
}
