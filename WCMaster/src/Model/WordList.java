package Model;

import java.util.HashMap;
import java.util.Map;

public class WordList {
	
	protected Map<String, Integer> mMap;
	
	public WordList() {
		mMap = new HashMap<String, Integer>();
	}
	
	public synchronized void addWord(String word, int number) {
		if (mMap.containsKey(word)) {
			mMap.put(word, mMap.get(word) + number);
		} else {
			mMap.put(word, number);
		}
	}

}
