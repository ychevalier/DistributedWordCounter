package Model;

import java.util.Map;

import Network.Protocols.ProtocolQuery;
import System.Config;

import Exceptions.InvalidQueryException;

public class Query {
	
	private String mFilename;
	
	private String mFilePath;

	private String mResultIP;
	
	private int mResultPort;
	
	private int mFileSize;
	
	public Query(Map<String, String> params, int jobId) throws InvalidQueryException {
		
		String tmp;
		
		// File Name.
		tmp = params.get(ProtocolQuery.CLIENT_FILE_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidQueryException();
		}
		
		this.mFilename = tmp;
		
		// File Path.
		this.mFilePath = Config.FILE_PATH + this.mFilename + '_' + jobId;
		
		// Result IP.
		tmp = params.get(ProtocolQuery.CLIENT_RESULT_IP);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidQueryException();
		}
		this.mResultIP = tmp;
		
		// Result Port.
		tmp = params.get(ProtocolQuery.CLIENT_RESULT_PORT);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidQueryException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidQueryException();
		}
		this.mResultPort = tmpInt;
		
		// File Size.
		tmp = params.get(ProtocolQuery.CLIENT_FILE_SIZE);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidQueryException();
		}
		tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidQueryException();
		}
		this.mFileSize = tmpInt;
	}

	public String getFilename() {
		return mFilename;
	}

	public String getFilePath() {
		return mFilePath;
	}

	public String getResultIP() {
		return mResultIP;
	}

	public int getResultPort() {
		return mResultPort;
	}

	public int getFileSize() {
		return mFileSize;
	}
}
