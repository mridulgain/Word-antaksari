import java.io.FileNotFoundException;
public class TestDictionary{
	public static void main(String args[]) throws FileNotFoundException{
		Dictionary myDict = new Dictionary();
		System.out.println(myDict.contains("bad"));//true
		System.out.println(myDict.accessedBefore("beer"));//false
		myDict.addToHistory("beer");
		System.out.println(myDict.accessedBefore("beer"));//true
		System.out.println(myDict.isCommon("am"));//true
		System.out.println(myDict.isCommon("bad"));//false

		System.out.println(myDict.isCommon("asdf"));
		System.out.println(myDict.accessedBefore("asdf"));
	}
}
