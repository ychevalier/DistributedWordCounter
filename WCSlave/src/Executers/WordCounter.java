package Executers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

	public void count() {
		Map<String, Integer> index = new TreeMap<String, Integer>();

		System.out.println("Starting Processing...");

		try {
			Scanner sc = new Scanner(new File(mInputFile));
			String sCurrentLine;
			while (sc.hasNextLine()) {
				
				sCurrentLine = sc.nextLine();
				
				StringBuilder currentString = new StringBuilder();
				for (int i = 0; i < sCurrentLine.length() + 1; i++) {
					if (i != sCurrentLine.length() && ((sCurrentLine.charAt(i) >= 'a' && sCurrentLine
							.charAt(i) <= 'z')
							|| (sCurrentLine.charAt(i) >= 'A' && sCurrentLine
									.charAt(i) <= 'Z'))) {
						currentString.append(sCurrentLine.charAt(i));
						//System.out.println(currentString.toString());
					} else if (currentString.length() != 0) {
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
			System.out.println("Error when processing file...");
			return; // e.printStackTrace();
		}
		
		File f = Utils.CreateFile(mOutputFile);
		if (f != null) {
			f.delete();
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(f));
				
				for (Map.Entry<String, Integer> e : index.entrySet()) {
					StringBuilder content = new StringBuilder();
					content.append(e.getKey());
					content.append(ProtocolResultFS.WORD_COUNT_SEPARATOR);
					content.append(e.getValue());
					content.append(ProtocolResultFS.COMMON_END_LINE);

					out.write(content.toString(), 0, content.length());
					
				}

				out.flush();
				out.close();

			} catch (IOException e) {
				return;
			}
		}
		System.out.println("Finished Processing");
	}

}
