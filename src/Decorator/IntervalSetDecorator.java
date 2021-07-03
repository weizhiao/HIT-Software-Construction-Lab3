package Decorator;

import java.util.List;
import java.util.Set;

import BasicInterface.IntervalSet;
import CommonADT.MyMap;

public abstract class IntervalSetDecorator<L> implements IntervalSet<L>{
	protected final IntervalSet<L> set;
	
	public IntervalSetDecorator(IntervalSet<L> set) {
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
	
	public long start(L label) {
		return set.start(label);
	}
	
	public long end(L label) {
		return set.end(label);
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