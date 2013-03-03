package Model;

import java.util.Map;
import Network.Protocols.ProtocolPart;
import System.Config;

import Application.WCSlaveApp;
import Exceptions.InvalidPartException;

public class Part {
	
	private String mFileName;
	
	private int mPartNumber;
	
	private String mPartPath;

	private String mResultIP;
	
	private int mResultPort;
	
	private int mPartSize;
	
	private int mTimeout;
	
	public Part(Map<String, String> params) throws InvalidPartException {
		String tmp;
		
		// File Name.
		tmp = params.get(ProtocolPart.MASTER_FILE_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		
		this.mFileName = tmp;
		
		// Part Number.
		tmp = params.get(ProtocolPart.MASTER_PART_NUMBER);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidPartException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidPartException();
		}
		this.mPartNumber = tmpInt;
		
		// Part Path.
		this.mPartPath = Config.FILE_PATH + this.mFileName + this.mPartNumber + '_' + WCSlaveApp.getNextFileCount();
		
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
		tmpInt = 0;
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

	public String getFileName() {
		return mFileName;
	}
	
	public int getPartNumber() {
		return mPartNumber;
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
