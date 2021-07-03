package BasicInterfaceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import BasicInterface.IntervalSet;
import CommonADT.MyMap;

public class CommonIntervalSet<L> implements IntervalSet<L>{
	private List<L> labellist=new ArrayList<>();
	private List<Long> startlist=new ArrayList<>();
	private List<Long> endlist=new ArrayList<>();
	
	//AF:由三元组（label，start，end）组成的集合
	//RI；没有两个相同的label，且每个label的start小于end
	//Safety from rep exposure；使用防御式拷贝来防止表示泄露
	
	
	private void checkRep() {
		for(int i=0;i<labellist.size();i++) {
			if(startlist.get(i)>=endlist.get(i)) {
				assert false;
			}
		}
		for(int i=0;i<labellist.size();i++) {
			for(int j=0;j<labellist.size();j++) {
				if(i!=j&&labellist.get(i).equals(labellist.get(j))) {
					assert false;
				}
			}
		}
	}
	
	@Override public void insert(long start,long end,L label) {
		labellist.add(label);
		startlist.add(start);
		endlist.add(end);
		checkRep();
	}
	
	@Override public Set<L> labels(){
		Set<L> temp=new HashSet<>();
		for(L label:labellist) {
			temp.add(label);
		}
		return temp;
	}
	
	@Override public boolean remove(L label) {
		boolean flag=false;
		int i=0;
		for(;i<labellist.size();i++) {
			if(label.equals(labellist.get(i))) {
				flag=true;
				labellist.remove(i);
				startlist.remove(i);
				endlist.remove(i);
			}
		}
		checkRep();
		return flag;	
	}
	
	@Override public long start(L label) {
		int i=0;
		for(;i<labellist.size();i++) {
			if(label.equals(labellist.get(i))) {
				break;
			}
		}
		if(i==labellist.size()) {
			return -1;
		}
		return startlist.get(i);
	}
	
	@Override public long end(L label) {
		int i=0;
		for(;i<labellist.size();i++) {
			if(label.equals(labellist.get(i))) {
				break;
			}
		}
		if(i==labellist.size()) {
			return -1;
		}
		return endlist.get(i);
	}
	
	@Override public long getmin() {
		long min=100000000;
		for(L label:labels()) {
			if(start(label)<min) {
				min=start(label);
			}
		}
		return min;
	}
	
	@Override public long getmax() {
		long max=-100000000;
		for(L label:labels()) {
			if(end(label)>max) {
				max=end(label);
			}
		}
		return max;
	}
	
	@Override public Set<L> containtime(long currenttime){
		Set<L> temp=new HashSet<>();
		for(L label:labels()) {
			if(start(label)<=currenttime&&end(label)>=currenttime) {
				temp.add(label);
			}
		}
		return temp;
	}
	
	@Override public List<MyMap<L>> arrangestart(){
		List<MyMap<L>>array=new ArrayList<>();
		Set<Integer> contain=new HashSet<>();
		long min;
		int index=-1;
		for(int i=0;i<labellist.size();i++) {
			MyMap<L>temp=new MyMap<>();
			min=1000000000;
			for(int j=0;j<labellist.size();j++) {
				if(!contain.contains(j)&&startlist.get(j)<min) {
					min=startlist.get(j);
					index=j;
				}
			}
			contain.add(index);
			temp.label=labellist.get(index);
			temp.start=min;
			temp.end=endlist.get(index);
			array.add(temp);
		}
		return array;
	}
	
	@Override public String toString() {
		String str="";
		int i;
		for(i=0;i<labellist.size()-1;i++) {
			str+=labellist.get(i)+"="+"["+startlist.get(i)+","+endlist.get(i)+"]"+",";
		}
		if(i==labellist.size()-1) {
			str+=labellist.get(i)+"="+"["+startlist.get(i)+","+endlist.get(i)+"]";
		}
		return str;
	}
	
	@Override public void removeall() {
		labellist.clear();
		startlist.clear();
		endlist.clear();
	}
}