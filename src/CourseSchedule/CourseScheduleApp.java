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
	
	//使用防御式拷贝获得set
	
	/**
	 * 设置学期开始日期
	 * @param year
	 * @param month
	 * @param day
	 */
	public boolean setdate(int year,int month,int day) {
		return startdate.setdate(year, month, day,0);
	}
	
	/**
	 * 获得学期开始日期
	 * @return
	 */
	public Date getstartdate() {
		Date temp=new Date(startdate.getdata());
		return temp;
	}
	
	/**
	 * 获得总周数
	 * @return
	 */
	public long getweeks() {
		return weeks;
	}
	
	/**
	 * 获得排课表
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
	 * 设置总周数
	 * @param weeks
	 */
	public boolean setweeks(long weeks) {
		if(weeks<=0) {
			System.out.println("总周数必须大于0");
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
	 * 添加课程，且以课程id来标识课程，若课程id已存在则不加入该课程
	 * @param courseid
	 * @param coursename
	 * @param teachername
	 * @param position
	 * @param weeklyhours
	 */
	public void addcourse(long courseid,String coursename,String teachername,String position,long weeklyhours) {
		boolean flag=true;
		if(weeklyhours%2!=0||weeklyhours<=0) {
			System.out.println("课程周学时数必须是偶数且大于0");
			flag=false;
		}
		Course temp=new Course(courseid,coursename,teachername,position,weeklyhours);
		for(int i=0;i<courselist.size();i++) {
			if(courselist.get(i).equal(temp)) {
				System.out.println("该课程id已存在");
				flag=false;
			}
		}
		if(flag) {
			courselist.add(temp);
		}
	}
	
	/**
	 * 通过课程id来获得课程在list里的下标
	 * @param courseid
	 * @return 若返回-1说明课程不存在
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
	 * 安排课程
	 * @param courseid
	 * @param week
	 * @param hourchoice
	 */
	public void arrangecourse(long courseid,int week,int hourchoice) {
		int index=getcourse(courseid);
		if(index==-1) {
			System.out.println("该课程不存在");
		}
		else {
			Course current=courselist.get(index);
			if(countweeklyhours(current)==current.getweeklyhours()) {
				System.out.println("该课程周课时数已达上限");
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
					System.out.println("请选择指定时间段");
				}
			}
		}
	}
	
	/**
	 * 将时间转换为日期
	 * @param l
	 * @return
	 */
	public Date longtoDate(long l) {
		Date temp=new Date(startdate.getdata());
		temp.addhours(l);
		return temp;
	}
	
	/**
	 * 打印一周内的所有课程
	 */
	public void print() {
		for(MyMap<Course> course:set.arrangestart()) {
			if(startdate.betweenday(longtoDate(course.start))>=7) {
				break;
			}
			Date start=longtoDate(course.start);
			Date end=longtoDate(course.end);
			System.out.println("第一节课上课日期："+start.toString()+"\t"+start.gethourString()+"-"+end.gethourString()+"\t"+"星期"+start.getdayofweek()+"\t"+course.label.toString());
		}
	}
	
	/**
	 * 获得一门课的周学时数
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
	 * 列出所有已添加的课程及其分配情况
	 */
	public void printallcourse() {
		for(int i=0;i<courselist.size();i++) {
			Course current=courselist.get(i);
			System.out.print(current.toString());
			long left=current.getweeklyhours()-countweeklyhours(current);
			if(left==0) {
				System.out.println("\t周课时数已排满");
			}
			else {
				System.out.println("\t周课时数未排满，还需安排"+left+"节课");
			}
		}
	}
	
	/**
	 * 获得学期结束的日期
	 * @return
	 */
	public Date getenddate() {
		Date temp=new Date(startdate.getdata());
		temp.addhours(weeks*7*24);
		return temp;
	}
	
	/**
	 * 打印某一天的全部课程
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
				System.out.println("该天无课");
			}
		}
		else {
			System.out.println("请输入正确的日期");
		}
	}
	
	/**
	 * 客户端
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
				System.out.println("1 设置学期开始日期:");
			}
			else {
				System.out.println("学期开始日期:"+schedule.getstartdate().toString());
			}
			if(!flag2) {
				System.out.println("2 设置总周数");
			}
			else {
				System.out.println("总周数:"+schedule.getweeks());
			}
			if(flag1&&flag2) {
				System.out.println("3 添加课程");
				System.out.println("4 安排上课时间");
				System.out.println("5 打印一周的课表");
				System.out.println("6 查看所有课程");
				System.out.println("7 打印某一天所有课程");
				System.out.println("8 计算每周的空闲时间比例");
				System.out.println("9 计算每周的重复时间比例");
			}
			System.out.println("0 退出");
			while(!input.hasNextInt()) {
				System.out.println("请输入符合要求的整数");
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
					System.out.println("请输入年：");
					year=input.nextInt();
					System.out.println("请输入月：");
					month=input.nextInt();
					System.out.println("请输入日：");
					day=input.nextInt();
					if(schedule.setdate(year, month, day)) {
						flag1=true;
					}
				}
				break;
			case 2:
				if(!flag2) {
					System.out.println("请输入总周数：");
					week=input.nextLong();
					if(schedule.setweeks(week)) {
						flag2=true;
					}
				}
				break;
			case 3:
				if(flag1&&flag2) {
					System.out.println("请输入课程id：");
					courseid=input.nextLong();
					System.out.println("请输入课程名称：");
					coursename=input.next();
					System.out.println("请输入授课教师：");
					teachername=input.next();
					System.out.println("请输入上课地点：");
					position=input.next();
					System.out.println("请输入周学时数（偶数）：");
					weeklyhours=input.nextLong();
					schedule.addcourse(courseid, coursename, teachername, position, weeklyhours);
				}
				break;
			case 4:
				if(flag1&&flag2) {
					System.out.println("请输入课程id：");
					courseid=input.nextLong();
					System.out.println("请输入上课星期（输入1~7）");
					week=input.nextLong();
					System.out.println("请选择上课时间(输入1~5)：");
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
					System.out.println("请输入年：");
					year=input.nextInt();
					System.out.println("请输入月：");
					month=input.nextInt();
					System.out.println("请输入日：");
					day=input.nextInt();
					schedule.printallcourseofday(year, month, day);
				}
				break;
			case 8:
				if(flag1&&flag2) {
					double free=api.calcFreeTimeRatio(schedule.getMultiIntervalSet(),schedule.getstartdate().betweenhours(schedule.getenddate()));
					System.out.println("空闲比例为："+free);
				}
				break;
			case 9:
				if(flag1&&flag2) {
					double conflict=api.calcConflictRatio(schedule.getMultiIntervalSet(),schedule.getstartdate().betweenhours(schedule.getenddate()));
					System.out.println("重复比例为："+conflict);
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