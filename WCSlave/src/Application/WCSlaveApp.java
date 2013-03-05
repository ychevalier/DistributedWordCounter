package Application;

import Network.FromMaster.PartServer;
import System.Config;
import System.Utils;


public class WCSlaveApp {
	
	static int mFileCounter = 0;

    public static synchronized int getNextFileCount() {
    	return mFileCounter++;
    }
    
    static void initConfigPath() {
    	Config.JUNK_PATH = "slave_" + Config.SLAVE_PORT + "_tmp_files/";
    	Config.FILE_PATH = Config.JUNK_PATH + "files_to_process/";
    	Config.RESULT_PATH = Config.JUNK_PATH + "processed_files/";
    	
    }
    
    static boolean simpleCliParser(String [] args) {
    	
    	boolean isError = false;
    	
    	if(args.length != 6) {
    		isError = true;
    	} else {	
	    	for(int x = 0; x < args.length; x++){
	    	    if("-p".equals(args[x])){
	    	    	try {
	    	            Config.SLAVE_PORT = Integer.parseInt(args[x + 1]);
	    	            initConfigPath();
	    	            //Config.JUNK_PATH = "2";
	    	            //System.out.println("Oh");
	    	        } catch (NumberFormatException e) {
	    	            System.out.println("Local port must be an integer.");
	    	            isError = true;
	    	            break;
	    	        }
	    	        x++;
	    	    } else if("-ma".equals(args[x])){
	    	        Config.MASTER_IP = args[x + 1];
	    	        
	    	        if(!Utils.checkIP(Config.MASTER_IP)) {
	    	        	System.out.println("Remote master IP is incorrect.");
	    	        	isError = true;
	    	        	break;
	    	        }	        
	    	        x++;
	    	    } else if("-mp".equals(args[x])){
	    	    	try {
	    	            Config.MASTER_PORT = Integer.parseInt(args[x + 1]);
	    	        } catch (NumberFormatException e) {
	    	            System.out.println("Remote master port must be an integer.");
	    	            isError = true;
	    	            break;
	    	        }
	    	        x++;
	    	    } else {
	    	    	isError = true;
	    	    	System.out.println("Wrong argument.");
	    	    	break;
	    	    }
	    	}
    	}

    	if(isError) {
    		System.out.println("Usage: COMMAND -p <port_where_to_listen> -ma <remote_master_ip> -mp <remote_master_port>");
    	}
    	
    	return !isError;
    }
	
	public static void main(String[] args) {
		
		if(!simpleCliParser(args)) {
			return;
		}
		
		PartServer server = new PartServer();
		System.out.println("### Slave ###");
		server.launch();
	}

}
