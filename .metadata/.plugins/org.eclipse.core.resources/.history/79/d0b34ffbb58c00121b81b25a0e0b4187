package System;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static File CreateFile(String filepath) {
		File f = new File(filepath);
		//f.mkdirs();
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
	
	private static final String PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static boolean checkIP(String ip) {
		if(ip == null) {
			return false;
		} else if(ip.equals("localhost")) {
			return true;
		}
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
}
