// Server
import java.net.*;
import java.io.*;
import java.util.Random;

public class GameServer{
	public static void main(String args[]){
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
			dataSocket1.sendMessage(player2);//player 1 is informed about player 2
			System.out.println("Player 2 " + player2 +" has joined....Starting the game");
			//Dictionary
			Dictionary dict = new Dictionary();
			//toss
			Random rand = new Random();
			int rand_id = rand.nextInt(2) + 1;
			char rand_ch = (char)(rand.nextInt(26) + 97);
			String msg = rand_id + "," + rand_ch;
			System.out.println(msg);
			dataSocket1.sendMessage(msg);
			dataSocket2.sendMessage(msg);
			if(rand_id == 2){
				MyStreamSocket temp = dataSocket1;
				dataSocket1 = dataSocket2;
				dataSocket2 = temp;
				System.out.println("swap");
			}
			while(true){
				msg = dataSocket1.receiveMessage();
				System.out.println("word"+msg);
				if(valid(dict, msg)){
					dataSocket2.sendMessage(msg);
					dataSocket1.sendMessage("T");
					//swap turn
					MyStreamSocket temp = dataSocket1;
					dataSocket1 = dataSocket2;
					dataSocket2 = temp;
				}
				else{
					dataSocket1.sendMessage("F");
				}
			}
			//dataSocket1.close();
			//dataSocket2.close();
			//connectionSocket.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

	}
	//helping methods
	private static boolean valid(Dictionary dict,String msg){
		//1. present in dict
		//2. not accessed before
		//3. 
		return true;
	}
}
/* To make our Game server concurrent */
/*class GameServerThread implements Runnable{

}*/
