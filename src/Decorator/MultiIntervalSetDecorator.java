package Decorator;

import java.util.List;
import java.util.Set;

import BasicInterface.IntervalSet;
import BasicInterface.MultiIntervalSet;
import CommonADT.MyMap;

public abstract class MultiIntervalSetDecorator<L>implements MultiIntervalSet<L>{
	protected final MultiIntervalSet<L> set;
	
	public MultiIntervalSetDecorator(MultiIntervalSet<L> set) {
		this.set=set;
	}
	
	public void insert(long start,long end,L label) {
		set.insert(start, end, label);
	}
	
	public Set<L> labels(){
		return set.labels();
	}
	
	public boolean remove(L label) {
		return set.remove(label);
	}
	
	public IntervalSet<Integer> intervals(L label){
		return set.intervals(label);
	}
	
	public long getmin() {
		return set.getmin();
	}
	
	public long getmax() {
		return set.getmax();
	}
	
	public Set<L> containtime(long currenttime){
		return set.containtime(currenttime);
	}
	
	public List<MyMap<L>> arrangestart(){
		return set.arrangestart();
	}
	
	public void removeall() {
		set.removeall();
	}
}