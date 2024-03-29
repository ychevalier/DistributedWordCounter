package Executers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Model.WordList;
import Network.Protocols.ProtocolResultFM;

public class WordCounter {

	public static void countToWordList(String inputFile, WordList list) {
		
		System.out.println("Starting Processing...");
		
		try {
			Scanner sc = new Scanner(new File(inputFile));
			String sCurrentLine;
			while (sc.hasNextLine()) {
				sCurrentLine = sc.nextLine();
				String[] line = sCurrentLine.split("\\"
						+ ProtocolResultFM.WORD_COUNT_SEPARATOR);
				if (line.length == 2) {
					if(line[0] != null && !line[0].isEmpty()) {
						list.addWord(line[0], new Integer(line[1]).intValue());
					}
					//System.out.println(line[0] + " "+ new Integer(line[1]).intValue());
				}
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		System.out.println("Finished Processing");
	}

}
