package Network.ToMaster;

import java.io.File;

import Network.TCPClient;
import Network.Protocols.ProtocolResultFS;

public class ResultSender {
	
	private boolean mIsConnected;
	private TCPClient mNetClient;
	
	public ResultSender() {
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
	
	public boolean sendResult(String partName, String filepath) {
		
		if(!mIsConnected
				|| partName == null 
				|| partName.isEmpty()
				|| filepath == null
				|| filepath.isEmpty()) {
			
			return false;
		}
		
		File fileToSend = new File(filepath);
		if(!fileToSend.exists()) {
			return false;
		}
		long fileSize = fileToSend.length();
		
		final StringBuilder query = new StringBuilder();
		
		query.append(ProtocolResultFS.SLAVE_SEND_RESULT);
		query.append(ProtocolResultFS.COMMON_END_LINE);
		query.append(ProtocolResultFS.SLAVE_PART_NAME);
		query.append(ProtocolResultFS.COMMON_SEPARATOR);
		query.append(partName);
		query.append(ProtocolResultFS.COMMON_END_LINE);
		query.append(ProtocolResultFS.SLAVE_RESULT_SIZE);
		query.append(ProtocolResultFS.COMMON_SEPARATOR);
		query.append(String.valueOf(fileSize));
		query.append(ProtocolResultFS.COMMON_END_LINE);
		query.append(ProtocolResultFS.COMMON_END_LINE);
		
		final StringBuilder footer = new StringBuilder(ProtocolResultFS.COMMON_END_LINE);
		
		final String response = mNetClient.sendFile(query.toString(), fileToSend, footer.toString());
		
		if(ProtocolResultFS.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}
