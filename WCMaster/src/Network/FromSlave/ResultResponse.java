package Network.FromSlave;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.ResultFS;
import Network.Protocols.ProtocolResultFS;

public class ResultResponse {
	
private Socket mSocket;
	
	public ResultResponse(Socket client) {
		mSocket = client;
	}

	public boolean sendResponse(ResultFS r) {
		
		boolean response = false;
		
		if(r != null) {
			response = true;
		}
		
		try {
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());

			if(response) {
				mOutput.writeBytes(ProtocolResultFS.MASTER_OK
						+ ProtocolResultFS.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolResultFS.MASTER_KO
						+ ProtocolResultFS.COMMON_END_LINE);
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
