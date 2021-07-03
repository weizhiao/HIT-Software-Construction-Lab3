package BasicInterface;
import java.util.List;
import java.util.Set;

import BasicInterfaceImpl.CommonMultiIntervalSet;
import CommonADT.MyMap;

public interface MultiIntervalSet<L>{
	public static <L> MultiIntervalSet<L> empty(){
		return new CommonMultiIntervalSet<L>();
	}
	
	public static <L> MultiIntervalSet<L> CreateMultiIntervalSet(IntervalSet<L> initial){
		CommonMultiIntervalSet<L> temp=new CommonMultiIntervalSet<L>();
		for(L label:initial.labels()) {
			temp.insert(initial.start(label), initial.end(label), label);
		}
		return temp;
	}
	
	/**
	 * 给label分配一段时间
	 * @param start 起始时间
	 * @param end	结束时间
	 * @param label	标签
	 */
	public void insert(long start,long end,L label);
	
	/**
	 * 返回所有标签的集合
	 * @return
	 */
	public Set<L> labels();
	
	/**
	 * 移除label及其所有的时间段
	 * @param label 标签
	 * @return	若label存在返回true；若label不存在返回false
	 */
	public boolean remove(L label);
	
	/**
	 * 得到label对应的所有时间段
	 * @param label	标签
	 * @return	时间段从小到大排列
	 */
	public IntervalSet<Integer> intervals(L label);
	
	/**
	 * 得到最小时间
	 * @return
	 */
	public long getmin();
	
	/**
	 * 得到最大时间
	 * @return
	 */
	public long getmax();
	
	/**
	 * 返回一个包含当前时间的label的集合
	 * @param currenttime
	 * @return
	 */
	public Set<L> containtime(long currenttime);
	
	/**
	 * 返回一个按起始时间从小到大顺序排序的label列表,同一个label可能重复出现
	 * @return
	 */
	public List<MyMap<L>> arrangestart();
	
	/**
	 * 删除所有元素
	 */
	public void removeall();
}