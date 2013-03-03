package Network.FromServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidResultFMException;
import Model.ResultFM;
import Network.Protocols.ProtocolResultFM;
import System.Utils;

public class ResultAnalyzer {
	
private Socket mSocket;
	
	public ResultAnalyzer(Socket client) {
		mSocket = client;
	}

	public ResultFM handleResult() {	
			
		ResultFM result = null;
		String slaveQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();
		boolean isSuccess = true;

		try {
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());
			slaveQuery = mInput.readLine();

			if (slaveQuery != null
					&& slaveQuery.equals(ProtocolResultFM.MASTER_SEND_RESULT)) {
				

				// Go through arguments.
				for (int i = 0; i < ProtocolResultFM.MASTER_MAX_ARG_NUMBER; i++) {
					
					
					slaveQuery = mInput.readLine();
					if (slaveQuery != null && !slaveQuery.isEmpty()) {
						String[] line = slaveQuery.split("\\"
								+ ProtocolResultFM.COMMON_SEPARATOR);
						
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
					result = new ResultFM(aMap);
					
					// +1 to get the last character...
					char[] content = new char[result.getResultSize() + 1];
					mInput.read(content, 0, content.length);
					
					File f = Utils.CreateFile(result.getResultPath());
					if(f == null) {
						isSuccess = false;
					}
					Utils.WriteInFile(f, content, result.getResultSize());
					
				} catch(InvalidResultFMException e) {
					isSuccess = false;
				}
				
			}
			
			if(isSuccess) {
				mOutput.writeBytes(ProtocolResultFM.CLIENT_OK
						+ ProtocolResultFM.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolResultFM.CLIENT_KO
						+ ProtocolResultFM.COMMON_END_LINE);
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