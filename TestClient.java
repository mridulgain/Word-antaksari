import java.net.*;
import java.io.*;
public class TestClient{
	public static void main(String args[]){
		String msg, op_name, id, state;
		try{
			MyStreamSocket socket  = new MyStreamSocket("localhost", 2000);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			// read from socket
			socket.sendMessage(input.readLine());//say my name
			msg = socket.receiveMessage();//id
			if(msg.equals("1")){
				id ="1";
				op_name = socket.receiveMessage();
			}
			else{
				op_name = msg;
				id = "2";
			}
			System.out.println(id);
			String[] temp_msg = socket.receiveMessage().split(",");
			System.out.println(temp_msg[0]+","+temp_msg[1]);
			String toss_result = temp_msg[0];

			if(id.equals(toss_result)){
				System.out.println("I winn");
				state = "active";
			}
			else{
				System.out.println("I loose");
				state = "passive";
			}
			while(true){
				if(state.equals("active")){
					socket.sendMessage(input.readLine());//give a word
					String result = socket.receiveMessage();
					System.out.println(result);
					if(result.split(",")[0].equals("T"))
						state = "passive";
				}
				else{
					String oponentsMove = socket.receiveMessage();
					System.out.println(oponentsMove);
					state = "active";
				}
			}
			//socket.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}