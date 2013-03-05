package Network.FromMaster;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidPartException;
import Model.Part;
import Network.Protocols.ProtocolPart;
import System.Utils;

public class PartAnalyzer {
	
	private Socket mSocket;
	
	public PartAnalyzer(Socket client) {
		mSocket = client;
	}

	public Part handleQuery() {	
			
		Part result = null;
		String masterQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();
		boolean isSuccess = true;

		try {
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());
			masterQuery = mInput.readLine();

			if (masterQuery != null
					&& masterQuery.equals(ProtocolPart.MASTER_SEND_PART)) {

				// Go through arguments.
				for (int i = 0; i < ProtocolPart.MASTER_MAX_ARG_NUMBER; i++) {
					masterQuery = mInput.readLine();
					if (masterQuery != null && !masterQuery.isEmpty()) {
						String[] line = masterQuery.split("\\"
								+ ProtocolPart.COMMON_SEPARATOR);
						
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
					result = new Part(aMap);
					if(result.getPartSize() != 0) {
						char[] content = new char[result.getPartSize() + 1];
						mInput.read(content, 0, content.length);
						
						File f = Utils.CreateFile(result.getPartPath());
						if(f == null) {
							isSuccess = false;
						}
						Utils.WriteInFile(f, content, result.getPartSize());
					} else {
						isSuccess = false;
					}
					
				} catch(InvalidPartException e) {
					isSuccess = false;
				}
				
			}
			
			
			
			if(isSuccess) {
				mOutput.writeBytes(ProtocolPart.SLAVE_OK
						+ ProtocolPart.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolPart.SLAVE_KO
						+ ProtocolPart.COMMON_END_LINE);
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
