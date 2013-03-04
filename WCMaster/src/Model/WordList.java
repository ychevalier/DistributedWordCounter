package Model;

import java.util.HashMap;
import java.util.Map;

import Network.Protocols.ProtocolResultFM;
import Network.Protocols.ProtocolResultFS;

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
	
	public String toString() {
		StringBuilder content = new StringBuilder();
		for(Map.Entry<String, Integer> e : mMap.entrySet()) {
	    	content.append(e.getKey());
	    	content.append(ProtocolResultFM.WORD_COUNT_SEPARATOR);
	    	content.append(e.getValue());
	    	content.append(ProtocolResultFS.COMMON_END_LINE);
		}
		return content.toString();
	}

}
