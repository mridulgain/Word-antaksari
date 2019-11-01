//Dictionary check
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Dictionary{
	private Hashtable<String, Boolean> dict;
	private Hashtable<String, Boolean> stop_words;

	public Dictionary() throws FileNotFoundException{
		dict = new Hashtable<String, Boolean>();
		stop_words = new Hashtable<String, Boolean>();
    	Scanner sc = new Scanner(new File("words_alpha.txt"));
    	while(sc.hasNextLine())
    		dict.put(sc.nextLine(), false);

    	sc = new Scanner(new File("stop_words.txt"));
    	while(sc.hasNextLine())
    		stop_words.put(sc.nextLine(), false);
	}
	public boolean contains(String key){
		return dict.containsKey(key);
	}
	public boolean isCommon(String key){
		return stop_words.containsKey(key);
	}
	public void addToHistory(String key){
		dict.put(key, true);
	}
	public boolean accessedBefore(String key){
		return dict.containsKey(key) && dict.get(key);
	}
}
