package Model;

public class Slave {
	
	private String mIP;
	private int mPort;
	
	private int mPart;
	
	public Slave(String ip, int port, int part) {
		mIP = ip;
		mPort = port;
		mPart = part;
	}
	
	public String getIP() {
		return mIP;
	}
	
	public int getPort() {
		return mPort;
	}
	
	public int getPart() {
		return mPart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mIP == null) ? 0 : mIP.hashCode());
		result = prime * result + mPart;
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
		if (mPart != other.mPart)
			return false;
		if (mPort != other.mPort)
			return false;
		return true;
	}
	
	
}