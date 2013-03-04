package Executers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import Network.Protocols.ProtocolResultFS;
import System.Utils;

public class WordCounter {
	
	private String mInputFile;
	private String mOutputFile;
	
	public WordCounter(String inputFile, String outputFile) {
		mInputFile = inputFile;
		mOutputFile = outputFile;
	}

	public void count() throws IOException {
		Map<String, Integer> index = new TreeMap<String, Integer>();
		
		System.out.println("Starting Processing...");
		
		Scanner sc2 = null;
	    try {
	        sc2 = new Scanner(new File(mInputFile));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    while (sc2.hasNextLine()) {
	            Scanner s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	            String s = s2.next();
	            if (index.containsKey(s)) {
					index.put(s, index.get(s) + 1);
				} else {
					index.put(s, 1);
				}
	        }
	    }
	    
	    StringBuilder content = new StringBuilder();
	    for(Map.Entry<String, Integer> e : index.entrySet()) {
	    	content.append(e.getKey());
	    	content.append(ProtocolResultFS.WORD_COUNT_SEPARATOR);
	    	content.append(e.getValue());
	    	content.append(ProtocolResultFS.COMMON_END_LINE);
		}
	    
	    File f = Utils.CreateFile(mOutputFile);
		if(f == null) {
			throw new IOException();
		}
		Utils.WriteInFile(f, content.toString().toCharArray(), content.length());
		
		System.out.println("Finished Processing");
	}

}
