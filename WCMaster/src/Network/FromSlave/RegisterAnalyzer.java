package Network.FromSlave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidRegisterException;
import Model.Slave;
import Network.Protocols.ProtocolRegister;

public class RegisterAnalyzer {
	private Socket mSocket;
	
	public RegisterAnalyzer(Socket client) {
		mSocket = client;
	}

	public Slave handleSlave() {	
			
		Slave result = null;
		String slaveQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();

		try {
			boolean isSuccess = true;
			
			BufferedReader mInput = new BufferedReader(
					new InputStreamReader(mSocket.getInputStream()));

			slaveQuery = mInput.readLine();
			
			if (slaveQuery != null
					&& (slaveQuery.equals(ProtocolRegister.SLAVE_REGISTER) 
							|| slaveQuery.equals(ProtocolRegister.SLAVE_UNREGISTER))) {

				boolean isARegisterQuery = slaveQuery.equals(ProtocolRegister.SLAVE_REGISTER);
				// Go through arguments.
				for (int i = 0; i < ProtocolRegister.SLAVE_MAX_ARG_NUMBER; i++) {
					slaveQuery = mInput.readLine();
					
					if (slaveQuery != null && !slaveQuery.isEmpty()) {
						String[] line = slaveQuery.split("\\"
								+ ProtocolRegister.COMMON_SEPARATOR);
						
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
					result = new Slave(aMap, isARegisterQuery);
				}
			} else {
				result = null;
			}
			
			// Closing and quitting this client.
			mSocket.shutdownInput();
			
		} catch (IOException e) {
			result = null;
		} catch(InvalidRegisterException e) {
			result = null;
		}
		
		return result;
	}
}
