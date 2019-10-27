import java.net.*;
import java.io.*;
public class TestClient{
	public static void main(String args[]){
		String msg;
		try{
			MyStreamSocket socket  = new MyStreamSocket("localhost", 2000);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			// read from socket
			int id = Integer.parseInt(input.readLine());
			socket.sendMessage(input.readLine());//say my name
			String id = socket.receiveMessage();//id
			System.out.println(id);
			String[] temp_msg = socket.receiveMessage().split(",");
			System.out.println(temp_msg);
			String toss_result = temp_msg[0];
			if(id.equals(toss_result)){
				System.out.println("I winn");
			}
			else{
				System.out.println("I loose");
			}
			while(true){

			}
			//socket.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}