package Network.FromClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.Query;
import Network.Protocols.ProtocolQuery;

public class QueryResponse {
	
	private Socket mSocket;
	
	public QueryResponse(Socket client) {
		mSocket = client;
	}

	public boolean sendResponse(Query q, boolean isReady) {
		
		boolean response = false;
		
		if(q != null && isReady) {
			response = true;
		}
		
		try {
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());

			if(response) {
				mOutput.writeBytes(ProtocolQuery.MASTER_OK
						+ ProtocolQuery.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolQuery.MASTER_KO
						+ ProtocolQuery.COMMON_END_LINE);
			}

			mOutput.flush();
			//mOutput.close();
			mSocket.shutdownOutput();
		} catch (IOException e) {
			//e.printStackTrace();
			response = false;
		}
		return response;
	}
}
