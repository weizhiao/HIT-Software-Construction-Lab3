package DutyRoster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import API.APIs;
import BasicInterfaceImpl.CommonMultiIntervalSet;
import CommonADT.Date;
import CommonADT.MyMap;
import Decorator.NonOverlapMultiIntervalSet;



public class DutyRosterApp{
	private Date startdate=new Date();
	private Date enddate=new Date();
	private List<Employee> employeelist=new ArrayList<>();
	private NonOverlapMultiIntervalSet<Employee> set= new NonOverlapMultiIntervalSet<>(new CommonMultiIntervalSet<>());
	
	//AF:员工，排班时间的笛卡尔乘积
	//RI:员工排表后不可删除
	//使用防御式拷贝
	
	/**
	 * 初始化排班表，但保留员工
	 */
	public void clear() {
		set.removeall();
	}
	
	public NonOverlapMultiIntervalSet<Employee> getIntervalSet(){
		NonOverlapMultiIntervalSet<Employee> temp= new NonOverlapMultiIntervalSet<>(new CommonMultiIntervalSet<>());
		for(MyMap<Employee> e:set.arrangestart()) {
			temp.insert(e.start, e.end, e.label);
		}
		return temp;
	}
	
	/**
	 * 根据格式添加员工
	 * @param str
	 */
	public void addfileemployee(String str) {
		Pattern s=Pattern.compile("([a-zA-Z]+)\\{([a-zA-Z]+),(\\d+)-(\\d+)-(\\d+)}");
		String phone="";
		String name=null;
		String position=null;
		Matcher m=s.matcher(str);	
		if(m.find()) {
			name=m.group(1);
			if(getemployeeindex(name)!=-1) {
				System.out.println("文件格式错误:有员工名字重复");
				System.exit(0);
			}
			position=m.group(2);
			if(m.group(3).length()==3&&m.group(4).length()==4&&m.group(5).length()==4) {
				phone+=m.group(3)+m.group(4)+m.group(5);
				addemployee(name,position,phone);
			}
			else {
				System.out.println("文件格式错误:手机共 11 位，分为三段（3-4-4）");
				System.exit(0);
			}
		}
	}
	
	/**
	 * 根据文件设置日期
	 * @param str
	 */
	public void setfiledate(String str) {
		Pattern dateP=Pattern.compile("(\\d+)-(\\d+)-(\\d+),(\\d+)-(\\d+)-(\\d+)");
		Matcher m=dateP.matcher(str);
		int startyear;
		int startmonth;
		int startday;
		int endyear;
		int endmonth;
		int endday;
		if(m.find()) {
			startyear=Integer.valueOf(m.group(1));
			startmonth=Integer.valueOf(m.group(2));
			startday=Integer.valueOf(m.group(3));
			endyear=Integer.valueOf(m.group(4));
			endmonth=Integer.valueOf(m.group(5));
			endday=Integer.valueOf(m.group(6));
			setstartdate(startyear,startmonth,startday);
			setenddate(endyear,endmonth,endday);
		}
		else {
			System.out.println("文件格式有误");
			System.exit(0);
		}
	}
	
	/**
	 * 根据文件排班
	 * @param str
	 */
	public void insertfile(String str) {
		Pattern s=Pattern.compile("([a-zA-Z]+)\\{(\\d+)-(\\d+)-(\\d+),(\\d+)-(\\d+)-(\\d+)\\}");
		Matcher m=s.matcher(str);
		String name=null;
		int startyear;
		int startmonth;
		int startday;
		int endyear;
		int endmonth;
		int endday;
		if(m.find()) {
			name=m.group(1);
			startyear=Integer.valueOf(m.group(2));
			startmonth=Integer.valueOf(m.group(3));
			startday=Integer.valueOf(m.group(4));
			endyear=Integer.valueOf(m.group(5));
			endmonth=Integer.valueOf(m.group(6));
			endday=Integer.valueOf(m.group(7));
			if(getemployeeindex(name)==-1) {
				System.out.println("文件格式有误:出现在 Roster 内的员工，必须在 Employee 中已有定义");
				System.exit(1);
			}
			else
			insert(name,startyear,startmonth,startday,endyear,endmonth,endday);
		}
		else {
			System.out.println("文件格式有误");
			System.exit(0);
		}
	}
	
