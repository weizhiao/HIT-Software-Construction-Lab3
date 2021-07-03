package Decorator;

import BasicInterface.MultiIntervalSet;

public class NonOverlapMultiIntervalSet<L>extends MultiIntervalSetDecorator<L>{

	public NonOverlapMultiIntervalSet(MultiIntervalSet<L> set) {
		super(set);
	}
	
	/**
	 * ��label����һ��ʱ�䣬��ʱ��η����ص��򱨴�
	 * @param start ��ʼʱ��
	 * @param end	����ʱ��
	 * @param label	��ǩ
	 */
	@Override public void insert(long start,long end,L label) {
		boolean flag=true;
		for(L temp:set.labels()) {
			for(int i:set.intervals(temp).labels()) {
				if(!(end<set.intervals(temp).start(i)||start>set.intervals(temp).end(i))) {
					flag=false;
					System.out.println("�����ص�");
				}
			}
		}
		if(flag) {
			super.insert(start, end, label);
		}
	}
}