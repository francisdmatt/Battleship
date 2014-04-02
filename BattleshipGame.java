/////////////////////////////////////////////////////////////////
// Title: Battleship (Local Machine)                           //
// Purpose: Simulate a Battleship game on your local computer. //
// Creator: Matthew Francis                                    //
// Date Started: 4/2/14                                        //  
// Date Finished:                                              //
// Status:                                                     //
// Notes: The following board information regards the coded    //
//         version of the board, the live version is converted //
//         to H (Hit), M (Miss), or the respective ship on     //
//         the player board C (Carrier), P (Patrol), etc.      //
//                                                             //
// The following is the player board dictionary(Int version)   //
//       0 - Water                                             //
//       1 - Hit                                               //
//       2 - Patrol                                            //
//       3 - Submarine                                         //
//       4 - Destroyer                                         //
//       5 - Carrier                                           //
//       6 - Battleship                                        //
//                                                             //
// The following is the enemy board dictionary(Int version)    //
//       0 - Water                                             //
//       1 - Hit                                               //
//       7 - Miss                                              //
//                                                             //
/////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

public class BattleshipGame
{
   static Scanner input = new Scanner(System.in);
   final static int boardSizeX = 10;
   final static int boardSizeY = 10;
   static boolean winCondition = false;
   public static void main(String [] args)
   {
      // Create game variables
      System.out.println("Creating Player(s).");
      
      System.out.println("Generating Player 1 Board.");
      Player player1 = new Player("Player 1", boardSizeX, boardSizeY);
      System.out.println("Generating Player 2 Board.");
      Player player2 = new Player("Player 2", boardSizeX, boardSizeY);
      
      System.out.println("Let the game begin.");
      String temp = input.next();
      // Main Game Loop
      boolean player1Hit, player2Hit;
      String player1Result, player2Result, gameWinner;
      // While no battleship has been sunk, the game will continue
      // If a player successfully hits an enemy ship, they get to go again
      while(winCondition == false)
      {  
         player1Hit = true;
         player2Hit = true;
         
         while(player1Hit == true)
         {
            player1Result = "";
            player1.displayEnemyBoard();
            player1.displayMyBoard();
            player1Result = player1.shoot(1,1); // return appropriate response
            player1Hit = false;
            if(winCondition == true)
               break;
         } 
         
         while(player2Hit == true)
         {
            player2Result = "";
            player2.displayEnemyBoard();
            player2.displayMyBoard();
            player2Result = player2.shoot(1,1); // return appropriate response
            player2Hit = false;
            if(winCondition == true)
               break;
         }   
      }
   }
}

class Player
{
   String playerName;
   private int remainingShips;
   private int[][] myBoard;
   private int[][] enemyBoard;
   public Player(String player,int boardSizeX, int boardSizeY)
   {
      playerName = player;
      remainingShips = 5;
      myBoard = new int[boardSizeX][boardSizeY];
      enemyBoard = new int[boardSizeX][boardSizeY];
      generateShips(myBoard, boardSizeX, boardSizeY);
      generateEnemyBoard(enemyBoard, boardSizeX, boardSizeY);
   }
   
   public void displayMyBoard()
   {
      System.out.println(playerName + "'s Board");
   }
   
   public void displayEnemyBoard()
   {
      System.out.println(playerName + "'s Fired Shots");
   }
   
   // Creates the initial board
   private void generateShips(int[][] board, int boardSizeX, int boardSizeY)
   {
      // Initial Board Creation
      for(int q = 0; q < boardSizeX; q++)
      {
         for(int w = 0; w < boardSizeY; w++)
         {
            //System.out.print("|");
            board[q][w] = 0;
            System.out.print(board[q][w]);
         }
         //System.out.println("|");
         System.out.println();  
      }
      
      //Ship Generation
      for(int i = 6; i > 1; i--)
      {
         placeShipAttempt(i,board);
      } 
   }
   
   //Generates the starting board for the enemy
   // Status complete
   private void generateEnemyBoard(int[][] board, int boardSizeX, int boardSizeY)
   {
      // Initial Board Creation
      for(int q = 0; q < boardSizeX; q++)
      {
         for(int w = 0; w < boardSizeY; w++)
         {
            //System.out.print("|");
            board[q][w] = 0;
            System.out.print(board[q][w]);
         }
         //System.out.println("|");
         System.out.println();  
      }
   }
   
   // Returns random start information for ship placement
   // Status: Complete
   private String shipInfo()
   {
      Random rand = new Random();
      //Direction puts the head at this point and extends the opposite direction
      int direction = rand.nextInt(4); // 0 - N, 1 - S, 2 - E, 3 - W 
      int column = rand.nextInt(10) + 1; // Random value 1 to 10
      int row = rand.nextInt(10) + 1; // Random value 1 to 10, convert to letters in display
      // Example, direction = 3, column = 7, row = 2
      //    It would place the first point of the ship in 7,2 -> 6,2 -> 5,2 -> 4,2 -> 3,2 -> 2,2
      return direction + "," + column +  "," + row;
   }
   
   // Place the different ships and mark it on the board
   // Status: In Progress
   private boolean placeShipAttempt(int size, int[][] board)
   {
      boolean shipPlaced = false;
      while(shipPlaced == false)
      {
         //Get random points for ship placement
         String[] temp = shipInfo().split(",");
         int[] info = new int[3];
         for(int i = 0; i < 3; i++)
            info[i] = Integer.parseInt(temp[i]);
      
         // Take the information for ship placement above
         // Check to see if it can fit
         // If it can't, shipsPlaced remains false, it loops around, grabs new
         // information and tries again.
         // If it fits, commit the changes to the board
      }    
      return true;
   }
   
   public String shoot(int x, int y)
   {
      System.out.println(playerName + " SHOTS FIRED AT: (" + x + "," + y + ")!");
      return "string";
   }
}