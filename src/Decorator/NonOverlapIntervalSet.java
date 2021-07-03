package Decorator;
import BasicInterface.IntervalSet;

public class NonOverlapIntervalSet<L>extends IntervalSetDecorator<L>{
	
	public NonOverlapIntervalSet(IntervalSet<L> set) {
		super(set);
	}

	/**
	 * ��label����һ��ʱ�䣬��ʱ��η����ص��򱨴�
	 * @param start ��ʼʱ��
	 * @param end	����ʱ��
	 * @param label	��ǩ
	 */
	public void insert(long start,long end,L label) {
		boolean flag=true;
		for(L temp:set.labels()) {
			if(!(end<=set.start(temp)||start>=set.end(temp))) {
				flag=false;
				System.out.println("�����ص�");
			}
		}
		if(flag) {
			super.insert(start, end, label);
		}
	}
	
}