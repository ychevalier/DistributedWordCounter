package Network.Protocols;

public class ProtocolResultFS extends Protocol {
		
	// ======================
	// Slave to Master
	// ======================
	
	// Slave Sends result.
	public static final int SLAVE_MAX_ARG_NUMBER = 3;
	public static final String SLAVE_SEND_RESULT = "RESULTFS";
	public static final String SLAVE_FILE_NAME = "name";
	public static final String SLAVE_PART_NUMBER = "part";
	public static final String SLAVE_RESULT_SIZE = "size";
	
	// Master answers.
	public static final String MASTER_OK = "OK";
	public static final String MASTER_KO = "KO";
	
	public static final char WORD_COUNT_SEPARATOR = ' ';

}
