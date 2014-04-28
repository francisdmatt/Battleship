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
/*
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
            player1.displayEnemyBoard();  // Display players fired shots
            player1.displayMyBoard();     // Display players board
            
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
            player2.displayEnemyBoard();  // Display players fired shots
            player2.displayMyBoard();     // Display players board
            
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
*/

class Player
{
   static Scanner input = new Scanner(System.in);
   String playerName;
   int remainingShips;
   int[][] myBoard;
   int[][] enemyBoard;
   
   public Player(String player)
   {
      playerName = player;
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
            //System.out.print(board[q][w]);
         }
         //System.out.println("|");
         //System.out.println();  
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
            //System.out.print(board[q][w]);
         }
         //System.out.println("|");
         //System.out.println();  
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
   
   public void displayBoards()
   {
      for(int i = 0; i < 10; i++)
      {
         
         for(int k = 0; k < 10; k++)
         {
            System.out.print(" | " + myBoard[i][k]);
         }
         System.out.print(" |\t");
         for(int k = 0; k < 10; k++)
         {
            System.out.print(" | " + enemyBoard[i][k]);
         }
         System.out.println();
         System.out.print(" ");
         for(int k = 0; k<41; k++)
         {
            System.out.print("-");
         }
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
         int direction = Integer.parseInt(temp[0]);
         int rowIndex = Integer.parseInt(temp[1]);
         int columnIndex = Integer.parseInt(temp[2]);
         
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
   
   public int alphaToInt(char temp)
   {
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
   
   public boolean searchGrid(int[][] myBoard, int target)
   {
      boolean stillHere = false;
      for(int i = 0; i < 10; i++)
      {
         for(int k = 0; k < 10; k++)
         {
            if(myBoard[i][k] == target)
               stillHere = true;
         }
      }
      return stillHere;   
   }

      //Player inputs their target coordinate, this checks to verify the input is valid
   public String shoot()
   {
      int xCoord, yCoord;
      String target = "";
      String cords[];
      while(true)
      {
         System.out.println("Please enter your target (ex. a,4):");
         target = input.next();
         // Make sure the input is valid
         if(target.length() == 3 && target.charAt(1) == ',')
         {
            cords = target.split(",");
            cords[0] = cords[0].toUpperCase();
            xCoord = alphaToInt(cords[0].charAt(0));
            if(xCoord != -1)
            {
               yCoord = Integer.parseInt(cords[1]);
               break;
            }
            System.out.println("Please enter a valid target");
         }
         else
            System.out.println("\tInvalid input. Try again");   
      }
      System.out.print(playerName + " SHOTS FIRED AT: (" + cords[0] + "," + yCoord + ")!\n\t");
      return xCoord+","+yCoord;
   }
   
   public String incomingShot(int xCoord, int yCoord)
   {
      if(myBoard[xCoord][yCoord] == 0 || 
         myBoard[xCoord][yCoord] == 1 ||
         myBoard[xCoord][yCoord] == 7)
         return "MISS";
      else if(myBoard[xCoord][yCoord] == 2)
      {
         myBoard[xCoord][yCoord] = 1;
         if(searchGrid(myBoard,2) == true)
            return "HIT";
         else
            return "YOU SUNK MY PATROL";
      }
      else if(myBoard[xCoord][yCoord] == 3)
      {
         myBoard[xCoord][yCoord] = 1;
         if(searchGrid(myBoard,3) == true)
            return "HIT";
         else
            return "YOU SUNK MY SUBMARINE";
      }
      else if(myBoard[xCoord][yCoord] == 4)
      {
         myBoard[xCoord][yCoord] = 1;
         if(searchGrid(myBoard,4) == true)
            return "HIT";
         else
            return "YOU SUNK MY DESTROYER";
      }
      else if(myBoard[xCoord][yCoord] == 5)
      {
         myBoard[xCoord][yCoord] = 1;
         if(searchGrid(myBoard,5) == true)
            return "HIT";
         else
            return "YOU SUNK MY CARRIER";
      }
      else if(myBoard[xCoord][yCoord] == 6)
      {
         // Check if that spot has the battleship
         myBoard[xCoord][yCoord] = 1;
         
         if(searchGrid(myBoard,6) == true)
            return "HIT";
         else
            return "YOU SUNK MY BATTLESHIP. YOU WIN! GAME OVER.";
      }
      else
         return "Failed";
   }

   public void markMyBoard(int x, int y)
   {
      // Marks on my personal board where they have shot
      if(myBoard[x][y] == 0 || myBoard[x][y] == 7)
         myBoard[x][y] = 7;
      else
         myBoard[x][y] = 1;
   }

   public void markEnemyBoard(int x, int y, int z)
   {
      enemyBoard[x][y] = z;
   }
}