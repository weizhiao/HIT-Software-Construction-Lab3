package Decorator;
import BasicInterface.IntervalSet;

public class NonOverlapIntervalSet<L>extends IntervalSetDecorator<L>{
	
	public NonOverlapIntervalSet(IntervalSet<L> set) {
		super(set);
	}

	/**
	 * 给label分配一段时间，若时间段发生重叠则报错
	 * @param start 起始时间
	 * @param end	结束时间
	 * @param label	标签
	 */
	public void insert(long start,long end,L label) {
		boolean flag=true;
		for(L temp:set.labels()) {
			if(!(end<=set.start(temp)||start>=set.end(temp))) {
				flag=false;
				System.out.println("发生重叠");
			}
		}
		if(flag) {
			super.insert(start, end, label);
		}
	}
	
}