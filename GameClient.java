//Client
import java.net.*;
import java.io.*;

public class Client{

	private static int liveCount;

	static void setCount(){
		liveCount = 0;
	}

	static boolean checkCount(){
		if(liveCount == 3){
			System.out.println("Sorry, We can't bear your more Mistakes\nYou Loose");
			return true;
		}
		else
			return false;
	}

	static void reenterWord(){
		System.out.print("Re-Enter your word: ");
	}

	// static boolean checkWordValidity(String word, char letter, String signal){
	// 	boolean flag = true;
	// 	if(signal.equals("T")){
	// 		reenterWord();
	// 	}
	// }

	static boolean compareLetter(String word, char letter){
		boolean flag = false;
		if(word.charAt(0) == letter)
			flag = true;

		if(!flag){
			liveCount++;
			System.out.println("Your word begin with wrong letter \'" + word.charAt(0) + "\', Word must start with \'" + letter + "\'");
			reenterWord();
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
				String name, oppName, word, signal, message;

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

				setCount();

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

				// Toss and Starting Letter
				String[] tossAndLetter = socketInput.readLine().split(",");
				toss = Integer.parseInt(tossAndLetter[0]);
				char letter = tossAndLetter[1].charAt(0);
				
				if(id == toss){
					System.out.println("You won the toss");
					System.out.println("Start war with letter " + letter);
					System.out.print("You: ");
					do{
						if(checkCount()){
							mySocket.close();
							return;
						}
						word = input.readLine().toLowerCase();
					}while(!compareLetter(word, letter));
				}
				else{
					System.out.println("You lost the toss");
					System.out.println(oppName + " is going to start the war with letter " + letter);
				}

				// Communication Between Opponents
				do{
					while((message = socketInput.readLine()).length() == 0);
					if(message.equals("z")){
						System.out.println(oppName + " quit the game.\nYou WON, Huurrrrraaaaaaayyyyyyyyyyy");
						break;
					}

					System.out.println(oppName + " : " + message);
					letter = message.charAt(message.length()-1);

					// write message to output Stream
					System.out.print("You: ");
					// word = input.readLine();
					boolean val = true;
					do{
						if(checkCount()){
							mySocket.close();
							return;
						}

						if(!val){
							reenterWord();
						}

						word = input.readLine().toLowerCase();
						if(word.equals("z")){
							System.out.println("You quit the game in the middle of war,\nYou Loose");
							mySocket.close();
							return;
						}
						val = compareLetter(word, letter);

						if(val){
							socketOutput.println(word);
							socketOutput.flush();

							while((signal = socketInput.readLine()).length() == 0);
							if(signal.equals("T"))
								val = true;
							else{
								liveCount++;
								val = false;
							}
						}
					}while(!val);

				}while(true);
				
				// Connection Close
				mySocket.close();
				// System.out.println("Connection Terminated");
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
