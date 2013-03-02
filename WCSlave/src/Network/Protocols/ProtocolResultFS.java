package Network.Protocols;

public class ProtocolResultFS extends Protocol {
		
	// ======================
	// Slave to Master
	// ======================
	
	// Slave Sends result.
	public static final int SLAVE_MAX_ARG_NUMBER = 2;
	public static final String SLAVE_SEND_RESULT = "RESULTFS";
	public static final String SLAVE_PART_NAME = "name";
	public static final String SLAVE_RESULT_SIZE = "size";
	
	// Master answers.
	public static final String MASTER_OK = "OK";
	public static final String MASTER_KO = "KO";

}
