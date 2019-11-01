import java.net.*;
import java.io.*;

public class GameClient{

	private static int mistakeCount;
	private static String oppName;

	static void setCount(){
		mistakeCount = 3;
	}

	static boolean checkCount(){
		if(mistakeCount == 0){
			System.out.println("Sorry, We can't bear your more Mistakes\nYou Lose");
			return true;
		}
		else
			return false;
	}
 
	static boolean compareLetter(String word, char letter){
		boolean flag = false;
		if(word.charAt(0) == letter)
			flag = true;

		if(!flag){
			mistakeCount--;
			System.out.println("Your word begin with wrong letter \'" + word.charAt(0) + "\', Word must start with \'" + letter + "\'");
		}
		return flag;
	}

	static boolean checkValidity(String[] signal){
		if(signal[0].equals("T")){
			System.out.println("Your Score: " + signal[1] + " " + oppName + ": " + signal[2] + " Lives remaining: " + mistakeCount);
			return true;
		}
		else if(signal[0].equals("F")){
			System.out.println("Invalid Word");
			mistakeCount--;
			System.out.println("Lives remaining: " + mistakeCount);
		}
		else if(signal[0].equals("R")){
			System.out.println("Word is repeated, Please enter new word");
		}
		else if(signal[0].equals("C")){
			System.out.println("the word you entered is very common, please try again");
		}
		return false;
	}

	static void rules(){
		String rule = "\nWelcome to the WAR WITH WORDS (WWW)\n\n" + 
						"Game Rules are:\n" +
						"* 1. This is 2 Player Game.\n" + 
						"* 2. Each Player have only 3 Lives.\n" + 
						"* 3. Game Score is calculated in terms of word length, after 10 moves winner will be decided.\n" + 
						"* 4. Lives will be deducted for following mistakes:\n" + 
						"*   \t- Word start with different letter from the given letter\n" + 
						"*   \t- Word length less than 3.\n" + 
						"*   \t- If word does\'t exists in Dictionary.\n" + 
						"* 5. Warning will be given for following mistakes:\n" + 
						"*   \t- For repeated word.\n" + 
						"*   \t- For some common words like I, we, the...\n" + 
						"* 6. You can give up in the middle of war by sending \'q\', but in that case opponent will be declared as winner.";
		System.out.println(rule);
	}

	public static void main(String args[]){
		if(args.length != 1){
			System.out.println("Invalid Number of arguments: only one argument <IP Address> is required");
		}
		else{
			try{
				int port = 2000;
				int id, toss, flag;
				String name, word, signal, message, msg;
				boolean val = true;
				String[] str;

				MyStreamSocket mySocket = new MyStreamSocket(args[0], port);
				rules();

				// user input
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

				// ask user for name
				System.out.print("\nEnter your Name: ");
				name = input.readLine();
				mySocket.sendMessage(name);

				setCount();

				// Connection Established signal
				String[] c = mySocket.receiveMessage().split(",");
				id = Integer.parseInt(c[0]);
				oppName = c[1];
				System.out.println("Your word war is against " + oppName);

				// Toss and Starting Letter
				String[] tossAndLetter = mySocket.receiveMessage().split(",");
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
						val = compareLetter(word, letter);

						if(val){
							mySocket.sendMessage(word);

							signal = mySocket.receiveMessage();
							str = signal.split(",");
							val = checkValidity(str);
						}
					}while(!val);
				}
				else{
					System.out.println("You lost the toss");
					System.out.println(oppName + " is going to start the war with letter " + letter);
				}

				// Communication Between Opponents
				do{
					msg = mySocket.receiveMessage();
					str = msg.split(",");
					if(str[0].equals("W")){
						System.out.println("You Win the War\nYour Score : " + str[1] + " " + oppName + " score: " + str[2]);
						mySocket.close();
						return;
					}
					else if(str[0].equals("L")){
						System.out.println("You Lost the War\nYour Score : " + str[1] + " " + oppName + " score: " + str[2]);
						mySocket.close();
						return;
					}
					else if(str[0].equals("D")){
						System.out.println("Game Draw, both have same score\nYour Score : " + str[1] + " " + oppName + " score: " + str[2]);
						mySocket.close();
						return;
					}

					message = str[0];
					if(message.equals("q")){
						System.out.println(oppName + " quit the game.\nYou WON, Huurrrrraaaaaaayyyyyyyyyyy");
						break;
					}

					System.out.println("Your Score: " + str[1] + " " + oppName + ": " + str[2] + " Lives remaining: " + mistakeCount);
					System.out.println(oppName + " : " + message);
					letter = message.charAt(message.length()-1);
					System.out.println("Your letter is " + letter);

					// write message to output Stream
					System.out.print("You: ");
					
					do{
						if(checkCount()){
							mySocket.close();
							return;
						}

						if(!val){
							System.out.print("Re-Enter your word: ");
						}

						word = input.readLine().toLowerCase();
						if(word.equals("q")){
							System.out.println("You quit the game in the middle of war,\nYou Lost");
							mySocket.sendMessage(word);
							mySocket.close();
							return;
						}
						val = compareLetter(word, letter);

						if(val){
							mySocket.sendMessage(word);

							signal = mySocket.receiveMessage();
							str = signal.split(",");
							if(str[0].equals("W")){
								System.out.println("You Win the War\nYour Score : " + str[1] + " " + oppName + " score: " + str[2]);
								mySocket.close();
								return;
							}
							else if(str[0].equals("L")){
								System.out.println("You Lost the War\nYour Score : " + str[1] + " " + oppName + " score: " + str[2]);
								mySocket.close();
								return;
							}
							else if(str[0].equals("D")){
								System.out.println("Game Draw, both have same score\nYour Score : " + str[1] + " " + oppName + " score: " + str[2]);
								mySocket.close();
								return;
							}
							val = checkValidity(str);
						}
					}while(!val);

				}while(true);
				
				// Connection Close
				mySocket.close();
			}
			catch(SocketException e){
				System.out.println(e);
			}

			catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
