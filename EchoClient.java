import java.io.*;
import java.net.*;

public class EchoClient {
   public static void main(String[] args) throws IOException 
   {
      if(args.length != 2) 
      {
         System.err.println("Usuage: java EchoClient <host name> <port number>");
         System.exit(1);
      }
   
      String hostName = args[0];
      int portNumber = Integer.parseInt(args[1]);
   
      Player client = new Player("Client");
      boolean wonGame = false;
      boolean playerHit = false;
      String myResult = ""; // Result of last shot
      String incomingShot = ""; // Incoming target
      String theirResult = "NULL"; // Result of their shot
      String target = ""; // My new target
      int myTargetX, myTargetY;
      String[] target2;
      int theirX, theirY;
      String status = "notReady";
      String userInput = "";
      String userInput2[];
   	// Message Format:
   	//		HIT/MISS/SUNKETC,MOVE,A,2
   
      while(true)
      {
      	//SendMode
         try 
         {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter outSend = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader inSend = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
         
            while(status != "READY")
            {
               System.out.println("Ready? Y/N");
               String readyStatus = stdIn.readLine();
               if(readyStatus.equals("Y"))
               {
                  outSend.println(readyStatus);
                  String inputLine = inSend.readLine();
                  if(inputLine.equals("Y"))
                     status = "READY";
               }
            }
         
            while (wonGame==false && status.equals("READY"))
            {
               client.displayBoards();
            
               target = client.shoot(); // Get target:  a,2
               target2 = target.split(",");
               myTargetX = Integer.parseInt(target2[0]);
               myTargetY = Integer.parseInt(target2[1]);
               userInput = theirResult + ",MOVE," + target;
            
            
            	//Sending message to server
               System.out.println("Outsend: " + userInput);
            
               outSend.println(userInput);
               if(userInput.contains("BATTLESHIP"))
               {
                  System.exit(0);
                  for(int i = 0; i < 100; i++)
                     System.out.print("");
               }
               System.out.println("Waiting to Recieve message ...");
            	
            
            	//Response from Server Client
               userInput = inSend.readLine();
            
               System.out.println();
               System.out.println("\tRecieved "+userInput);
               if(userInput.contains("BATTLESHIP"))
               {
                  System.exit(0);
                  for(int i = 0; i < 100; i++)
                     System.out.print("");
               }
               userInput2 = userInput.split(",");
               myResult = userInput2[0];
               if(myResult.contains("NULL"))
                  System.out.print("");
               if(myResult.contains("MISS"))
                  client.markEnemyBoard(myTargetX,myTargetY,7);
               if(myResult.contains("HIT") || myResult.contains("SUNK"))
                  client.markEnemyBoard(myTargetX,myTargetY,1);
            
               theirX = Integer.parseInt(userInput2[2]);
               theirY = Integer.parseInt(userInput2[3]);
               theirResult = client.incomingShot(theirX,theirY);
               client.markMyBoard(theirX,theirY);
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

   public String sendOut(String toSend)
   {
      return "Sent";
   }
}