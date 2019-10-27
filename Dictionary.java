//Dictionary check
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Dictionary{
	private Hashtable<String, Integer> dict;
	public Dictionary() throws FileNotFoundException{
		dict = new Hashtable<String, Integer>();
    	Scanner sc = new Scanner(new File("words_alpha.txt"));
    	while(sc.hasNextLine())
    		dict.put(sc.nextLine(), 0);
	}
	public String toString(){
		return String.valueOf(dict.get("apple"));
	}
}
