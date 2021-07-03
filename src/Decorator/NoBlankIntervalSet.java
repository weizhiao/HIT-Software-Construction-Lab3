package Decorator;

import BasicInterface.IntervalSet;

public class NoBlankIntervalSet<L>extends IntervalSetDecorator<L>{
	
	public NoBlankIntervalSet(IntervalSet<L> set) {
		super(set);
	}

	/**
	 * ¼ì²éÊÇ·ñÓÐ¿Õ°×
	 */
	public void checkNoBlank() {
		long currenttime=set.getmin();
		long maxtime=set.getmax();
		while(currenttime!=maxtime) {
			long temp=currenttime;
			for(L label:set.containtime(currenttime)) {
				if(set.end(label)>temp) {
					temp=set.end(label);
				}
			}
			if(currenttime==temp) {
				assert false;
			}
			currenttime=temp;
		}
	}
}