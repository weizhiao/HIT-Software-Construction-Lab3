package Decorator;

import BasicInterface.MultiIntervalSet;

public class  PeriodicMultiIntervalSet<L>extends MultiIntervalSetDecorator<L>{
	private  long loopperiod;
	private  long looptimes;
	
	public PeriodicMultiIntervalSet(MultiIntervalSet<L> set) {
		super(set);
	}
	
	/**
	 * ����ѭ�����
	 * @param loopperiod
	 */
	public void setloopperiod(long loopperiod) {
		this.loopperiod=loopperiod;	
	}
	
	/**
	 * ����ѭ������
	 * @param looptimes
	 */
	public void setlooptimes(long looptimes) {
		this.looptimes=looptimes;	
	}
	
	/**
	 * �����Բ���ʱ���
	 */
	public void insert(long start,long end,L label) {
		for(int i=0;i<looptimes;i++) {
			super.insert(start, end, label);
			start+=loopperiod;
			end+=loopperiod;
		}
	}
}