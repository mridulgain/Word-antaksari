/* Wrapper for Socket class */
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class MyStreamSocket extends Socket{
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	public int score, turnCount;
	//for client
	MyStreamSocket(String acceptorHost, int acceptorPort) throws SocketException, IOException{
		this.socket = new Socket(acceptorHost, acceptorPort);
		setStreams();
	}
	//for server
	MyStreamSocket(Socket socket) throws IOException{
		this.socket = socket;
		setStreams();
	}
	private void setStreams() throws IOException{
		//input
		InputStream inStream = this.socket.getInputStream();
		this.input = new BufferedReader(new InputStreamReader(inStream));
		//output
		OutputStream  outStream = this.socket.getOutputStream();
		this.output = new PrintWriter(new OutputStreamWriter(outStream));
	}
	public void sendMessage(String message) throws IOException{
		this.output.println(message);
		this.output.flush();
	}
	public String receiveMessage() throws IOException{
		return this.input.readLine();
		/*String message;
		while((message = this.input.readLine()).length() == 0);
		return message;*/
	}
}