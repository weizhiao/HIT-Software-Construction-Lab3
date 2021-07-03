package CourseSchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import API.APIs;
import BasicInterfaceImpl.CommonMultiIntervalSet;
import CommonADT.Date;
import CommonADT.MyMap;
import Decorator.PeriodicMultiIntervalSet;

public class CourseScheduleApp{
	private Date startdate=new Date();
	private long weeks;
	private List<Course> courselist=new ArrayList<>();
	private PeriodicMultiIntervalSet<Course> set=new PeriodicMultiIntervalSet<>(new CommonMultiIntervalSet<>());
	
	//ʹ�÷���ʽ�������set
	
	/**
	 * ����ѧ�ڿ�ʼ����
	 * @param year
	 * @param month
	 * @param day
	 */
	public boolean setdate(int year,int month,int day) {
		return startdate.setdate(year, month, day,0);
	}
	
	/**
	 * ���ѧ�ڿ�ʼ����
	 * @return
	 */
	public Date getstartdate() {
		Date temp=new Date(startdate.getdata());
		return temp;
	}
	
	/**
	 * ���������
	 * @return
	 */
	public long getweeks() {
		return weeks;
	}
	
	/**
	 * ����ſα�
	 * @return
	 */
	public PeriodicMultiIntervalSet<Course> getMultiIntervalSet(){
		PeriodicMultiIntervalSet<Course> temp=new PeriodicMultiIntervalSet<>(new CommonMultiIntervalSet<>());
		temp.setloopperiod(7*24);
		temp.setlooptimes(weeks);
		for(MyMap<Course> m:set.arrangestart()) {
			if(m.start<=7*24) {
				temp.insert(m.start, m.end, m.label);
			}
		}
		return temp;
	}
	
	/**
	 * ����������
	 * @param weeks
	 */
	public boolean setweeks(long weeks) {
		if(weeks<=0) {
			System.out.println("�������������0");
			return false;
		}
		else {
			set.setloopperiod(7*24);
			set.setlooptimes(weeks);
			this.weeks=weeks;
			return true;
		}
	}
	
	/**
	 * ��ӿγ̣����Կγ�id����ʶ�γ̣����γ�id�Ѵ����򲻼���ÿγ�
	 * @param courseid
	 * @param coursename
	 * @param teachername
	 * @param position
	 * @param weeklyhours
	 */
	public void addcourse(long courseid,String coursename,String teachername,String position,long weeklyhours) {
		boolean flag=true;
		if(weeklyhours%2!=0||weeklyhours<=0) {
			System.out.println("�γ���ѧʱ��������ż���Ҵ���0");
			flag=false;
		}
		Course temp=new Course(courseid,coursename,teachername,position,weeklyhours);
		for(int i=0;i<courselist.size();i++) {
			if(courselist.get(i).equal(temp)) {
				System.out.println("�ÿγ�id�Ѵ���");
				flag=false;
			}
		}
		if(flag) {
			courselist.add(temp);
		}
	}
	
	/**
	 * ͨ���γ�id����ÿγ���list����±�
	 * @param courseid
	 * @return ������-1˵���γ̲�����
	 */
	public int getcourse(long courseid) {
		int index=-1;
		for(int i=0;i<courselist.size();i++) {
			if(courselist.get(i).getcourseid()==courseid) {
				index=i;
			}
		}
		return index;
	}
	
