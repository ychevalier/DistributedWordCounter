package Network.ToSlave;

import java.io.File;

import Network.TCPClient;
import Network.Protocols.ProtocolPart;
import System.Utils;

public class PartSender {
	
	private boolean mIsConnected;
	private TCPClient mNetClient;
	
	public PartSender() {
		mIsConnected = false;
		mNetClient = new TCPClient();
	}
	
	public boolean connect(String ip, int port) {
		if(!mIsConnected) {
			if(mNetClient == null || !mNetClient.connect(ip, port)) {
				return false;
			}
			mIsConnected = true;
		}
		return true;
	}
	
	public void disconnect() {
		if(mIsConnected && mNetClient != null) {
				mNetClient.disconnect();
				mIsConnected = false;
		}
	}
	
	public boolean checkAvailability() {
		if(!mIsConnected) {
			return false;
		}
		
		final StringBuilder toSend = new StringBuilder();
		final String response;
		
		toSend.append(ProtocolPart.MASTER_CHECK_AVAILABILITY);
		toSend.append(ProtocolPart.COMMON_END_LINE);
		
		response = mNetClient.sendData(toSend.toString());
		
		if(ProtocolPart.SLAVE_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sendFile(String filename, String filepath, String resultIP, int resultPort, int timeout) {
		
		if(!mIsConnected
				|| filename == null 
				|| filename.isEmpty()
				|| filepath == null
				|| filepath.isEmpty()
				|| !Utils.checkIP(resultIP)) {
			
			return false;
		}
		
		File fileToSend = new File(filepath);
		if(!fileToSend.exists()) {
			return false;
		}
		long fileSize = fileToSend.length();
		
		final StringBuilder query = new StringBuilder();
		
		query.append(ProtocolPart.MASTER_SEND_PART);
		query.append(ProtocolPart.COMMON_END_LINE);
		query.append(ProtocolPart.MASTER_PART_NAME);
		query.append(ProtocolPart.COMMON_SEPARATOR);
		query.append(filename);
		query.append(ProtocolPart.COMMON_END_LINE);
		query.append(ProtocolPart.MASTER_PART_SIZE);
		query.append(ProtocolPart.COMMON_SEPARATOR);
		query.append(String.valueOf(fileSize));
		query.append(ProtocolPart.COMMON_END_LINE);
		query.append(ProtocolPart.MASTER_RESULT_IP);
		query.append(ProtocolPart.COMMON_SEPARATOR);
		query.append(resultIP);
		query.append(ProtocolPart.COMMON_END_LINE);
		query.append(ProtocolPart.MASTER_RESULT_PORT);
		query.append(ProtocolPart.COMMON_SEPARATOR);
		query.append(String.valueOf(resultPort));
		query.append(ProtocolPart.COMMON_END_LINE);
		query.append(ProtocolPart.MASTER_RESULT_PORT);
		query.append(ProtocolPart.COMMON_SEPARATOR);
		query.append(String.valueOf(timeout));
		query.append(ProtocolPart.COMMON_END_LINE);
		query.append(ProtocolPart.COMMON_END_LINE);
		
		final StringBuilder footer = new StringBuilder(ProtocolPart.COMMON_END_LINE);
		
		final String response = mNetClient.sendFile(query.toString(), fileToSend, footer.toString());
		
		if(ProtocolPart.SLAVE_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}
