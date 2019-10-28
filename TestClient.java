import java.net.*;
import java.io.*;
public class TestClient{
	public static void main(String args[]){
		String msg,name,id;
		try{
			MyStreamSocket socket  = new MyStreamSocket("localhost", 2000);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			// read from socket
			socket.sendMessage(input.readLine());//say my name
			msg = socket.receiveMessage();//id
			if(msg.equals("1")){
				id ="1";
				name = socket.receiveMessage();
			}
			else{
				name = msg;
				id = "2";
			}
			System.out.println(id);
			String[] temp_msg = socket.receiveMessage().split(",");
			System.out.println(temp_msg[0]+","+temp_msg[1]);
			String toss_result = temp_msg[0];

			if(id.equals(toss_result)){
				System.out.println("I winn");
				id = "active";
			}
			else{
				System.out.println("I loose");
				id = "passive";
			}
			while(true){
				if(id.equals("active")){
					socket.sendMessage(input.readLine());//give a word
					String result = socket.receiveMessage();
					System.out.println(result);
					id = "passive";
				}
				else{
					String oponentsMove = socket.receiveMessage();
					System.out.println(oponentsMove);
					id = "active";
				}
			}
			//socket.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}