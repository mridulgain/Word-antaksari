import java.io.FileNotFoundException;
public class TestDictionary{
	public static void main(String args[]) throws FileNotFoundException{
		Dictionary myDict = new Dictionary();
		System.out.println(myDict.contains("bad"));
		System.out.println(myDict.accessedBefore("beer"));
		myDict.addToHistory("beer");
		System.out.println(myDict.accessedBefore("beer"));
	}
}