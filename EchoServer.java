import java.net.*;
import java.io.*;

public class EchoServer2
{
   public static void main(String args[]) throws IOException
   {
      Player server = new Player("Server");
      int receivedTargetX = -1;
      int receivedTargetY = -1;
      String myTarget = "";
      int myTargetX = -1;
      int myTargetY = -1;
      String theirResult = "";
      String myResult = "";
      boolean readyStatus = false;
   
      if (args.length != 1)
      {
         System.err.println("Usage: java EchoServer <port number>");
         System.exit(1);
      }
   
      int portNumber = Integer.parseInt(args[0]);
   
      //Recieve Mode
      try 
      {
         ServerSocket serverSocket = new ServerSocket(portNumber);
         Socket clientSocket = serverSocket.accept();
         BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      
         String answer2 = in.readLine();
         while(readyStatus == false)
         {
            if(answer2.equals("READY"))
            {
               System.out.println("Ready? Y/N");
               String answer = stdIn.readLine();
               if(answer.equals("Y"))
               {
                  out.println("READY");
                  readyStatus = true;
               }
            }
         }
      
         String inputLine;
         server.displayBoards();
         System.out.println("Waiting for enemy's target...");
         while((inputLine = in.readLine()) != null)
         {
            String [] temp = inputLine.split(",");
            receivedTargetX = Character.getNumericValue(temp[1].charAt(0));
            receivedTargetY = Character.getNumericValue(temp[2].charAt(0));
            theirResult =server.incomingShot(receivedTargetX,receivedTargetY);
            System.out.print("Incoming shot at: ("+server.intToChar(receivedTargetX)
                  +","+receivedTargetY+")!\t");
            if(theirResult.contains("BATTLESHIP"))
            {
               System.out.println(theirResult);
               out.println(theirResult);
               System.out.println("THE ENEMY HAS WON. ALL IS LOST.");
               System.exit(0);
            }
            else if(theirResult.contains("SUNK"))
            {
               String temp2[] = theirResult.split(" ");
               System.out.println("THEY SUNK YOUR "+ temp2[4]+"!");
               out.println(theirResult);
            }
            else
            {
               System.out.println(theirResult);
               out.println(theirResult);  
            }
         
            server.displayBoards();
            myTarget = server.shoot();
            myTargetX = Character.getNumericValue(myTarget.charAt(0));
            myTargetY = Character.getNumericValue(myTarget.charAt(2));
            out.println("MOVE,"+myTargetX+","+myTargetY);
            myResult = in.readLine();
            System.out.println("Your move ("+server.intToChar(Character.getNumericValue(myTarget.charAt(0)))
                     +","+myTargetY+"):"+ myResult+"!");
            
            if(myResult.contains("BATTLESHIP"))
            {
               System.out.println("YOU WIN!");
               System.exit(0);
            }
            else if(myResult.contains("HIT") || myResult.contains("SUNK"))
            {
               server.markEnemyBoard(myTargetX,myTargetY,1);
            }
            else if(myResult.contains("MISS"))
            {
               server.markEnemyBoard(myTargetX,myTargetY,7);
            }
         
            server.displayBoards();
            System.out.println("Waiting for enemy's target...");
         }
      } 
      catch (IOException e)
      {
         System.out.println("Excpetion caught when trying to listen on port " + 
                         portNumber + " or listening for a connection");
         System.out.println(e.getMessage());
      }
   }
}