	/**
	 * ���ſγ�
	 * @param courseid
	 * @param week
	 * @param hourchoice
	 */
	public void arrangecourse(long courseid,int week,int hourchoice) {
		int index=getcourse(courseid);
		if(index==-1) {
			System.out.println("�ÿγ̲�����");
		}
		else {
			Course current=courselist.get(index);
			if(countweeklyhours(current)==current.getweeklyhours()) {
				System.out.println("�ÿγ��ܿ�ʱ���Ѵ�����");
			}
			else {
				int startweek=startdate.getdayofweek();
				int hours=0;
				if(week>=startweek) {
					hours+=(week-startweek)*24;
				}
				else {
					hours+=(7+week-startweek)*24;
				}
				switch(hourchoice) {
				case 1:
					hours+=8;
					set.insert(hours, hours+2, current);
					break;
				case 2:
					hours+=10;
					set.insert(hours, hours+2, current);
					break;
				case 3:
					hours+=13;
					set.insert(hours, hours+2, current);
					break;
				case 4:
					hours+=15;
					set.insert(hours, hours+2, current);
					break;
				case 5:
					hours+=19;
					set.insert(hours, hours+2, current);
					break;
				default:
					System.out.println("��ѡ��ָ��ʱ���");
				}
			}
		}
	}
	
	/**
	 * ��ʱ��ת��Ϊ����
	 * @param l
	 * @return
	 */
	public Date longtoDate(long l) {
		Date temp=new Date(startdate.getdata());
		temp.addhours(l);
		return temp;
	}
	
	/**
	 * ��ӡһ���ڵ����пγ�
	 */
	public void print() {
		for(MyMap<Course> course:set.arrangestart()) {
			if(startdate.betweenday(longtoDate(course.start))>=7) {
				break;
			}
			Date start=longtoDate(course.start);
			Date end=longtoDate(course.end);
			System.out.println("��һ�ڿ��Ͽ����ڣ�"+start.toString()+"\t"+start.gethourString()+"-"+end.gethourString()+"\t"+"����"+start.getdayofweek()+"\t"+course.label.toString());
		}
	}
	
