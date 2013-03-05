package Network.ToMaster;

import Network.TCPClient;
import Network.Protocols.ProtocolRegister;
import Network.Protocols.ProtocolResultFS;
import System.Config;

public class Register {
	
	private boolean mIsConnected;
	private TCPClient mNetClient;
	
	public Register() {
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
	
	public boolean register() {
		
		if(!mIsConnected) {
			return false;
		}
		
		final StringBuilder query = new StringBuilder();
		
		query.append(ProtocolRegister.SLAVE_REGISTER);
		query.append(ProtocolRegister.COMMON_END_LINE);
		query.append(ProtocolRegister.SLAVE_IP);
		query.append(ProtocolRegister.COMMON_SEPARATOR);
		query.append(Config.MY_IP);
		query.append(ProtocolRegister.COMMON_END_LINE);
		query.append(ProtocolRegister.SLAVE_PORT);
		query.append(ProtocolRegister.COMMON_SEPARATOR);
		query.append(String.valueOf(Config.SLAVE_PORT));
		query.append(ProtocolRegister.COMMON_END_LINE);
		query.append(ProtocolRegister.COMMON_END_LINE);
		
		final String response = mNetClient.sendData(query.toString());
		//String response = ProtocolResultFS.MASTER_OK;
		//System.out.println(query.toString());
		
		if(ProtocolResultFS.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}
	
public boolean unregister() {
		
		if(!mIsConnected) {
			return false;
		}
		
		final StringBuilder query = new StringBuilder();
		
		query.append(ProtocolRegister.SLAVE_UNREGISTER);
		query.append(ProtocolRegister.COMMON_END_LINE);
		query.append(ProtocolRegister.SLAVE_IP);
		query.append(ProtocolRegister.COMMON_SEPARATOR);
		query.append(Config.MY_IP);
		query.append(ProtocolRegister.COMMON_END_LINE);
		query.append(ProtocolRegister.SLAVE_PORT);
		query.append(ProtocolRegister.COMMON_SEPARATOR);
		query.append(String.valueOf(Config.SLAVE_PORT));
		query.append(ProtocolRegister.COMMON_END_LINE);
		query.append(ProtocolRegister.COMMON_END_LINE);
		
		final String response = mNetClient.sendData(query.toString());
		//String response = ProtocolResultFS.MASTER_OK;
		//System.out.println(query.toString());
		
		if(ProtocolResultFS.MASTER_OK.equals(response)) {
			return true;
		} else {
			return false;
		}
	}

}
