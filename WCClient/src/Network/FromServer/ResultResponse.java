package Network.FromServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.ResultFM;
import Network.Protocols.ProtocolResultFM;


public class ResultResponse {
	
private Socket mSocket;
	
	public ResultResponse(Socket client) {
		mSocket = client;
	}

	public boolean sendResponse(ResultFM r) {
		
		boolean response = false;
		
		if(r != null) {
			response = true;
		}
		
		try {
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());

			if(response) {
				mOutput.writeBytes(ProtocolResultFM.CLIENT_OK
						+ ProtocolResultFM.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolResultFM.CLIENT_KO
						+ ProtocolResultFM.COMMON_END_LINE);
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