package Network.Protocols;

public class ProtocolQuery extends Protocol {
	
	// ===============
	// Client to Master
	// ===============
	
	// Client check if it can send a file to master.
	public static final String CLIENT_CHECK_AVAILABILITY = "U_AVLBL";
	
	// Master answers.
	public static final String MASTER_OK = "OK";
	public static final String MASTER_KO = "KO";
	
	// Client says Goodbye.
	public static final String CLIENT_CLOSE_FAILURE = "NEVERMIND";
	
	// Client sends a file to master.
	public static final int CLIENT_MAX_ARG_NUMBER = 4;
	public static final String CLIENT_SEND_FILE = "FILE";
	public static final String CLIENT_FILE_NAME = "name";
	public static final String CLIENT_FILE_SIZE = "size";
	public static final String CLIENT_RESULT_IP = "result_ip";
	public static final String CLIENT_RESULT_PORT = "result_port";
	
	// Master says OK or KO again.
	
	// Master sends results.
	public static final String MASTER_SEND_RESULTS = "RESULTS";
	public static final String MASTER_FILE_NAME = "name";
	public static final String MASTER_RESULTS_SIZE = "size";
	
	// Client Ack of results.
	public static final String CLIENT_CLOSE_SUCCESS = "THANKS";

}
