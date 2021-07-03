package BasicInterfaceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import BasicInterface.IntervalSet;
import BasicInterface.MultiIntervalSet;
import CommonADT.MyMap;

public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L>{
	private List<IntervalSet<L>> IntervalSetlist=new ArrayList<>();
	
	//AF:labels组成的集族，其中一个label集合包含多个时间段的二元组(start,end)
	//RI:同一个二元组中start小于end
	//Safety from rep exposure；使用防御式拷贝来防止表示泄露
		
	@Override public void insert(long start,long end,L label) {
		int i=0;
		boolean isoverlap=false;
		boolean flag=true;
		for(int j=0;j<IntervalSetlist.size();j++) {
			IntervalSet<L>current=IntervalSetlist.get(i);
			if(current.labels().contains(label)) {
				if(!(end<=current.start(label)||start>=current.end(label))) {
					isoverlap=true;
					System.out.println("当前label已在该时间段存在，请重新插入");
				}
			}
		}
		if(!isoverlap) {
			for(;i<IntervalSetlist.size();i++) {
				if(!IntervalSetlist.get(i).labels().contains(label)) {
					flag=false;
					break;
				}
			}
			if(flag==false) {
				IntervalSetlist.get(i).insert(start, end, label);
			}
			else {
				IntervalSet<L> temp=new CommonIntervalSet<>();
				temp.insert(start, end, label);
				IntervalSetlist.add(temp);
			}
		}
	}
	
	@Override public Set<L> labels(){
		Set<L> temp=new HashSet<>();
		for(int i=0;i<IntervalSetlist.size();i++) {
			for(L label:IntervalSetlist.get(i).labels()) {
				temp.add(label);
			}
		}
		return temp;
	}
	
	@Override public boolean remove(L label) {
		int count=0;
		boolean flag=false;
		for(int i=0;i<IntervalSetlist.size();i++) {
			if(IntervalSetlist.get(i).remove(label)) {
				count++;
			}
		}
		if(count>0) {
			flag=true;
		}
		return flag;
	}
	
	@Override public IntervalSet<Integer> intervals(L label){
		IntervalSet<Integer>temp=new CommonIntervalSet<>();
		int flag;
		int index=0;
		Set<Integer> hascount=new HashSet<>();
		long currentmin;
		int currentminindex=-1;
		for(int i=0;i<IntervalSetlist.size();i++) {
			currentmin=1000000000;
			flag=0;
			for(int j=0;j<IntervalSetlist.size();j++) {
				if(IntervalSetlist.get(j).labels().contains(label)&&IntervalSetlist.get(j).start(label)<=currentmin&&!hascount.contains(j)) {
					currentmin=IntervalSetlist.get(j).start(label);
					currentminindex=j;
					flag=1;
				}
			}
			if(flag==1) {
				hascount.add(currentminindex);
				temp.insert(IntervalSetlist.get(currentminindex).start(label), IntervalSetlist.get(currentminindex).end(label), index++);
			}
		}
		return temp;
	}
	
	@Override public long getmin() {
		long min=100000000;
		for(int i=0;i<IntervalSetlist.size();i++) {
			if(IntervalSetlist.get(i).getmin()<min) {
				min=IntervalSetlist.get(i).getmin();
			}
		}
		return min;
	}
	
	@Override public long getmax() {
		long max=-10000000;
		for(int i=0;i<IntervalSetlist.size();i++) {
			if(IntervalSetlist.get(i).getmax()>max) {
				max=IntervalSetlist.get(i).getmax();
			}
		}
		return max;
	}
	
	@Override public Set<L> containtime(long currenttime){
		Set<L>temp=new HashSet<>();
		for(int i=0;i<IntervalSetlist.size();i++) {
			for(L label:IntervalSetlist.get(i).containtime(currenttime)) {
				temp.add(label);
			}
		}
		return temp;
	}
	
	@Override public List<MyMap<L>> arrangestart(){
		List<MyMap<L>>array=new ArrayList<>();
		for(int i=0;i<IntervalSetlist.size();i++) {
			for(int j=0;j<IntervalSetlist.get(i).arrangestart().size();j++) {
				array.add(IntervalSetlist.get(i).arrangestart().get(j));
			}
		}
		for(int i=0;i<array.size();i++) {
			long min=1000000000;
			int index=-1;
			MyMap<L>temp;
			for(int j=i;j<array.size();j++) {
				if(array.get(j).start<min) {
					min=array.get(j).start;
					index=j;
				}
			}
			temp=array.get(i);
			array.set(i, array.get(index));
			array.set(index, temp);
		}
		return array;
	}
	
	@Override public String toString() {
		String str="";
		for(L label:labels()) {
			str+=label+"="+"{";
			for(int temp:intervals(label).labels()) {
				str+=temp+"="+"["+intervals(label).start(temp)+","+intervals(label).end(temp)+"]";
			}
			str+="}";
		}
		return str;
	}
	
	@Override public void removeall() {
		IntervalSetlist.clear();
	}
}