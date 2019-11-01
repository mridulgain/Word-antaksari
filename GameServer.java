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
				//System.out.println("swap");
			}
			int counter = 0;
			while(true){
				msg = dataSocket1.receiveMessage();
				//String error_code;
				System.out.println("Word :" + msg);
				if(msg.equals("q"))
					dataSocket2.sendMessage("q");
				else if(msg.length() < 3)
					dataSocket1.sendMessage("S");
				else if(dict.isCommon(msg))
					dataSocket1.sendMessage("C");
				else if(dict.accessedBefore(msg))
					dataSocket1.sendMessage("R");
				else if(!dict.contains(msg))
					dataSocket1.sendMessage("F");
				else{
					dict.addToHistory(msg);
					dataSocket1.turnCount++;
					dataSocket1.score += msg.length();
					if(dataSocket1.turnCount == 2 && dataSocket2.turnCount == 2){
						MyStreamSocket win = null, lose = null;
						if(dataSocket1.score > dataSocket2.score){
							 win = dataSocket1;
							 lose = dataSocket2;
						}
						else if(dataSocket1.score < dataSocket2.score){
							win = dataSocket2;
							lose = dataSocket1;
						}
						win.sendMessage("W," + String.valueOf(win.score)+","+String.valueOf(lose.score));
						lose.sendMessage("L," + String.valueOf(lose.score)+","+String.valueOf(win.score));
						System.exit(0);
					}
					dataSocket2.sendMessage(msg + "," + String.valueOf(dataSocket2.score) + "," + String.valueOf(dataSocket1.score));
					dataSocket1.sendMessage("T," + String.valueOf(dataSocket1.score) + "," + String.valueOf(dataSocket2.score));
					//swap turn
					MyStreamSocket temp = dataSocket1;
					dataSocket1 = dataSocket2;
					dataSocket2 = temp;
				}
			}
			//dataSocket1.close();
			//dataSocket2.close();
			//connectionSocket.close();
		}
		catch(SocketException s){
			System.out.println("Connection closed");
		}
		catch(Exception e){
			System.out.println(e);
		}

	}
}
/* To make our Game server concurrent */
/*class GameServerThread implements Runnable{

}*/
