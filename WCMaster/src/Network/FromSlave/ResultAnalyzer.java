package Network.FromSlave;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidResultFSException;
import Model.ResultFS;
import Network.Protocols.Protocol;
import Network.Protocols.ProtocolResultFS;

public class ResultAnalyzer {
	
private Socket mSocket;
	
	public ResultAnalyzer(Socket client) {
		mSocket = client;
	}

	public ResultFS handleResult() {	
			
		ResultFS result = null;
		String slaveQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();
		boolean isSuccess = true;

		try {
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());
			slaveQuery = mInput.readLine();
			System.out.println(slaveQuery);
			if (slaveQuery != null
					&& slaveQuery.equals(ProtocolResultFS.SLAVE_SEND_RESULT)) {
				

				// Go through arguments.
				for (int i = 0; i < ProtocolResultFS.SLAVE_MAX_ARG_NUMBER; i++) {
					
					
					slaveQuery = mInput.readLine();
					if (slaveQuery != null && !slaveQuery.isEmpty()) {
						String[] line = slaveQuery.split("\\"
								+ ProtocolResultFS.COMMON_SEPARATOR);
						
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
					result = new ResultFS(aMap);
					
					// +1 to get the last character...
					char[] content = new char[result.getResultSize() + 1];
					mInput.read(content, 0, content.length);
					
					System.out.println("This is the content: " + new String(content));
					
					// TODO???	
					
				} catch(InvalidResultFSException e) {
					isSuccess = false;
				}
				
			}
			
			if(isSuccess) {
				mOutput.writeBytes(ProtocolResultFS.MASTER_OK
						+ ProtocolResultFS.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolResultFS.MASTER_KO
						+ ProtocolResultFS.COMMON_END_LINE);
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