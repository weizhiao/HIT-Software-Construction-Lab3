package Decorator;

import BasicInterface.MultiIntervalSet;

public class  PeriodicMultiIntervalSet<L>extends MultiIntervalSetDecorator<L>{
	private  long loopperiod;
	private  long looptimes;
	
	public PeriodicMultiIntervalSet(MultiIntervalSet<L> set) {
		super(set);
	}
	
	/**
	 * 设置循环间隔
	 * @param loopperiod
	 */
	public void setloopperiod(long loopperiod) {
		this.loopperiod=loopperiod;	
	}
	
	/**
	 * 设置循环次数
	 * @param looptimes
	 */
	public void setlooptimes(long looptimes) {
		this.looptimes=looptimes;	
	}
	
	/**
	 * 周期性插入时间段
	 */
	public void insert(long start,long end,L label) {
		for(int i=0;i<looptimes;i++) {
			super.insert(start, end, label);
			start+=loopperiod;
			end+=loopperiod;
		}
	}
}