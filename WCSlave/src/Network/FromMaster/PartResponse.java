package Network.FromMaster;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.Part;
import Network.Protocols.ProtocolPart;

public class PartResponse {
	
	private Socket mSocket;
	
	public PartResponse(Socket client) {
		mSocket = client;
	}

	public boolean sendResponse(Part p, boolean isReady) {
		
		boolean response = false;
		
		if(p != null && isReady) {
			response = true;
		}
		
		try {
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());
			if(response) {
				mOutput.writeBytes(ProtocolPart.SLAVE_OK
						+ ProtocolPart.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolPart.SLAVE_KO
						+ ProtocolPart.COMMON_END_LINE);
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
