package System;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	/*
	public static void WriteInFile(File file, char[] textToSave, int filesize) {

	    file.delete();
	    try {
	    	BufferedWriter out = new BufferedWriter(new FileWriter(file), filesize);
	        out.write(textToSave);
	        out.close();
	    } catch (IOException e) {
	    }
	}
	*/
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
	
	public static boolean isAvailable(int port) {
	    if (port < Config.RESULT_MIN_PORT_NUMBER || port > Config.RESULT_MAX_PORT_NUMBER) {
	        throw new IllegalArgumentException("Invalid start port: " + port);
	    }

	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	                /* should not be thrown */
	            }
	        }
	    }

	    return false;
	}

}