	/**
	 * 读文件构造排班表
	 * @param fp
	 * @return
	 * @throws IOException
	 */
	public boolean ReadFile(File fp) throws IOException {
		FileReader reader=new FileReader(fp);
		BufferedReader in=new BufferedReader(reader);
		String str="";
		String temp=null;
		while((temp=in.readLine())!=null) {
			str+=temp;
		}
		in.close();
		str=str.replaceAll("\\s", "");
		Pattern context=Pattern.compile("\\{.*\\}");
		Pattern employee=Pattern.compile("Employee\\{.*?\\}\\}");
		Pattern period=Pattern.compile("Period\\{.*?\\}");
		Pattern roster=Pattern.compile("Roster\\{.*?\\}\\}");
		String employeestr=null;
		String periodstr=null;
		String rosterstr=null;
		String []employees=null;
		String []rosters=null;
		Matcher m=employee.matcher(str);
		if(m.find()) {
			employeestr=m.group();
			m=context.matcher(employeestr);
			if(m.find()) {
				employeestr=m.group();
				employees=employeestr.split("　");
				for(int i=1;i<employees.length;i++) {
					addfileemployee(employees[i]);
				}
			}
		}
		m=period.matcher(str);
		if(m.find()) {
			periodstr=m.group();
			m=context.matcher(periodstr);
			if(m.find()) {
				periodstr=m.group();
				setfiledate(periodstr);
			}
		}
		m=roster.matcher(str);
		if(m.find()) {
			rosterstr=m.group();
			m=context.matcher(rosterstr);
			if(m.find()) {
				rosterstr=m.group();
				rosters=rosterstr.split("　");
				for(int i=1;i<rosters.length;i++) {
					insertfile(rosters[i]);
				}
			}
		}
		return true;
	}
	
	/**
	 * 设置排班开始日期
	 * @param year
	 * @param month
	 * @param day
	 * @return 若输入的年月日不符合规范返回false，代表设置失败；符合规范则返回
	 * true，代表设置成功
	 */
	public boolean setstartdate(int year,int month,int day) {
		return startdate.setdate(year, month, day);
	}
	
	/**
	 * 打印开始日期
	 */
	public void printstartdate() {
		System.out.println(startdate.toString());
	}
	
	/**
	 * 打印结束日期
	 */
	public void printenddate() {
		System.out.println(enddate.toString());
	}
	
