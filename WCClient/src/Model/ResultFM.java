package Model;

import java.util.Map;

import Exceptions.InvalidResultFMException;
import Network.Protocols.ProtocolResultFM;
import System.Config;

public class ResultFM {
	
	private String mFileName;
	
	private String mResultPath;

	private int mResultSize;
	
	public ResultFM(Map<String, String> params) throws InvalidResultFMException {
		String tmp;
		
		// File Name.
		tmp = params.get(ProtocolResultFM.MASTER_FILE_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFMException();
		}
		
		this.mFileName = tmp;
		
		this.mResultPath = Config.RESULT_PATH + mFileName;
		
		// Result Size.
		tmp = params.get(ProtocolResultFM.MASTER_RESULT_SIZE);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFMException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidResultFMException();
		}
		this.mResultSize = tmpInt;
	}

	public String getFileName() {
		return mFileName;
	}
	
	public String getResultPath() {
		return mResultPath;
	}

	public int getResultSize() {
		return mResultSize;
	}
}
