package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

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
