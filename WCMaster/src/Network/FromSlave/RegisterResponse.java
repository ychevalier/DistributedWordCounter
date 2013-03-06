package Network.FromSlave;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.Slave;
import Network.Protocols.ProtocolRegister;

public class RegisterResponse {
	
private Socket mSocket;
	
	public RegisterResponse(Socket client) {
		mSocket = client;
	}

	public boolean sendResponse(Slave s) {
		
		boolean response = false;
		
		if(s != null) {
			response = true;
		}
		
		try {
			DataOutputStream mOutput = new DataOutputStream(mSocket.getOutputStream());

			if(response) {
				mOutput.writeBytes(ProtocolRegister.MASTER_OK
						+ ProtocolRegister.COMMON_END_LINE);
			} else {
				mOutput.writeBytes(ProtocolRegister.MASTER_KO
						+ ProtocolRegister.COMMON_END_LINE);
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