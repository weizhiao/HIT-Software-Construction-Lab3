package ProcessSchedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import BasicInterfaceImpl.CommonMultiIntervalSet;
import CommonADT.MyMap;
import Decorator.NonOverlapMultiIntervalSet;

public class ProcessScheduleApp{
	private List<ProcessContext> processlist=new ArrayList<>();
	private NonOverlapMultiIntervalSet<Process> set=new NonOverlapMultiIntervalSet<>(new CommonMultiIntervalSet<>());
	private long runtime=0;
	
	/**
	 * 添加一个进程的上下文到进程组里
	 * @param pid
	 * @param name
	 * @param mintime
	 * @param maxtime
	 * @return 若进程添加失败返回false，若进程添加成功返回true
	 */
	public boolean addprocess(long pid,String name,long mintime,long maxtime) {
		Process temp=new Process(pid,name,mintime,maxtime);
		if(mintime>maxtime) {
			System.out.println("Error：进程的最小执行时间大于最大执行时间");
			return false;
		}
		if(mintime<=0||maxtime<=0) {
			System.out.println("Error：进程的执行时间应大于0");
			return false;
		}
		for(int i=0;i<processlist.size();i++) {
			if(processlist.get(i).getprocess().equal(temp)) {
				System.out.println("进程已存在");
				return false;
			}
		}
		ProcessContext c=new ProcessContext(temp);
		processlist.add(c);
		return true;
	}
	
	/**
	 * 随机选择进程运行
	 */
	public void runprocessrandom() {
		Random random=new Random();
		Set<Integer> end=new HashSet<>();
		int choice;
		int time;
		while(end.size()!=processlist.size()) {
			runtime+=random.nextInt(30);
			choice=random.nextInt(processlist.size());
			if(!end.contains(choice)) {
				ProcessContext current=processlist.get(choice);
				time=random.nextInt((int)current.maxremaintime())+1;
				current.run(time);
				if(current.isfinished()) {
					end.add(choice);
				}
				set.insert(runtime, runtime+time, current.getprocess());
				runtime+=time;
			}
		}
	}
	
	/**
	 * 获得距离其最大执行时间差距最小的进程的下标
	 * @return
	 */
	public int getminremainprocessindex(Set<Integer> end) {
		int index=0;
		long remain=1000000000;
		for(int i=0;i<processlist.size();i++) {
			if(!end.contains(i)&&remain>processlist.get(i).maxremaintime()) {
				remain=processlist.get(i).maxremaintime();
				index=i;
			}
		}
		return index;
	}
	
	/**
	 * 最短进程优先运行
	 */
	public void runminprocess() {
		Random random=new Random();
		Set<Integer> end=new HashSet<>();
		int choice;
		int time;
		while(end.size()!=processlist.size()) {
			runtime+=random.nextInt(30);
			choice=getminremainprocessindex(end);
			ProcessContext current=processlist.get(choice);
			time=random.nextInt((int)current.maxremaintime())+1;
			current.run(time);
			if(current.isfinished()) {
				end.add(choice);
			}
			set.insert(runtime, runtime+time, current.getprocess());
			runtime+=time;
		}
	}
	
	/**
	 * 打印所有进程的执行过程
	 */
	public void print() {
		for(MyMap<Process> temp:set.arrangestart()) {
			System.out.println("时间："+temp.start+"-"+temp.end+"\t"+temp.label.toString());
		}
	}
	
	/**
	 * 根据pid查找进程在进程组里的下标
	 * @param pid
	 * @return 若返回-1表示没有该进程
	 */
	public int getprocess(long pid) {
		int index=-1;
		for(int i=0;i<processlist.size();i++) {
			if(pid==processlist.get(i).getprocess().getpid()) {
				index=i;
			}
		}
		return index;
	}
	
	/**
	 * 根据输入的pid打印对应进程的运行信息
	 * @param pid
	 */
	public void printSelectpid(long pid) {
		int index=getprocess(pid);
		if(index==-1) {
			System.out.println("没有该进程");
		}
		else {
			if(processlist.get(index).isfinished()) {
				Process current=processlist.get(index).getprocess();
				System.out.println(current.toString()+"\t进程最小执行时间："+current.getmintime()+"\t进程最小执行时间："+current.getmaxtime());
				System.out.println("当前进程运行时段：");
				for(MyMap<Integer>temp:set.intervals(current).arrangestart()) {
					System.out.println(temp.start+"-"+temp.end);
				}
			}
			else {
				System.out.println("当前进程尚未执行,请先模拟运行");
			}
		}
	}
	
	/**
	 * 清除上次模拟的结果
	 */
	public void clearrun() {
		runtime=0;
		for(int i=0;i<processlist.size();i++) {
			processlist.get(i).clear();
		}
		set.removeall();
	}
	
	/**
	 * 客户端
	 * @param args
	 */
	public static void main(String args[]) {
		ProcessScheduleApp schedule=new ProcessScheduleApp();
		Scanner input=new Scanner(System.in);
		while(true) {
			System.out.println("1 添加进程");
			System.out.println("2 随机选择进程模拟运行");
			System.out.println("3 最短进程优先模拟运行");
			System.out.println("4 查看选定进程的运行轨迹");
			System.out.println("0 退出");
			while(!input.hasNextInt()) {
				System.out.println("请输入符合要求的整数");
				input.next();
			}
			int choice=input.nextInt();
			long pid;
			long mintime;
			long maxtime;
			String name;
			switch(choice) {
			case 1:
				System.out.println("请输入进程的pid：");
				pid=input.nextLong();
				System.out.println("请输入进程的名称：");
				name=input.next();
				System.out.println("请输入进程的最小执行时间：");
				mintime=input.nextLong();
				System.out.println("请输入进程的最大执行时间：");
				maxtime=input.nextLong();
				if(schedule.addprocess(pid, name, mintime, maxtime)) {
					System.out.println("添加成功");
				}
				else {
					System.out.println("添加失败");
				}
				break;
			case 2:
				schedule.clearrun();
				schedule.runprocessrandom();
				schedule.print();
				System.out.println("模拟完成");
				break;
			case 3:
				schedule.clearrun();
				schedule.runminprocess();
				schedule.print();
				System.out.println("模拟完成");
				break;
			case 4:
				System.out.println("请输入进程的pid：");
				pid=input.nextLong();
				schedule.printSelectpid(pid);
				break;
			case 0:
				input.close();
				System.exit(0);
			}
			System.out.println();
		}
	}
}