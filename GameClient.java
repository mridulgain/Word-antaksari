//Client
import java.net.*;
import java.io.*;

public class GameClient{
	boolean compareLetter(String word){
		int flag = 1;
		if(word.charAt(0) == letter.charAt(0))
			flag = 0;

		if(flag==1){
			System.out.println("Your word begin with wrong letter \'" + word.charAt(0) + "\', Word must start with \'" + letter + "\'");
			System.out.print("Re-Enter you word: ");
		}
		return flag;
	}

	public static void main(String args[]){
		if(args.length != 1){
			System.out.println("Invalid Number of arguments: only one argument <IP Address> is required");
		}
		else{
			try{
				InetAddress acceptorHost = InetAddress.getByName(args[0]);
				int port = 2000;
				int id, toss, flag;
				String name, oppName, word;

				Socket mySocket = new Socket(acceptorHost, port);
				System.out.println("Connection to Server...");
				// Thread.currentThread().sleep(1000);

				// user input
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

				// read from socket
				InputStream inStream = mySocket.getInputStream();
				BufferedReader socketInput = new BufferedReader(new InputStreamReader(inStream));

				// write into the socket
				OutputStream outStream = mySocket.getOutputStream();
				PrintWriter socketOutput = new PrintWriter(new OutputStreamWriter(outStream));

				// ask user for name
				System.out.print("Enter your Name: ");
				name = input.readLine();
				socketOutput.println(name);
				socketOutput.flush();

				// For Connection Established
				String c = socketInput.readLine();
				if(c.equals("1")){
					id = 1;
					System.out.println("Waiting for Player 2...");
					while((oppName = socketInput.readLine()).length() == 0);
					System.out.println("Your word war is against " + oppName);
				}
				else{
					id = 2;
					oppName = c;
					System.out.println("Your word war is against " + oppName);
				}

				String[] tossAndLetter = socketInput.readLine().split(",");
				toss = Integer.parseInt(tossAndLetter[0]);
				String letter = tossAndLetter[1];
				
				if(id == toss){
					System.out.println("You won the toss");
					System.out.println("Start war with letter " + letter);
					System.out.print("You: ");
					do{
						word = input.readLine().toLowerCase();
					}while(compareLetter(word, letter));

				}
				else{
					System.out.println("You lost the toss");
					System.out.println(oppName + " is going to start the war with letter " + letter);
				}

				/*do{
					while((message = socketInput.readLine()).length() == 0);
					if(message.equals("z")){
						System.out.println(oppName + " quit the game.\nYou WON, Huurrrrraaaaaaayyyyyyyyyyy");
						break;
					}

					System.out.println(oppName + " : " + message);

					// write message to output Stream
					System.out.print("You: ");
					word = input.readLine();
					do{
						word = input.readLine().toLowerCase();
					}while(compareLetter(word, letter));

					socketOutput.println(word);
					socketOutput.flush();

					if(word.equals("z")){
						break;
					}

				}while(true);*/
				
				mySocket.close();
				System.out.println("Connection Terminated");
			}
			catch(SocketException e){
				System.out.println("Wrong IP format");
			}

			catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
