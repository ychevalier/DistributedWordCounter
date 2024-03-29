package Model;

import java.util.Map;

import Exceptions.InvalidRegisterException;
import Network.Protocols.ProtocolRegister;
import System.Utils;

public class Slave {
	
	private String mIP;
	private int mPort;
	private boolean mAlive;

	public Slave(Map<String, String> params) throws InvalidRegisterException {
		this(params, true);
	}
	
	public Slave(Map<String, String> params, boolean alive) throws InvalidRegisterException {
		String tmp;
		
		// Result IP.
		tmp = params.get(ProtocolRegister.SLAVE_IP);
		if(tmp == null || tmp.isEmpty() || !Utils.checkIP(tmp)) {
			throw new InvalidRegisterException();
		}
		this.mIP = tmp;
		
		// Result Port.
		tmp = params.get(ProtocolRegister.SLAVE_PORT);
		if(tmp == null || tmp.isEmpty()) {
			throw new InvalidRegisterException();
		}
		int tmpInt = 0;
		try {
			tmpInt = Integer.decode(tmp);
		} catch(NumberFormatException e) {
			throw new InvalidRegisterException();
		}
		this.mPort = tmpInt;
		mAlive = alive;
	}
	
	public Slave(String ip, int port) {
		mIP = ip;
		mPort = port;
		mAlive = true;
	}
	
	public String getIP() {
		return mIP;
	}
	
	public int getPort() {
		return mPort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mIP == null) ? 0 : mIP.hashCode());
		result = prime * result + mPort;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slave other = (Slave) obj;
		if (mIP == null) {
			if (other.mIP != null)
				return false;
		} else if (!mIP.equals(other.mIP))
			return false;
		if (mPort != other.mPort)
			return false;
		return true;
	}
	
	public boolean isAlive() {
		return mAlive;
	}
	
}
