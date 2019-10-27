// Server
import java.net.*;
import java.io.*;
import java.util.Random;

public class GameServer{
	public static void main(String args[]){
		OutputStream outStream1, outStream2;
		PrintWriter socketOutput1, socketOutput2;
		InputStream inStream1, inStream2;
		BufferedReader socketInput1, socketInput2;
		try{
  		    int portNo = 2000;
			ServerSocket connectionSocket = new ServerSocket(portNo);
			System.out.println("Waiting for connection...");
			//adding player 1
			MyStreamSocket dataSocket1 = new MyStreamSocket(connectionSocket.accept());
			String player1 = dataSocket1.receiveMessage();
			dataSocket1.sendMessage("1");//Acknowledging player 1
			System.out.println("Player 1 " + player1 + " has joined....Waiting for player 2");
			//adding player 2
			MyStreamSocket dataSocket2 = new MyStreamSocket(connectionSocket.accept());
			String player2 = dataSocket2.receiveMessage();
			dataSocket2.sendMessage(player1);//Acknowledging player 2
			dataSocket1.sendMessage(player2);
			System.out.println("Player 2 " + player2 +" has joined....Starting the game");
			//toss
			Random rand = new Random();
			int rand_id = rand.nextInt(2) + 1;
			char rand_ch = (char)(rand.nextInt(26) + 97);
			String msg = rand_id + "," + rand_ch;
			//System.out.println(msg);
			dataSocket1.sendMessage(msg);
			dataSocket2.sendMessage(msg);
			while(true){
				break;
			}
			dataSocket1.close();
			dataSocket2.close();
			connectionSocket.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

	}
}
/* To make our Game server concurrent
class GameServerThread implements Runnable{

}*/
