package System;

public class Config {

	public static int MASTER_PORT;// = 4545;
	
	public static int MASTER_REGISTER_PORT;// = 4646;
	
	public static final String MASTER_PATH = "master/";
	
	public static final String FILE_PATH = MASTER_PATH + "files_to_split/";
	
	public static final String PART_PATH = MASTER_PATH + "file_parts/";
	
	public static final String RESULT_PATH = MASTER_PATH + "result_parts/";
	
	public static final String FINAL_RESULT = MASTER_PATH + "results/";
	
	public static final int RESULT_MIN_PORT_NUMBER = 2222;
	public static final int RESULT_MAX_PORT_NUMBER = 4444;
	
	public static String MY_IP;
	
	public static final int NETWORK_BUFFER_SIZE = 2048;

}
