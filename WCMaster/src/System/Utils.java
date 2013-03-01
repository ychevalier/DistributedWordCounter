package System;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

	public static File CreateFile(String filepath) {
		File f = new File(filepath);
		f.mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e) {
			f = null;
		}
		return f;
	}
	
	public static void WriteInFile(File file, char[] textToSave, int filesize) {

	    file.delete();
	    try {
	    	BufferedWriter out = new BufferedWriter(new FileWriter(file), filesize);
	        out.write(textToSave);
	        out.close();
	    } catch (IOException e) {
	    }
	}
}