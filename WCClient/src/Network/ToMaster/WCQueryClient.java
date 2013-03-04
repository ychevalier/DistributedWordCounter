package Network.ToMaster;

import java.io.File;

import Network.TCPClient;
import Network.Protocols.ProtocolQuery;
import System.Utils;

public class WCQueryClient {
	
	private boolean mIsConnected;
	private TCPClient mNetClient;
	
	public WCQueryClient() {
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
		
		toSend.append(ProtocolQuery.CLIENT_CHECK_AVAILABILITY);
		toSend.append(ProtocolQuery.COMMON_END_LINE);
		
		response = mNetClient.sendData(toSend.toString());
		
		//System.out.println("### This is the response : " + response);
		
		if(ProtocolQuery.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sendFile(String filename, String filepath, String resultIP, int resultPort) {
		
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
		
		query.append(ProtocolQuery.CLIENT_SEND_FILE);
		query.append(ProtocolQuery.COMMON_END_LINE);
		query.append(ProtocolQuery.CLIENT_FILE_NAME);
		query.append(ProtocolQuery.COMMON_SEPARATOR);
		query.append(filename);
		query.append(ProtocolQuery.COMMON_END_LINE);
		query.append(ProtocolQuery.CLIENT_FILE_SIZE);
		query.append(ProtocolQuery.COMMON_SEPARATOR);
		query.append(String.valueOf(fileSize));
		query.append(ProtocolQuery.COMMON_END_LINE);
		query.append(ProtocolQuery.CLIENT_RESULT_IP);
		query.append(ProtocolQuery.COMMON_SEPARATOR);
		query.append(resultIP);
		query.append(ProtocolQuery.COMMON_END_LINE);
		query.append(ProtocolQuery.CLIENT_RESULT_PORT);
		query.append(ProtocolQuery.COMMON_SEPARATOR);
		query.append(String.valueOf(resultPort));
		query.append(ProtocolQuery.COMMON_END_LINE);
		query.append(ProtocolQuery.COMMON_END_LINE);
		
		final StringBuilder footer = new StringBuilder(ProtocolQuery.COMMON_END_LINE);
		
		final String response = mNetClient.sendFile(query.toString(), fileToSend, footer.toString());
		
		if(ProtocolQuery.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}
