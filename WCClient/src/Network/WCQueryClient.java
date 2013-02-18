package Network;

import java.io.File;

import Utils.Utils;

public class WCQueryClient {
	
	private boolean mIsConnected;
	private TCPClient mNetClient;
	
	public WCQueryClient() {
		mIsConnected = false;
		mNetClient = new TCPClient();
	}
	
	public boolean checkAvailability() {
		if(!mIsConnected) {
			if(!mNetClient.connect()) {
				return false;
			}
			mIsConnected = true;
		}
		
		final StringBuilder toSend = new StringBuilder();
		final String response;
		
		toSend.append(Protocol.CLIENT_CHECK_AVAILABILITY);
		toSend.append(Protocol.COMMON_END_LINE);
		
		response = mNetClient.sendData(toSend.toString());
		
		if(Protocol.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sendFile(String filename, String filepath, String resultIP, int resultPort) {
		
		if(!mIsConnected) {
			if(!mNetClient.connect()) {
				return false;
			}
			mIsConnected = true;
		} else if(filename == null 
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
		
		query.append(Protocol.CLIENT_SEND_FILE);
		query.append(Protocol.COMMON_END_LINE);
		query.append(Protocol.CLIENT_FILE_NAME);
		query.append(Protocol.COMMON_SEPARATOR);
		query.append(filename);
		query.append(Protocol.COMMON_END_LINE);
		query.append(Protocol.CLIENT_FILE_SIZE);
		query.append(Protocol.COMMON_SEPARATOR);
		query.append(String.valueOf(fileSize));
		query.append(Protocol.COMMON_END_LINE);
		query.append(Protocol.CLIENT_RESULT_IP);
		query.append(Protocol.COMMON_SEPARATOR);
		query.append(resultIP);
		query.append(Protocol.COMMON_END_LINE);
		query.append(Protocol.CLIENT_RESULT_PORT);
		query.append(Protocol.COMMON_SEPARATOR);
		query.append(String.valueOf(resultPort));
		query.append(Protocol.COMMON_END_LINE);
		query.append(Protocol.COMMON_END_LINE);
		
		final StringBuilder footer = new StringBuilder(Protocol.COMMON_END_LINE);
		
		final String response = mNetClient.sendFile(query.toString(), fileToSend, footer.toString());
		
		mNetClient.disconnect();
		mIsConnected = false;
		
		if(Protocol.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}