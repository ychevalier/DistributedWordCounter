package Model;

import java.util.Map;
import Network.Protocol;
import System.Config;

import Application.WCServerApp;
import Exceptions.InvalidQueryException;

public class Query {
	
	private String mFilename;
	
	private String mFilePath;

	private String mResultIP;
	
	private int mResultPort;
	
	private int mFileSize;

	public Query(String filename, String filePath, String resultIP,
			int resultPort, int fileSize) {
		super();
		this.mFilename = filename;
		this.mFilePath = filePath;
		this.mResultIP = resultIP;
		this.mResultPort = resultPort;
		this.mFileSize = fileSize;
	}
	
	public Query(Map<String, String> params) throws InvalidQueryException {
		String tmp;
		
		// File Name.
		tmp = params.get(Protocol.CLIENT_FILE_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidQueryException();
		}
		
		this.mFilename = tmp;
		
		// File Path.
		this.mFilePath = Config.FILE_PATH + this.mFilename + '_' + WCServerApp.IncrementFileCount() + ".txt";
		
		// Result IP.
		tmp = params.get(Protocol.CLIENT_RESULT_IP);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidQueryException();
		}
		this.mResultIP = tmp;
		
		// Result Port.
		tmp = params.get(Protocol.CLIENT_RESULT_PORT);
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
		tmp = params.get(Protocol.CLIENT_FILE_SIZE);
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

	public String getmFilename() {
		return mFilename;
	}

	public void setmFilename(String mFilename) {
		this.mFilename = mFilename;
	}

	public String getmFilePath() {
		return mFilePath;
	}

	public void setmFilePath(String mFilePath) {
		this.mFilePath = mFilePath;
	}

	public String getmResultIP() {
		return mResultIP;
	}

	public void setmResultIP(String mResultIP) {
		this.mResultIP = mResultIP;
	}

	public int getmResultPort() {
		return mResultPort;
	}

	public void setmResultPort(int mResultPort) {
		this.mResultPort = mResultPort;
	}

	public int getmFileSize() {
		return mFileSize;
	}

	public void setmFileSize(int mFileSize) {
		this.mFileSize = mFileSize;
	}
}