package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Network.Protocols.ProtocolResultFM;
import Network.Protocols.ProtocolResultFS;
import System.Utils;

public class WordList {

	protected Map<String, Integer> mMap;

	public WordList() {
		mMap = new HashMap<String, Integer>();
	}

	public /*synchronized*/ void addWord(String word, int number) {
		if (mMap.containsKey(word)) {
			mMap.put(word, mMap.get(word) + number);
		} else {
			mMap.put(word, number);
		}
	}

	public Map<String, Integer> getMap() {
		return mMap;
	}

	public void toFile(String path) {
		// char[] s = toString().toCharArray();
		File f = Utils.CreateFile(path);
		f.delete();

		if (f != null) {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(f));

				for (Map.Entry<String, Integer> e : mMap.entrySet()) {
					StringBuilder content = new StringBuilder();
					content.append(e.getKey());
					content.append(ProtocolResultFM.WORD_COUNT_SEPARATOR);
					content.append(e.getValue());
					content.append(ProtocolResultFM.COMMON_END_LINE);
					
					out.write(content.toString(), 0, content.length());
				}
	
				out.flush();
				out.close();

			} catch (IOException e) {
				// e.printStackTrace();
			}

		}
	}

	public String toString() {
		StringBuilder content = new StringBuilder();
		for (Map.Entry<String, Integer> e : mMap.entrySet()) {
			content.append(e.getKey());
			content.append(ProtocolResultFM.WORD_COUNT_SEPARATOR);
			content.append(e.getValue());
			content.append(ProtocolResultFS.COMMON_END_LINE);
		}
		return content.toString();
	}

}
