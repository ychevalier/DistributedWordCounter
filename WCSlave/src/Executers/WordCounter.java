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
		
		try {
			Scanner sc = new Scanner(new File(mInputFile));
			String sCurrentLine;
			while (sc.hasNextLine()) {
				sCurrentLine = sc.nextLine();
				StringBuilder currentString = new StringBuilder();
				for (int i = 0; i < sCurrentLine.length(); i++) {
					if ((sCurrentLine.charAt(i) >= 'a' && sCurrentLine.charAt(i) <= 'z')
							|| (sCurrentLine.charAt(i) >= 'A' && sCurrentLine.charAt(i) <= 'Z')) {
						currentString.append(sCurrentLine.charAt(i));
					} else if(currentString.length() != 0){
						if (index.containsKey(currentString.toString())) {
							index.put(currentString.toString(),
									index.get(currentString.toString()) + 1);
						} else {
							index.put(currentString.toString(), 1);
						}
						currentString.setLength(0);
					}
				}
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		System.out.println("Finished Processing");
		
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
