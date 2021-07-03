package API;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import BasicInterface.IntervalSet;
import BasicInterface.MultiIntervalSet;
import CommonADT.MyMap;

public class APIs<L>{
	
	/**
	 * 判断两个时间段是否冲突
	 * @param m1
	 * @param m2
	 * @return	返回0表示不冲突;返回1表示m1包含m2;返回2表示m2包含m1;
	 * 返回3表示冲突但m1与m2无包含关系;返回4表示时间段m1=m2
	 */
	private int isconflict(MyMap<L> m1,MyMap<L>m2) {
		if(m1.end<=m2.start||m2.end<=m1.start) {
			return 0;
		}
		else if(m1.start==m2.start&&m1.end==m2.end) {
			return 4;
		}
		else if(m1.start<=m2.start&&m1.end>=m2.end) {
			return 1;
		}
		else if(m1.start>=m2.start&&m1.end<=m2.end) {
			return 2;
		}
		else {
			return 3;
		}
	}
	
	/**
	 * 计算两个时间段重合的时间长度，并将冲突段存到列表中
	 * @param m1
	 * @param m2
	 * @return
	 */
	private double computeconflicttime(MyMap<L> m1,MyMap<L>m2,List<MyMap<L>>array) {
		double same=0;
		MyMap<L>temp=new MyMap<>();
		temp.label=m1.label;
		switch(isconflict(m1,m2)) {
		case 0:break;
		case 1:same=m2.end-m2.start;
		temp.start=m2.start;
		temp.end=m2.end;
		break;
		case 2:same=m1.end-m1.start;
		temp.start=m1.start;
		temp.end=m1.end;
		break;
		case 3:if(m1.end>m2.start) {
			same=m1.end-m2.start;
			temp.start=m2.start;
			temp.end=m1.end;
		}
		else {
			same=m2.end-m1.start;
			temp.start=m1.start;
			temp.end=m2.end;
		}
		break;
		case 4:
			same=m1.end-m1.start;
			temp.start=m1.start;
			temp.end=m1.end;
			break;
		}
		if(isconflict(m1,m2)!=0&&array!=null) {
			array.add(temp);
		}
		return same;
	}
	
	/**
	 * 获得总时长
	 * @param s1
	 * @param s2
	 * @return
	 */
	private double gettotaltime(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
		double totaltime;
		List<MyMap<L>> arrays1=s1.arrangestart();
		List<MyMap<L>> arrays2=s2.arrangestart();
		if(arrays1.get(arrays1.size()-1).end>arrays2.get(arrays2.size()-1).end){
			totaltime=arrays1.get(arrays1.size()-1).end;
		}
		else {
			totaltime=arrays2.get(arrays2.size()-1).end;
		}
		return totaltime;
	}
	
	/**
	 * 从array中获得不在集合mark中的最小开始时间的下标
	 * @param array
	 * @param mark
	 * @return 若返回-1说明mark已满
	 */
	private int getminindex(List<MyMap<L>>array,Set<Integer> mark) {
		long min=1000000000;
		int index=-1;
		for(int i=0;i<array.size();i++) {
			if(!mark.contains(i)&&array.get(i).start<min) {
				min=array.get(i).start;
				index=i;
			}
		}
		return index;
	}
	
	/**
	 * 合并冲突时间段
	 * @param array
	 * @return
	 */
	public List<MyMap<L>> mergetimearray(List<MyMap<L>>array) {
		Set<Integer> mark=new HashSet<>();
		List<MyMap<L>> result=new ArrayList<>();
		while(mark.size()!=array.size()) {
			int index=getminindex(array,mark);
			mark.add(index);
			long start=array.get(index).start;
			long end=array.get(index).end;
			for(int i=0;i<array.size();i++) {
				if(!mark.contains(i)) {
					if(array.get(i).start<=end&&array.get(i).end>end) {
						end=array.get(i).end;
						mark.add(i);
					}
					else if(array.get(i).end<=end) {
						mark.add(i);
					}
				}
			}
			MyMap<L>temp=new MyMap<>();
			temp.start=start;
			temp.end=end;
			result.add(temp);
		}
		return result;
	}
	
