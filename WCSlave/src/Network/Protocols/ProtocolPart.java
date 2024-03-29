package Network.Protocols;

public class ProtocolPart extends Protocol {
	
	// ======================
	// Master to Slave
	// ======================
	
	// Master checks if it can send a file to slave.
	public static final String MASTER_CHECK_AVAILABILITY = "U_AVLBL";
	
	// Slave answers.
	public static final String SLAVE_OK = "OK";
	public static final String SLAVE_KO = "KO";
	
	// Master Sends part.
	public static final int MASTER_MAX_ARG_NUMBER = 6;
	public static final String MASTER_SEND_PART = "PART";
	public static final String MASTER_FILE_NAME = "name";
	public static final String MASTER_PART_NUMBER = "part";
	public static final String MASTER_PART_SIZE = "size";
	public static final String MASTER_RESULT_IP = "result_ip";
	public static final String MASTER_RESULT_PORT = "result_port";
	public static final String MASTER_LIFE_TIMEOUT = "timeout";
	
	// Master says OK or KO again.
	
	// Master sends results.
	public static final String SlAVE_SEND_RESULTS = "RESULTS";
	public static final String SLAVE_PART_NAME = "name";
	public static final String SLAVE_RESULTS_SIZE = "size";
	
	// Client Ack of results.
	public static final String MASTER_CLOSE_SUCCESS = "THANKS";
	

}
