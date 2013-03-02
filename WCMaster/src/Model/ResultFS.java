package Model;

import java.util.Map;

import Exceptions.InvalidResultFSException;
import Network.Protocols.ProtocolResultFS;
import System.Config;

public class ResultFS {
	
	private String mFileName;
	
	private int mPartNumber;
	
	private String mResultPath;

	private int mResultSize;
	
	public ResultFS(Map<String, String> params) throws InvalidResultFSException {
		String tmp;
		
		// File Name.
		tmp = params.get(ProtocolResultFS.SLAVE_FILE_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFSException();
		}
		
		this.mFileName = tmp;
		
		// Result Size.
		tmp = params.get(ProtocolResultFS.SLAVE_PART_NUMBER);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFSException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidResultFSException();
		}
		this.mPartNumber = tmpInt;
		
		this.mResultPath = Config.RESULT_PATH + mFileName + '/' + mPartNumber;
		
		// Result Size.
		tmp = params.get(ProtocolResultFS.SLAVE_RESULT_SIZE);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFSException();
		}
		tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidResultFSException();
		}
		this.mResultSize = tmpInt;
	}

	public String getFileName() {
		return mFileName;
	}
	
	public int getPartNumber() {
		return mPartNumber;
	}
	
	public String getResultPath() {
		return mResultPath;
	}

	public int getResultSize() {
		return mResultSize;
	}

}
