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
			Socket dataSocket1 = connectionSocket.accept();
			inStream1 = dataSocket1.getInputStream();
			socketInput1 = new BufferedReader(new InputStreamReader(inStream1));
			String player1 = socketInput1.readLine();
			outStream1 = dataSocket1.getOutputStream();
			socketOutput1 = new PrintWriter(new OutputStreamWriter(outStream1));
			socketOutput1.println("1");//Acknowledging player 1
			socketOutput1.flush();
			System.out.println("Player 1 " + player1 + " has joined....Waiting for player 2");
			//adding player 2
			Socket dataSocket2 = connectionSocket.accept();
			inStream2 = dataSocket2.getInputStream();
			socketInput2 = new BufferedReader(new InputStreamReader(inStream2));
			String player2 = socketInput2.readLine();
			outStream2 = dataSocket2.getOutputStream();
			socketOutput2 = new PrintWriter(new OutputStreamWriter(outStream2));
			socketOutput2.println(player1);//Acknowledging player 2
			socketOutput2.flush();
			socketOutput1.println(player2);
			socketOutput1.flush();
			System.out.println("Player 2 " + player2 +" has joined....Starting the game");
			//toss
			Random rand = new Random();
			int rand_id = rand.nextInt(2) + 1;
			char rand_ch = (char)(rand.nextInt(26) + 97);
			String msg = rand_id + "," + rand_ch;
			//System.out.println(msg);
			socketOutput1.println(msg);
			socketOutput1.flush();
			socketOutput2.println(msg);
			socketOutput2.flush();
			while(1){
				
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