/////////////////////////////////////////////////////////////////
// Title: Battleship (Local Machine)                           //
// Purpose: Simulate a Battleship game on your local computer. //
// Creator: Matthew Francis                                    //
// Date Started: 4/2/14                                        //  
// Date Finished:                                              //
// Status:                                                     //
// Notes: The following board information regards the coded    //
//         version of the board, the live version is converted //
//         to H (HIT), M (Miss), or the respective ship on     //
//         the player board C (Carrier), P (Patrol), etc.      //
//                                                             //
// The following is the player board dictionary(Int version)   //
//       0 - Water                                             //
//       1 - HIT (Other player's shot)                         //
//       2 - Patrol                                            //
//       3 - Submarine                                         //
//       4 - Destroyer                                         //
//       5 - Carrier                                           //
//       6 - Battleship                                        //
//       7 - Miss (Other player's shot)                        //
// The following is the enemy board dictionary(Int version)    //
//       0 - Water                                             //
//       1 - HIT                                               //
//       7 - Miss                                              //
//                                                             //
/////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

public class BattleshipGame
{
   static Scanner input = new Scanner(System.in);
   static boolean winCondition = false;

   public static void main(String [] args)
   {
      // Create game variables
      //System.out.println("Creating Player(s).");
      
      //System.out.println("Generating Player 1 Board.");
      Player player1 = new Player("Player 1");
      //System.out.println("Generating Player 2 Board.");
      Player player2 = new Player("Player 2");
      
      System.out.println("Let the game begin.");
      //String temp = input.next();
      
      // Main Game Loop
      boolean player1HIT, player2HIT;
      String player1Result, player2Result, gameWinner;
      String player1Shots, player2Shots;
      // While no battleship has been sunk, the game will continue
      // If a player successfully HITs an enemy ship, they get to go again
      while(winCondition == false)
      {  
         player1HIT = true;
         player2HIT = true;
         
         while(player1HIT == true && winCondition == false)
         {
            player1Result = "";
            player1Shots = "";
            player1.displayBoards();
            
            player1Shots = player1.shoot(); // return appropriate response
            String [] player1Shot = player1Shots.split(" ");
            player1Result = player2.incomingShot(Integer.parseInt(player1Shot[0]),Integer.parseInt(player1Shot[1]));
            if(player1Result.contains("Miss"))
            {
               System.out.println("\t\tMiss");
               player1HIT = false;
               player1.enemyBoard[Integer.parseInt(player1Shot[0])][Integer.parseInt(player1Shot[1])] = 7;
            }
            else if(player1Result.equals("YOU SUNK MY BATTLESHIP. YOU WIN! GAME OVER."))
            {
               winCondition = true;
               System.out.println("you win");
            }
            else
            {
               player1HIT = true;
               player1.enemyBoard[Integer.parseInt(player1Shot[0])][Integer.parseInt(player1Shot[1])] = 1;
               System.out.println("\t\t"+player1Result);
            }  
               
               
            if(winCondition == true)
               break;
         } 
         
         while(player2HIT == true && winCondition == false)
         {
            player2Result = "";
            player2Shots = "";
            player2.displayBoards();
            
            player2Shots = player2.shoot(); // return appropriate response
            String [] player2Shot = player2Shots.split(" ");
            player2Result = player1.incomingShot(Integer.parseInt(player2Shot[0]),Integer.parseInt(player2Shot[1]));
            if(player2Result.contains("Miss"))
            {
               System.out.println("\t\tMiss");
               player2HIT = false;
               player2.enemyBoard[Integer.parseInt(player2Shot[0])][Integer.parseInt(player2Shot[1])] = 7;
            }
            else if(player2Result.equals("YOU SUNK MY BATTLESHIP. YOU WIN! GAME OVER."))
            {
               winCondition = true;
               System.out.println("you win");
            }
            else
            {
               player2HIT = true;
               player2.enemyBoard[Integer.parseInt(player2Shot[0])][Integer.parseInt(player2Shot[1])] = 1;
               System.out.println("\t\t"+player2Result);
            }  
               
               
            if(winCondition == true)
               break;
         }   
      }
   }
}
