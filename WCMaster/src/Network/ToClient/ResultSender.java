package Network.ToClient;

import Model.WordList;
import Network.TCPClient;
import Network.Protocols.ProtocolResultFM;

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
	
	
	
	public boolean sendResult(String fileName, WordList list) {
		
		if(!mIsConnected
				|| list == null) {
			
			return false;
		}
		
		String content = list.toString();
	    
		
		long fileSize = content.length();
		
		final StringBuilder query = new StringBuilder();
		
		query.append(ProtocolResultFM.MASTER_SEND_RESULT);
		query.append(ProtocolResultFM.COMMON_END_LINE);
		query.append(ProtocolResultFM.MASTER_FILE_NAME);
		query.append(ProtocolResultFM.COMMON_SEPARATOR);
		query.append(fileName);
		query.append(ProtocolResultFM.COMMON_END_LINE);
		query.append(ProtocolResultFM.MASTER_RESULT_SIZE);
		query.append(ProtocolResultFM.COMMON_SEPARATOR);
		query.append(String.valueOf(fileSize));
		query.append(ProtocolResultFM.COMMON_END_LINE);
		query.append(ProtocolResultFM.COMMON_END_LINE);
		
		final StringBuilder footer = new StringBuilder(ProtocolResultFM.COMMON_END_LINE);
		
		final String response = mNetClient.sendData(query.append(content).append(footer).toString());
		//String toSend = query.append(content).append(footer).toString();
		//final String response = mNetClient.sendData(toSend);
		
		if(ProtocolResultFM.CLIENT_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}
