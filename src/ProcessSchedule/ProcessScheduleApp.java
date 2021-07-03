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
	 * ���һ�����̵������ĵ���������
	 * @param pid
	 * @param name
	 * @param mintime
	 * @param maxtime
	 * @return ���������ʧ�ܷ���false����������ӳɹ�����true
	 */
	public boolean addprocess(long pid,String name,long mintime,long maxtime) {
		Process temp=new Process(pid,name,mintime,maxtime);
		if(mintime>maxtime) {
			System.out.println("Error�����̵���Сִ��ʱ��������ִ��ʱ��");
			return false;
		}
		if(mintime<=0||maxtime<=0) {
			System.out.println("Error�����̵�ִ��ʱ��Ӧ����0");
			return false;
		}
		for(int i=0;i<processlist.size();i++) {
			if(processlist.get(i).getprocess().equal(temp)) {
				System.out.println("�����Ѵ���");
				return false;
			}
		}
		ProcessContext c=new ProcessContext(temp);
		processlist.add(c);
		return true;
	}
	
	/**
	 * ���ѡ���������
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
	 * ��þ��������ִ��ʱ������С�Ľ��̵��±�
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
	 * ��̽�����������
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
	 * ��ӡ���н��̵�ִ�й���
	 */
	public void print() {
		for(MyMap<Process> temp:set.arrangestart()) {
			System.out.println("ʱ�䣺"+temp.start+"-"+temp.end+"\t"+temp.label.toString());
		}
	}
	
	/**
	 * ����pid���ҽ����ڽ���������±�
	 * @param pid
	 * @return ������-1��ʾû�иý���
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
	 * ���������pid��ӡ��Ӧ���̵�������Ϣ
	 * @param pid
	 */
	public void printSelectpid(long pid) {
		int index=getprocess(pid);
		if(index==-1) {
			System.out.println("û�иý���");
		}
		else {
			if(processlist.get(index).isfinished()) {
				Process current=processlist.get(index).getprocess();
				System.out.println(current.toString()+"\t������Сִ��ʱ�䣺"+current.getmintime()+"\t������Сִ��ʱ�䣺"+current.getmaxtime());
				System.out.println("��ǰ��������ʱ�Σ�");
				for(MyMap<Integer>temp:set.intervals(current).arrangestart()) {
					System.out.println(temp.start+"-"+temp.end);
				}
			}
			else {
				System.out.println("��ǰ������δִ��,����ģ������");
			}
		}
	}
	
	/**
	 * ����ϴ�ģ��Ľ��
	 */
	public void clearrun() {
		runtime=0;
		for(int i=0;i<processlist.size();i++) {
			processlist.get(i).clear();
		}
		set.removeall();
	}
	
	/**
	 * �ͻ���
	 * @param args
	 */
	public static void main(String args[]) {
		ProcessScheduleApp schedule=new ProcessScheduleApp();
		Scanner input=new Scanner(System.in);
		while(true) {
			System.out.println("1 ��ӽ���");
			System.out.println("2 ���ѡ�����ģ������");
			System.out.println("3 ��̽�������ģ������");
			System.out.println("4 �鿴ѡ�����̵����й켣");
			System.out.println("0 �˳�");
			while(!input.hasNextInt()) {
				System.out.println("���������Ҫ�������");
				input.next();
			}
			int choice=input.nextInt();
			long pid;
			long mintime;
			long maxtime;
			String name;
			switch(choice) {
			case 1:
				System.out.println("��������̵�pid��");
				pid=input.nextLong();
				System.out.println("��������̵����ƣ�");
				name=input.next();
				System.out.println("��������̵���Сִ��ʱ�䣺");
				mintime=input.nextLong();
				System.out.println("��������̵����ִ��ʱ�䣺");
				maxtime=input.nextLong();
				if(schedule.addprocess(pid, name, mintime, maxtime)) {
					System.out.println("��ӳɹ�");
				}
				else {
					System.out.println("���ʧ��");
				}
				break;
			case 2:
				schedule.clearrun();
				schedule.runprocessrandom();
				schedule.print();
				System.out.println("ģ�����");
				break;
			case 3:
				schedule.clearrun();
				schedule.runminprocess();
				schedule.print();
				System.out.println("ģ�����");
				break;
			case 4:
				System.out.println("��������̵�pid��");
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