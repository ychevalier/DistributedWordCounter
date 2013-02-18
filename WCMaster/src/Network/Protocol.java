package Network;

public class Protocol {

	static final char COMMON_END_LINE = '\n';
	static final char COMMON_SEPARATOR = ':';
	static final char COMMON_SPACE = ' ';
	
	// Client check if it can send a file to master.
	static final String CLIENT_CHECK_AVAILABILITY = "U_AVLBL";
	
	// Master answers.
	static final String MASTER_OK = "OK";
	static final String MASTER_KO = "KO";
	
	// Client says Goodbye.
	static final String CLIENT_CLOSE_FAILURE = "NEVERMIND";
	
	// Client sends a file to master.
	static final String CLIENT_SEND_FILE = "FILE";
	static final String CLIENT_FILE_NAME = "name";
	static final String CLIENT_FILE_SIZE = "size";
	static final String CLIENT_RESULT_IP = "result_ip";
	static final String CLIENT_RESULT_PORT = "result_port";
	
	// Master says OK or KO again.
	
	// Master sends results.
	static final String MASTER_SEND_RESULTS = "RESULTS";
	static final String MASTER_FILE_NAME = "name";
	static final String MASTER_RESULTS_SIZE = "size";
	
	// Client Ack of results.
	static final String CLIENT_CLOSE_SUCCESS = "THANKS";
	
}
