//Dictionary check
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Dictionary{
	private Hashtable<String, Boolean> dict;

	public Dictionary() throws FileNotFoundException{
		dict = new Hashtable<String, Boolean>();
    	Scanner sc = new Scanner(new File("words_alpha.txt"));
    	while(sc.hasNextLine())
    		dict.put(sc.nextLine(), false);
	}
	public boolean contains(String key){
		return dict.containsKey(key);
	}
	public void addToHistory(String key){
		dict.put(key, true);
	}
	public boolean accessedBefore(String key){
		return dict.get(key);
	}
	public String toString(){
		return dict.toString();
	}
}
