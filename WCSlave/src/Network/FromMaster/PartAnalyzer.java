package Network.FromMaster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidPartException;
import Model.Part;
import Network.Protocols.ProtocolPart;
import System.Config;
import System.Utils;

public class PartAnalyzer {
	
	private Socket mSocket;
	private int mJobId;
	
	public PartAnalyzer(Socket client, int jobId) {
		mSocket = client;
		mJobId = jobId;
	}

	public Part handlePart() {	
			
		Part result = null;
		String masterQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();
		boolean isSuccess = true;

		try {
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));
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
				
				if(isSuccess) {

					result = new Part(aMap, mJobId);
					if(result.getPartSize() != 0) {
						
						File f = Utils.CreateFile(result.getPartPath());
						if(f == null) {
							result = null;
						} else {
							
							int filesize = result.getPartSize() + 1;
							char[] content = new char[Config.NETWORK_BUFFER_SIZE];
							
							f.delete();
							BufferedWriter out = null;

							out = new BufferedWriter(new FileWriter(f));
							
							int count = 0;
							while ((count = mInput.read(content)) > 0 && filesize > 0)
							{
							  out.write(content, 0, count);
							  filesize -= count;
							}

							out.flush();
							out.close();
						}
					} else {
						result = null;
					}
				}
				
			} /* else if(masterQuery != null
					&& masterQuery.equals(ProtocolPart.MASTER_CHECK_AVAILABILITY)){
				result = new Part();
			} */else {
				result = null;
			}
			
			// Closing and quitting this client.
			//mInput.close();
			mSocket.shutdownInput();
			
		} catch (IOException e) {
			//e.printStackTrace();
			result = null;
		} catch (InvalidPartException e) {
			//e.printStackTrace();
			result = null;
		}
		return result;
	}

}
