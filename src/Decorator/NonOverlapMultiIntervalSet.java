package Decorator;

import BasicInterface.MultiIntervalSet;

public class NonOverlapMultiIntervalSet<L>extends MultiIntervalSetDecorator<L>{

	public NonOverlapMultiIntervalSet(MultiIntervalSet<L> set) {
		super(set);
	}
	
	/**
	 * 给label分配一段时间，若时间段发生重叠则报错
	 * @param start 起始时间
	 * @param end	结束时间
	 * @param label	标签
	 */
	@Override public void insert(long start,long end,L label) {
		boolean flag=true;
		for(L temp:set.labels()) {
			for(int i:set.intervals(temp).labels()) {
				if(!(end<set.intervals(temp).start(i)||start>set.intervals(temp).end(i))) {
					flag=false;
					System.out.println("发生重叠");
				}
			}
		}
		if(flag) {
			super.insert(start, end, label);
		}
	}
}