package Network.Protocols;

public class ProtocolRegister extends Protocol {
	
	// ======================
	// Register Slave to Master
	// ======================
	
	// Slave Sends result.
	public static final int SLAVE_MAX_ARG_NUMBER = 2;
	public static final String SLAVE_UNREGISTER = "UNREGISTER";
	public static final String SLAVE_REGISTER = "REGISTER";
	public static final String SLAVE_IP = "ip";
	public static final String SLAVE_PORT = "port";
	
	// Master answers.
	public static final String MASTER_OK = "OK";
	public static final String MASTER_KO = "KO";
	
}
