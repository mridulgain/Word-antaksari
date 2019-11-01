// Server
import java.net.*;
import java.io.*;
import java.util.Random;

public class GameServer{
	public static void main(String args[]){
		try{
  		    int portNo = 2000;
			ServerSocket connectionSocket = new ServerSocket(portNo);
			System.out.println("Server is ready. Waiting for connection...");
			int count = 0;
			while(true){			
				//adding player 1
				MyStreamSocket dataSocket1 = new MyStreamSocket(connectionSocket.accept());
				//adding player 2
				MyStreamSocket dataSocket2 = new MyStreamSocket(connectionSocket.accept());
				Thread t = new Thread(new GameServerThread(dataSocket1, dataSocket2), String.valueOf(count++));
				t.start();
			}//while
		}//try
		catch(SocketException e){
			System.out.println("Connection closed");
		}
		catch(Exception e){
			System.out.println(e);
		}

	}
}
/* To make our Game server concurrent */
class GameServerThread implements Runnable{
	MyStreamSocket dataSocket1, dataSocket2;
	String id;
	GameServerThread(MyStreamSocket d1, MyStreamSocket d2){
		dataSocket1 = d1;
		dataSocket2 = d2;
		
	}
	public void run(){
		this.id = "Thread " + Thread.currentThread().getId();
		try{
			String player1 = dataSocket1.receiveMessage();
			String player2 = dataSocket2.receiveMessage();
			dataSocket1.sendMessage("1,"+player2);//Acknowledging player 1
			dataSocket2.sendMessage("2,"+player1);//Acknowledging player 1
			System.out.println(id + " ~ Player 1 " + player1 + " has joined....Waiting for player 2");
			System.out.println(id + " ~ Player 2 " + player2 +" has joined....Starting the game");
			//Dictionary
			Dictionary dict = new Dictionary();
			//toss
			Random rand = new Random();
			int rand_id = rand.nextInt(2) + 1;
			char rand_ch = (char)(rand.nextInt(26) + 97);
			String msg = rand_id + "," + rand_ch;
			System.out.println(id + " ~ toss result, start letter " + msg);
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
				System.out.println(id + " ~ " + msg);
				if(msg.equals("q")){
					dataSocket2.sendMessage(msg);
					break;
				}
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
						else{
							dataSocket1.sendMessage("D," + String.valueOf(dataSocket1.score)+","+String.valueOf(dataSocket2.score));
							dataSocket2.sendMessage("D," + String.valueOf(dataSocket2.score)+","+String.valueOf(dataSocket1.score));
							break;
						}
						win.sendMessage("W," + String.valueOf(win.score)+","+String.valueOf(lose.score));
						lose.sendMessage("L," + String.valueOf(lose.score)+","+String.valueOf(win.score));
						break;
					}
					dataSocket2.sendMessage(msg + "," + String.valueOf(dataSocket2.score) + "," + String.valueOf(dataSocket1.score));
					dataSocket1.sendMessage("T," + String.valueOf(dataSocket1.score) + "," + String.valueOf(dataSocket2.score));
					//swap turn
					MyStreamSocket temp = dataSocket1;
					dataSocket1 = dataSocket2;
					dataSocket2 = temp;
				}
			}
			dataSocket1.close();
			dataSocket2.close();
			//connectionSocket.close();
		}//try
		catch(SocketException e){
			System.out.println("Connection closed");
		}
		catch(Exception e){
			System.out.println(e);
		}

	}//run
}//GameServerThread
