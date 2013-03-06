package Model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SlaveList {
	
	private Set<Slave> mSlaveList;
	
	public SlaveList() {
		mSlaveList = new HashSet<Slave>();
	}
	
	public synchronized void addSlave(Slave s) {
		mSlaveList.add(s);
	}
	
	public synchronized void removeSlave(Slave s) {
		mSlaveList.remove(s);
	}
	
	public synchronized List<Slave> getSlaves() {
		List<Slave> l = new LinkedList<Slave>();
		for (Slave s : mSlaveList) {
		    l.add(s);
		}
		return l;
	}
	
	public synchronized boolean isEmpty() {
		return mSlaveList.isEmpty();
	}

}