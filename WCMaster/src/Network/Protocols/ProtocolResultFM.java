package Network.Protocols;

public class ProtocolResultFM extends Protocol {
		
	// ======================
	// Master to Client
	// ======================
	
	// Master Sends result.
	public static final int MASTER_MAX_ARG_NUMBER = 2;
	public static final String MASTER_SEND_RESULT = "RESULTFM";
	public static final String MASTER_FILE_NAME = "name";
	public static final String MASTER_RESULT_SIZE = "size";
	
	// Client answers.
	public static final String CLIENT_OK = "OK";
	public static final String CLIENT_KO = "KO";
	
	public static final char WORD_COUNT_SEPARATOR = ' ';

}