	public List<MyMap<L>> getfreetime(List<MyMap<L>>array,long start,long end){
		List<MyMap<L>> fulltime=mergetimearray(array);
		List<MyMap<L>> result=new ArrayList<>();
		long a=start;
		for(int i=0;i<fulltime.size();i++) {
			MyMap<L>current=fulltime.get(i);
			if(a<current.start) {
				MyMap<L>temp=new MyMap<>();
				temp.start=a;
				temp.end=current.start;
				result.add(temp);
			}
			a=current.end;
		}
		if(a!=end) {
			MyMap<L>temp=new MyMap<>();
			temp.start=a;
			temp.end=end;
			result.add(temp);
		}
		return result;
	}
	
	public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
		List<MyMap<L>> arrays1=s1.arrangestart();
		List<MyMap<L>> arrays2=s2.arrangestart();
		double same=0;
		double totaltime=gettotaltime(s1,s2);
		for(int i=0;i<arrays1.size();i++) {
			MyMap<L> m1=arrays1.get(i);
			for(int j=0;j<arrays2.size();j++) {
				MyMap<L> m2=arrays2.get(j);
				if(m1.label.equals(m2.label)) {
					same+=computeconflicttime(m1,m2,null);
				}
			}
		}
		return same/totaltime;
	}
	
	public double calcConflictRatio(IntervalSet<L> set,long end) {
		List<MyMap<L>> array=set.arrangestart();
		List<MyMap<L>> conflictarray=new ArrayList<>();
		double same=0;
		double totaltime=end;
		for(int i=0;i<array.size();i++) {
			MyMap<L>m1=array.get(i);
			for(int j=i+1;j<array.size();j++) {
				MyMap<L>m2=array.get(j);
				computeconflicttime(m1,m2,conflictarray);
			}
		}
		List<MyMap<L>> mergeconflictarray=mergetimearray(conflictarray);
		for(int i=0;i<mergeconflictarray.size();i++) {
			same+=mergeconflictarray.get(i).end-mergeconflictarray.get(i).start;
		}
		return same/totaltime;
	}
	
	public double calcConflictRatio(MultiIntervalSet<L> set,long end) {
		List<MyMap<L>> array=set.arrangestart();
		List<MyMap<L>> conflictarray=new ArrayList<>();
		double same=0;
		double totaltime=end;
		for(int i=0;i<array.size();i++) {
			MyMap<L>m1=array.get(i);
			for(int j=i+1;j<array.size();j++) {
				MyMap<L>m2=array.get(j);
				computeconflicttime(m1,m2,conflictarray);
			}
		}
		List<MyMap<L>> mergeconflictarray=mergetimearray(conflictarray);
		for(int i=0;i<mergeconflictarray.size();i++) {
			same+=mergeconflictarray.get(i).end-mergeconflictarray.get(i).start;
		}
		System.out.println(same/totaltime);
		return same/totaltime;
	}
	
	public double calcFreeTimeRatio(IntervalSet<L> set,long end) {
		List<MyMap<L>> array=set.arrangestart();
		List<MyMap<L>> fullarray=mergetimearray(array);
		double totaltime=end;
		double fulltime=0;
		for(int i=0;i<fullarray.size();i++) {
			fulltime+=fullarray.get(i).end-fullarray.get(i).start;
		}
		return (totaltime-fulltime)/totaltime;
	}
	
	public double calcFreeTimeRatio(MultiIntervalSet<L> set,long end) {
		List<MyMap<L>> array=set.arrangestart();
		List<MyMap<L>> fullarray=mergetimearray(array);
		double totaltime=end;
		double fulltime=0;
		for(int i=0;i<fullarray.size();i++) {
			fulltime+=fullarray.get(i).end-fullarray.get(i).start;
		}
		return (totaltime-fulltime)/totaltime;
	}
}