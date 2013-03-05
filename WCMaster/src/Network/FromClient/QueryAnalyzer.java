package Network.FromClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidQueryException;
import Model.Query;
import Network.Protocols.ProtocolQuery;
import System.Utils;

public class QueryAnalyzer {
	
	private Socket mSocket;
	private int mJobId;
	
	public QueryAnalyzer(Socket client, int jobId) {
		mSocket = client;
		mJobId = jobId;
	}

	public Query handleQuery() {	
			
		Query result = null;
		String clientQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();

		try {
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));
			
			clientQuery = mInput.readLine();

			if (clientQuery != null
					&& clientQuery.equals(ProtocolQuery.CLIENT_SEND_FILE)) {
				
				boolean isSuccess = true;
				// Go through arguments.
				for (int i = 0; i < ProtocolQuery.CLIENT_MAX_ARG_NUMBER; i++) {
					
					clientQuery = mInput.readLine();
					
					if (clientQuery != null && !clientQuery.isEmpty()) {
						String[] line = clientQuery.split("\\"
								+ ProtocolQuery.COMMON_SEPARATOR);
						
						// If it doesn't fit the protocol.
						if (line.length != 2) {
							isSuccess = false;
							break;
						}
						
						// Otherwise Get the arguments.
						aMap.put(line[0], line[1]);
					} else {
						// Finish! Let's start to read the file. see below.
						break;
					}
				}
				
				if(isSuccess) {
					result = new Query(aMap, mJobId);
					if(result.getFileSize() != 0) {
						// +1 to get the last character...
						char[] content = new char[result.getFileSize() + 1];
						mInput.read(content, 0, content.length);
						
						File f = Utils.CreateFile(result.getFilePath());
						if(f == null) {
							result = null;
						} else {
							Utils.WriteInFile(f, content, result.getFileSize());
						}
					} else {
						result = null;
					}
				}
				
			} /*else if(clientQuery != null
					&& clientQuery.equals(ProtocolQuery.CLIENT_CHECK_AVAILABILITY)){
				result = new Query();
			} */else {
				result = null;
			}
			// Closing and quitting this client.
			//mInput.;
			
			mSocket.shutdownInput();
			
		} catch (IOException e) {
			//e.printStackTrace();
			result = null;
		} catch (InvalidQueryException e) {
			result = null;
			//e.printStackTrace();
		}
		
		return result;
	}

}