	/**
	 * ���һ�ſε���ѧʱ��
	 * @param c
	 * @return
	 */
	public int countweeklyhours(Course c) {
		int count=0;
		long weekhours=7*24;
		for(MyMap<Course> temp:set.arrangestart()) {
			if(temp.end<=weekhours&&temp.label.equal(c)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * �г���������ӵĿγ̼���������
	 */
	public void printallcourse() {
		for(int i=0;i<courselist.size();i++) {
			Course current=courselist.get(i);
			System.out.print(current.toString());
			long left=current.getweeklyhours()-countweeklyhours(current);
			if(left==0) {
				System.out.println("\t�ܿ�ʱ��������");
			}
			else {
				System.out.println("\t�ܿ�ʱ��δ���������谲��"+left+"�ڿ�");
			}
		}
	}
	
	/**
	 * ���ѧ�ڽ���������
	 * @return
	 */
	public Date getenddate() {
		Date temp=new Date(startdate.getdata());
		temp.addhours(weeks*7*24);
		return temp;
	}
	
	/**
	 * ��ӡĳһ���ȫ���γ�
	 * @param year
	 * @param month
	 * @param day
	 */
	public void printallcourseofday(int year,int month,int day) {
		Date current=new Date();
		int count=0;
		if(current.setdate(year, month, day)) {
			long starthours=startdate.betweenhours(current);
			long endhours=starthours+24;
			for(MyMap<Course>temp:set.arrangestart()) {
				if(temp.start>=starthours&&temp.end<=endhours) {
					count++;
					Date start=longtoDate(temp.start);
					Date end=longtoDate(temp.end);
					System.out.println(start.gethourString()+"-"+end.gethourString()+"\t"+temp.label.toString());
				}
			}
			if(count==0) {
				System.out.println("�����޿�");
			}
		}
		else {
			System.out.println("��������ȷ������");
		}
	}
	
	/**
	 * �ͻ���
	 * @param args
	 */
	public static void main(String args[]) {
		boolean flag1=false;
		boolean flag2=false;
		CourseScheduleApp schedule=new CourseScheduleApp();
		Scanner input=new Scanner(System.in);
		APIs<Course> api=new APIs<>();
		while(true) {
			if(!flag1) {
				System.out.println("1 ����ѧ�ڿ�ʼ����:");
			}
			else {
				System.out.println("ѧ�ڿ�ʼ����:"+schedule.getstartdate().toString());
			}
			if(!flag2) {
				System.out.println("2 ����������");
			}
			else {
				System.out.println("������:"+schedule.getweeks());
			}
			if(flag1&&flag2) {
				System.out.println("3 ��ӿγ�");
				System.out.println("4 �����Ͽ�ʱ��");
				System.out.println("5 ��ӡһ�ܵĿα�");
				System.out.println("6 �鿴���пγ�");
				System.out.println("7 ��ӡĳһ�����пγ�");
				System.out.println("8 ����ÿ�ܵĿ���ʱ�����");
				System.out.println("9 ����ÿ�ܵ��ظ�ʱ�����");
			}
			System.out.println("0 �˳�");
			while(!input.hasNextInt()) {
				System.out.println("���������Ҫ�������");
				input.next();
			}
			int year;
			int month;
			int day;
			long week;
			long courseid;
			String coursename;
			String teachername;
			String position;
			long weeklyhours;
			int choice=input.nextInt();
			switch(choice) {
			case 1:
				if(!flag1) {
					System.out.println("�������꣺");
					year=input.nextInt();
					System.out.println("�������£�");
					month=input.nextInt();
					System.out.println("�������գ�");
					day=input.nextInt();
					if(schedule.setdate(year, month, day)) {
						flag1=true;
					}
				}
				break;
			case 2:
				if(!flag2) {
					System.out.println("��������������");
					week=input.nextLong();
					if(schedule.setweeks(week)) {
						flag2=true;
					}
				}
				break;
			case 3:
				if(flag1&&flag2) {
					System.out.println("������γ�id��");
					courseid=input.nextLong();
					System.out.println("������γ����ƣ�");
					coursename=input.next();
					System.out.println("�������ڿν�ʦ��");
					teachername=input.next();
					System.out.println("�������Ͽεص㣺");
					position=input.next();
					System.out.println("��������ѧʱ����ż������");
					weeklyhours=input.nextLong();
					schedule.addcourse(courseid, coursename, teachername, position, weeklyhours);
				}
				break;
			case 4:
				if(flag1&&flag2) {
					System.out.println("������γ�id��");
					courseid=input.nextLong();
					System.out.println("�������Ͽ����ڣ�����1~7��");
					week=input.nextLong();
					System.out.println("��ѡ���Ͽ�ʱ��(����1~5)��");
					System.out.println("1   8:00-10:00");
					System.out.println("2   10:00-12:00");
					System.out.println("3   13:00-15:00");
					System.out.println("4   15:00-17:00");
					System.out.println("5   19:00-21:00");
					choice=input.nextInt();
					schedule.arrangecourse(courseid, (int)week, choice);
				}
				break;
			case 5:
				if(flag1&&flag2) {
					schedule.print();
				}
				break;
			case 6:
				if(flag1&&flag2) {
					schedule.printallcourse();
				}
				break;
			case 7:
				if(flag1&&flag2) {
					System.out.println("�������꣺");
					year=input.nextInt();
					System.out.println("�������£�");
					month=input.nextInt();
					System.out.println("�������գ�");
					day=input.nextInt();
					schedule.printallcourseofday(year, month, day);
				}
				break;
			case 8:
				if(flag1&&flag2) {
					double free=api.calcFreeTimeRatio(schedule.getMultiIntervalSet(),schedule.getstartdate().betweenhours(schedule.getenddate()));
					System.out.println("���б���Ϊ��"+free);
				}
				break;
			case 9:
				if(flag1&&flag2) {
					double conflict=api.calcConflictRatio(schedule.getMultiIntervalSet(),schedule.getstartdate().betweenhours(schedule.getenddate()));
					System.out.println("�ظ�����Ϊ��"+conflict);
				}
				break;
			case 0:
				input.close();
				System.exit(0);
			}
			System.out.println();
		}
	}
}