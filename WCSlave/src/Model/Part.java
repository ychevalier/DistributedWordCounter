package Model;

import java.util.Map;
import Network.Protocols.ProtocolPart;
import System.Config;

import Application.WCSlaveApp;
import Exceptions.InvalidPartException;

public class Part {
	
	private String mPartName;
	
	private String mPartPath;

	private String mResultIP;
	
	private int mResultPort;
	
	private int mPartSize;
	
	private int mTimeout;
	
	public Part(Map<String, String> params) throws InvalidPartException {
		String tmp;
		
		// Part Name.
		tmp = params.get(ProtocolPart.MASTER_PART_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		
		this.mPartName = tmp;
		
		// Part Path.
		this.mPartPath = Config.FILE_PATH + this.mPartName + '_' + WCSlaveApp.IncrementFileCount();
		
		// Result IP.
		tmp = params.get(ProtocolPart.MASTER_RESULT_IP);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		this.mResultIP = tmp;
		
		// Result Port.
		tmp = params.get(ProtocolPart.MASTER_RESULT_PORT);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidPartException();
		}
		this.mResultPort = tmpInt;
		
		// Part Size.
		tmp = params.get(ProtocolPart.MASTER_PART_SIZE);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidPartException();
		}
		this.mPartSize = tmpInt;
		
		// Timeout.
		tmp = params.get(ProtocolPart.MASTER_LIFE_TIMEOUT);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidPartException();
		}
		this.mTimeout = tmpInt;
		
	}

	public String getPartName() {
		return mPartName;
	}

	public String getPartPath() {
		return mPartPath;
	}

	public String getResultIP() {
		return mResultIP;
	}

	public int getResultPort() {
		return mResultPort;
	}

	public int getPartSize() {
		return mPartSize;
	}
	
	public int getTimeout() {
		return mTimeout;
	}
}