	/**
	 * 设置排班结束日期
	 * @param year
	 * @param month
	 * @param day
	 * @return 若输入的年月日不符合规范返回false，代表设置失败；符合规范则返回
	 * true，代表设置成功
	 */
	public boolean setenddate(int year,int month,int day) {
		return enddate.setdate(year, month, day);
	}
	/**
	 * 检查结束日期是否比开始日期大
	 * @return 若合法返回true；若不合法返回false
	 */
	public boolean checkdate() {
		long between=startdate.betweenday(enddate);
		if(between>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 添加员工
	 * @param name
	 * @param position
	 * @param phone
	 */
	public void addemployee(String name,String position,String phone) {
			Employee temp=new Employee(name,position,phone);
			employeelist.add(temp);
	}
	
	/**
	 * 删除员工，已在排班表里的不能删除
	 * @param name
	 * @return 删除成功返回true，删除失败返回false
	 */
	public boolean deleteemployee(String name) {
		boolean flag=false;
		int index=getemployeeindex(name);
		if(index==-1) {
			return false;
		}
		flag=true;
		for(Employee e:set.labels()) {
			if(e.getname().equals(name)) {
				flag=false;
			}
		}
		if(flag) {
			employeelist.remove(index);
		}
		return flag;
	}
	
	/**
	 * 返回员工的下标
	 * @param name
	 * @return
	 */
	public int getemployeeindex(String name) {
		for(int i=0;i<employeelist.size();i++) {
			if(employeelist.get(i).getname().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 给指定员工排班
	 * @param name
	 * @param year
	 * @param month
	 * @param day
	 * @param len
	 */
	public void insert(String name,int startyear,int startmonth,int startday,int endyear,int endmonth,int endday) {
		Date starttemp=new Date();
		Date endtemp=new Date();
		starttemp.setdate(startyear, startmonth, startday);
		endtemp.setdate(endyear, endmonth, endday);
		long start=startdate.betweenday(starttemp);
		long end=startdate.betweenday(endtemp);
		if(start<0||end>getlength()) {
			System.out.println("不在排班时段内");
		}
		else {
			int index=getemployeeindex(name);
			set.insert(start, end, employeelist.get(index));
		}
	}
	
	/**
	 * 自动排班，并使每个人排的天数尽量相等
	 */
	public void autoinsert() {
		long start=0;
		if(employeelist.size()==0) {
			System.out.println("请先添加员工");
			System.out.println("排班失败");
		}
		else {
			long personlen=getlength()/employeelist.size();
			long end=personlen;
			for(int i=0;i<employeelist.size()-1;i++) {
				set.insert(start,end,employeelist.get(i));
				start=end;
				end+=personlen;
			}
			set.insert(start, getlength(), employeelist.get(employeelist.size()-1));
			System.out.println("排班完成");
		}
	}
	
	/**
	 * 将当前时间转为日期
	 * @param time
	 * @return
	 */
	public Date timetoDate(long time) {
		Date temp=new Date(startdate.getdata());
		temp.addday(time);
		return temp;
	}
	
	public long getlength() {
		return startdate.betweenday(enddate);
	}
	
	public void printDutyRoster() {
		System.out.println("开始日期\t\t结束日期\t\t姓名\t职位\t电话");
		for(MyMap<Employee> temp:set.arrangestart()) {
			Date tempdate=new Date(startdate.getdata());
			Date tempdate1=new Date(startdate.getdata());
			tempdate.addday(temp.start);
			System.out.print(tempdate.toString()+"\t");
			tempdate1.addday(temp.end);
			System.out.print(tempdate1.toString()+"\t");
			System.out.print(temp.label.toString());
			System.out.println();
		}
	}	
	
	public void printEmployeelist() {
		System.out.println("姓名\t职位\t电话\t是否排班");
		for(int i=0;i<employeelist.size();i++) {
			Employee temp=employeelist.get(i);
			System.out.print(temp.toString()+"\t");
			if(set.labels().contains(temp)) {
				System.out.println("已排班");
			}
			else {
				System.out.println("未排班");
			}
		}
	}
	
	/**
	 * 客户端程序
	 * @param args
	 */
	public static void main(String args[]) {
		int flag=0;
		int choice;
		APIs<Employee> api=new APIs<>();
		Scanner input=new Scanner(System.in);
		DutyRosterApp roster=new DutyRosterApp();
		System.out.println("1 手动输入");
		System.out.println("2 从文件读入");
		while(!input.hasNextInt()) {
			System.out.println("请输入符合要求的整数");
			input.next();
		}
		choice=input.nextInt();
		if(choice==2) {
			System.out.println("请输入1~8选择读入的文件");
			choice=input.nextInt();
			try {
				roster.ReadFile(new File("src/DutyRosterTxt/test"+choice+".txt"));
				roster.printDutyRoster();
			} catch (IOException e) {
				e.printStackTrace();
			}
			input.close();
		}
		else if(choice==1) {
			while(true) {
				if(flag==0) {
					System.out.println("输入1开始设置开始日期");
				}
				else if(flag==1) {
					System.out.println("输入2开始设置结束日期");
				}
				else {
					System.out.print("开始日期:");
					roster.printstartdate();
					System.out.print("结束日期(不包含该天）:");
					roster.printenddate();
					System.out.println("3  添加员工");
					System.out.println("4  删除员工");
					System.out.println("5  指定员工排班");
					System.out.println("6  打印排班表");
					System.out.println("7  检查是否排满");
					System.out.println("8  自动生成排班表");
					System.out.println("9  打印员工表");
					System.out.println("0 退出");
					System.out.println("请输入3~9选择功能：");
				}
				while(!input.hasNextInt()) {
					System.out.println("请输入符合要求的整数");
					input.next();
				}
				int year;
				int month;
				int day;
				int endyear;
				int endmonth;
				int endday;
				String name;
				String position;
				String phone;
				choice=input.nextInt();
				switch(choice) {
				case 1:
					if(flag==0) {
						flag++;
						System.out.println("请输入年：");
						year=input.nextInt();
						System.out.println("请输入月：");
						month=input.nextInt();
						System.out.println("请输入日：");
						day=input.nextInt();
						roster.setstartdate(year,month,day);
					}
					break;
				case 2:
					if(flag==1) {
						System.out.println("请输入年：");
						year=input.nextInt();
						System.out.println("请输入月：");
						month=input.nextInt();
						System.out.println("请输入日：");
						day=input.nextInt();
						roster.setenddate(year,month,day);
						if(!roster.checkdate()) {
							System.out.println("结束日期必须大于开始日期，请重新设置");
						}
						else {
							flag++;
						}
					}
					break;
				case 3:
					if(flag==2) {
						System.out.println("请输入员工姓名：");
						name=input.next();
						System.out.println("请输入员工职位：");
						position=input.next();
						System.out.println("请输入员工电话：");
						phone=input.next();
						roster.addemployee(name, position, phone);
					}
					break;
				case 4:
					if(flag==2) {
						System.out.println("请输入要删除员工的姓名：");
						name=input.next();
						if(roster.deleteemployee(name)) {
							System.out.println("删除成功");
						}
						else {
							System.out.println("删除失败");
						}
					}
					break;
				case 5:
					if(flag==2) {
						System.out.println("请输入员工姓名：");
						name=input.next();
						if(roster.getemployeeindex(name)==-1) {
							System.out.println("员工不存在");
						}
						else {
							System.out.println("请输入该员工开始值班的时间：");
							System.out.println("请输入年：");
							year=input.nextInt();
							System.out.println("请输入月：");
							month=input.nextInt();
							System.out.println("请输入日：");
							day=input.nextInt();
							System.out.println("请输入该员工结束值班的时间：");
							System.out.println("请输入年：");
							endyear=input.nextInt();
							System.out.println("请输入月：");
							endmonth=input.nextInt();
							System.out.println("请输入日：");
							endday=input.nextInt();
							roster.insert(name, year, month, day, endyear,endmonth,endday);
						}
					}
					break;
				case 6:
					if(flag==2) {
						roster.printDutyRoster();
					}
					break;
				case 7:
					if(flag==2) {
						double freerate=api.calcFreeTimeRatio(roster.getIntervalSet(),roster.getlength());
						if(freerate==0) {
							System.out.println("已排满");
						}
						else {
							System.out.println("未排满");
							System.out.println("空闲率为："+freerate);
							System.out.println("空闲时间段为：");
							for(MyMap<Employee>temp:api.getfreetime(roster.getIntervalSet().arrangestart(), 0, roster.getlength())) {
								System.out.println(roster.timetoDate(temp.start).toString()+"\t"+roster.timetoDate(temp.end).toString());
							}
						}
					}
					break;
				case 8:
					if(flag==2) {
						roster.clear();
						roster.autoinsert();
					}
					break;
				case 9:
					if(flag==2) {
						roster.printEmployeelist();
					}
					break;
				case 0:
					input.close();
					System.exit(0);
				}
				System.out.println();
			}
		}	
		else {
			System.out.println("请输入符合要求的整数");
		}
	}
}