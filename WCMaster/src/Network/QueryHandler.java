package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidQueryException;
import Model.Query;
import System.Utils;

public class QueryHandler {
	
	private Socket mSocket;
	
	public QueryHandler(Socket client) {
		mSocket = client;
	}

	public Query handleQuery() {	
			
		Query result = null;
		String clientQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();
		boolean isSuccess = true;

		try {
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());
			clientQuery = mInput.readLine();

			if (clientQuery != null
					&& clientQuery.equals(Protocol.CLIENT_SEND_FILE)) {

				// Go through arguments.
				for (int i = 0; i < Protocol.CLIENT_MAX_ARG_NUMBER; i++) {
					clientQuery = mInput.readLine();
					if (clientQuery != null && !clientQuery.isEmpty()) {
						String[] line = clientQuery.split("\\"
								+ Protocol.COMMON_SEPARATOR);
						
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
				
			} else {
				isSuccess = false;
			}

			if (isSuccess) {
				try {
					result = new Query(aMap);
					
					char[] content = new char[result.getmFileSize()];
					mInput.read(content, 0, result.getmFileSize());
					
					File f = Utils.CreateFile(result.getmFilePath());
					if(f == null) {
						isSuccess = false;
					}
					Utils.WriteInFile(f, content, result.getmFileSize());	
					
				} catch(InvalidQueryException e) {
					isSuccess = false;
				}
				
			}
			
			
			
			if(isSuccess) {
				mOutput.writeBytes(Protocol.MASTER_OK
						+ Protocol.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(Protocol.MASTER_KO
						+ Protocol.COMMON_END_LINE);
			}
			
			// Closing and quitting this client.
			mOutput.flush();
			mOutput.close();
			mInput.close();
			
			mSocket.close();
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		
		return result;
	}

}