import java.io.*;
import java.net.*;

public class EchoClient2
{
   public static void main(String[] args) throws IOException 
   {

      Player client = new Player("Client");
      if(args.length != 2) 
      {
         System.err.println("Usuage: java EchoClient <host name> <port number>");
         System.exit(1);
      }
   
      String hostName = args[0];
      int portNumber = Integer.parseInt(args[1]);
   
      while(true)
      {      
         try 
         {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter outSend = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader inSend = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
         
            String userInput;
            int receivedTargetX = -1;
            int receivedTargetY = -1;
            String myTarget = "";
            int myTargetX = -1;
            int myTargetY = -1;
            String theirResult = "";
            String myResult = "";
            boolean readyStatus = false;

            while(readyStatus == false)
            {
               System.out.println("Ready? Y/N");
               String answer = stdIn.readLine();
               if(answer.equals("Y"))
               {
                  outSend.println("READY");
                  answer = inSend.readLine();
                  if(answer.equals("READY"))
                     readyStatus = true;
               }
            }
            
            while (readyStatus == true)
            {
               client.displayBoards();
               myTarget = client.shoot();
               myTargetX = Character.getNumericValue(myTarget.charAt(0));
               myTargetY = Character.getNumericValue(myTarget.charAt(2));
               outSend.println("MOVE,"+myTargetX+","+myTargetY);
               myResult = inSend.readLine();
               System.out.println("Your move ("+client.intToChar(Character.getNumericValue(myTarget.charAt(0)))
                     +","+myTargetY+"):"+ myResult+"!");
               if(myResult.contains("BATTLESHIP"))
               {
                  System.out.println("YOU WIN!");
                  System.exit(0);
               }
               else if(myResult.contains("HIT") || myResult.contains("SUNK"))
               {
                  client.markEnemyBoard(myTargetX,myTargetY,1);
               }
               else if(myResult.contains("MISS"))
               {
                  client.markEnemyBoard(myTargetX,myTargetY,7);
               }
               client.displayBoards();
               System.out.println("Waiting for enemy's target...");
               userInput = inSend.readLine();
               System.out.println("\tRecieved "+userInput);
               String [] temp = userInput.split(",");
               receivedTargetX = Character.getNumericValue(temp[1].charAt(0));
               receivedTargetY = Character.getNumericValue(temp[2].charAt(0));
               theirResult = client.incomingShot(receivedTargetX,receivedTargetY);
               System.out.print("Incoming shot at: ("+client.intToChar(receivedTargetX)
                  +","+receivedTargetY+")!\t");

               if(theirResult.contains("BATTLESHIP"))
               {
                  System.out.println(theirResult);
                  outSend.println(theirResult);
                  System.out.println("THE ENEMY HAS WON. ALL IS LOST.");
                  System.exit(0);
               }
               else if(theirResult.contains("SUNK"))
               {
                  String temp2[] = theirResult.split(" ");
                  System.out.println("THEY SUNK YOUR "+ temp2[4]+"!");
                  outSend.println(theirResult);
               }
               else
               {
                  System.out.println(theirResult);
                  outSend.println(theirResult);  
               }
            }
         } 
         catch (UnknownHostException e)
         {
            System.err.println("Don't know about host "+hostName);
            System.exit(1);
         } 
         catch (IOException e)
         {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
         }
      }
   }
}
