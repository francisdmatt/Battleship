import java.net.*;
import java.io.*;

public class EchoServer
{
	public static void main(String args[]) throws IOException
	{
		if (args.length != 1)
		{
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		Player server = new Player("Server");
		boolean wonGame = false;
	    boolean playerHit = false;
      	String myResult = ""; // Result of last shot
		String incomingShot = ""; // Incoming target
		String theirResult = ""; // Result of their shot
		String target = ""; // My new target
		int myTargetX=-1, myTargetY=-1;
		String[] target2;
		int theirX=-1, theirY=-1;
		String status = "READY";
		String userInput = "";
		String userInput2[];

		// Message Format:
		//		HIT/MISS/SUNKETC,MOVE,A,2
		//Recieve Mode
		try 
		{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			server.displayEnemyBoard();
			server.displayMyBoard();
			String inputLine;
			System.out.println("Waiting to Recieve message ...");
			while((inputLine = in.readLine()) != null)
			{
				server.displayEnemyBoard();
				server.displayMyBoard();
				//POINT A
				//
				//At this point the user has sent the information to the host (this program)

				System.out.println("\tRecieved "+inputLine);
					if(inputLine.contains("BATTLESHIP"))
					{
						System.exit(0);
						for(int i = 0; i < 100; i++)
							System.out.print("");
					}

					userInput2 = inputLine.split(",");
					myResult = userInput2[0];
					if(myResult.contains("NULL"))
						System.out.print("");
					
					if(myResult.contains("MISS"))
						server.markEnemyBoard(myTargetX,myTargetY,7);
					
					if(myResult.contains("HIT") || myResult.contains("SUNK"))
						server.markEnemyBoard(myTargetX,myTargetY,1);

					System.out.println("\t" + userInput2[2]);
					theirX = Integer.parseInt(userInput2[2]);
					theirY = Integer.parseInt(userInput2[3]);					
					System.out.println("\t X: "+ theirX);
					System.out.println("\t Y: "+ theirY);
					theirResult = server.incomingShot(theirX,theirY);
					server.markMyBoard(theirX,theirY);

				System.out.println("Swtiching to SendMode");
				System.out.println("Type Message >>>");
				//inputLine = stdIn.readLine();

				//POINT B

				//
				//User input is waiting in the userInput variable at this point
				//The below funciton is the variable that is sent to the client

					server.displayEnemyBoard();
					server.displayMyBoard();
					//POINT A
                  	target = server.shoot(); // Get target:  a,2
                  	target2 = target.split(",");
                  	myTargetX = Integer.parseInt(target2[0]);
                  	myTargetY = Integer.parseInt(target2[1]);
                  	userInput = theirResult + ",MOVE," + target;

                  	//inputLine = theirResult+",MOVE,"+
                 System.out.println("Outsend: " +userInput);
				out.println(userInput);
				if(userInput.contains("BATTLESHIP"))
				{
					System.exit(0);
					for(int i = 0; i < 100; i++)
						System.out.print("");
				}
				System.out.println("Waiting to Recieve message ...");
			}
		} 
		catch (IOException e)
		{
			System.out.println("Excpetion caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}