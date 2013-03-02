package Model;

import java.util.Map;

import Exceptions.InvalidResultFSException;
import Network.Protocols.ProtocolResultFS;

public class ResultFS {
	
	private String mPartName;

	private int mResultSize;
	
	public ResultFS(Map<String, String> params) throws InvalidResultFSException {
		String tmp;
		
		// File Name.
		tmp = params.get(ProtocolResultFS.SLAVE_PART_NAME);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFSException();
		}
		
		this.mPartName = tmp;
		
		// Result Size.
		tmp = params.get(ProtocolResultFS.SLAVE_RESULT_SIZE);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidResultFSException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidResultFSException();
		}
		this.mResultSize = tmpInt;
	}

	public String getPartName() {
		return mPartName;
	}

	public int getResultSize() {
		return mResultSize;
	}

}
