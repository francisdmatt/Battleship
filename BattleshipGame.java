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
   static boolean winCondition = false;
   public static void main(String [] args)
   {
      // Create game variables
      System.out.println("Creating Player(s).");
      
      System.out.println("Generating Player 1 Board.");
      Player player1 = new Player("Player 1");
      System.out.println("Generating Player 2 Board.");
      Player player2 = new Player("Player 2");
      
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
            player1.displayEnemyBoard();  // Display players fired shots
            player1.displayMyBoard();     // Display players board
            player1Result = player1.shoot(); // return appropriate response
            player1Hit = false;
            if(winCondition == true)
               break;
         } 
         
         while(player2Hit == true)
         {
            player2Result = "";
            player2.displayEnemyBoard();  // Display players fired shots
            player2.displayMyBoard();     // Display players board 
            player2Result = player2.shoot(); // return appropriate response
            player2Hit = false;
            if(winCondition == true)
               break;
         }   
      }
   }
}

class Player
{
   static Scanner input = new Scanner(System.in);
   String playerName;
   private int remainingShips;
   private int[][] myBoard;
   private int[][] enemyBoard;
   public Player(String player)
   {
      playerName = player;
      remainingShips = 5;
      myBoard = new int[10][10];
      enemyBoard = new int[10][10];
      generateShips(myBoard);
      generateEnemyBoard(enemyBoard);
   }
      
   // Creates the initial board
   private void generateShips(int[][] board)
   {
      // Initial Board Creation
      for(int q = 0; q < 10; q++)
      {
         for(int w = 0; w < 10; w++)
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
   private void generateEnemyBoard(int[][] board)
   {
      // Initial Board Creation
      for(int q = 0; q < 10; q++)
      {
         for(int w = 0; w < 10; w++)
         {
            //System.out.print("|");
            board[q][w] = 0;
            System.out.print(board[q][w]);
         }
         //System.out.println("|");
         System.out.println();  
      }
   }
   
   public void displayMyBoard()
   {
      System.out.println(playerName + "'s Board");
      for(int q = 0; q < 10; q++)
      {
         for(int w = 0; w < 10; w++)
         {
            //System.out.print("|");
            System.out.print(myBoard[q][w]);
         }
         //System.out.println("|");
         System.out.println();  
      }
   }
   
   public void displayEnemyBoard()
   {
      System.out.println(playerName + "'s Fired Shots");
      for(int q = 0; q < 10; q++)
      {
         for(int w = 0; w < 10; w++)
         {
            //System.out.print("|");
            System.out.print(enemyBoard[q][w]);
         }
         //System.out.println("|");
         System.out.println();  
      }
   }

   // Returns random start information for ship placement
   private String shipInfo()
   {
      Random rand = new Random();
      //Direction puts the head at this point and extends the opposite direction
      int direction = rand.nextInt(4); // 0 - N, 1 - S, 2 - E, 3 - W 
      int column = rand.nextInt(10); // Random value 0 to 9
      int row = rand.nextInt(10); // Random value 0 to 9, convert to letters in display
      // Example, direction = 3, column = 7, row = 2
      //    It would place the first point of the ship in 7,2 -> 6,2 -> 5,2 -> 4,2 -> 3,2 -> 2,2
      return direction + "," + row +  "," + column;
   }
   
   // Place the different ships and mark it on the board
   private boolean placeShipAttempt(int size, int[][] board)
   {
      boolean shipPlaced = false;
      while(shipPlaced == false)
      {
         //Get random points for ship placement
         String[] temp = shipInfo().split(",");
         int direction, rowIndex, columnIndex;
         direction = Integer.parseInt(temp[0]);
         rowIndex = Integer.parseInt(temp[1]);
         columnIndex = Integer.parseInt(temp[2]);
         
         // Check to see the origin spot is valid
         if(board[rowIndex][columnIndex] == 0)
         {
            int temp1 = 0;
            boolean isValidSpot = true;
            if(direction == 0 || direction == 1)
            {
               for(int i = 0; i < size; i++)
               {
                  if(rowIndex+size > 9)
                  {
                     isValidSpot = false;
                     break;
                  }
                  else if(board[rowIndex+i][columnIndex] == 0)
                     temp1 = 0;
                  else
                  {
                     isValidSpot = false;
                     break;
                  } 
               }
               if(isValidSpot == true)
               {
                  for(int p = 0; p < size; p++)
                     board[rowIndex+p][columnIndex] = size;
                  shipPlaced = true;
               }    
            }
            else if(direction == 2 || direction == 3)
            {
               for(int i = 0; i < size; i++)
               {
                  if(columnIndex+size > 9)
                  {
                     isValidSpot = false;
                     break;
                  }
                  else if(board[rowIndex][columnIndex+i] == 0)
                     temp1 = 0;
                  else
                  {
                     isValidSpot = false;
                     break;
                  } 
               }
               if(isValidSpot == true)
               {
                  for(int p = 0; p < size; p++)
                     board[rowIndex][columnIndex+p] = size;
                  shipPlaced = true;
               }
            }
            else
               break; 
         }
      }    
      return shipPlaced;
   }
   
   //Player inputs their target coordinate, and the game sends a message to 
   // the other player asking if it was a hit or miss, result is recorded on
   // both players boards.
   public String shoot()
   {
      int xCoord, yCoord;
      String target = "";
      while(true)
      {
         System.out.println("Please enter your target (ex. a,4):");
         target = input.next();
         // Make sure the input is valid
         if(target.length() == 3 && target.charAt(1) == ',')
         {
            String cords[] = target.split(",");
            cords[0] = cords[0].toUpperCase();
            xCoord = alphaToInt(cords[0]);
            if(xCoord != -1)
            {
               yCoord = Integer.parseInt(cords[1]);
               break;
            }
         }
         else
            System.out.println("\tInvalid input. Try again");   
      }
      System.out.println(playerName + " SHOTS FIRED AT: (" + xCoord + "," + yCoord + ")!");
      return "string";
   }
   
   public String iHaveBeenShot(int xCoord, int yCoord)
   {
      return "Damn.";
   }
   
   private int alphaToInt(String character)
   {
      char temp = character.charAt(0);
      switch(temp)
      {
         case 'A': 
            return 0;
         case 'B': 
            return 1;
         case 'C': 
            return 2;
         case 'D': 
            return 3;
         case 'E': 
            return 4;
         case 'F': 
            return 5;
         case 'G': 
            return 6;
         case 'H': 
            return 7;
         case 'I': 
            return 8;
         case 'J': 
            return 9;
      }
      return -1;
   }
}