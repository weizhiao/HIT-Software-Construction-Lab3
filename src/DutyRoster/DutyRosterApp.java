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
	
	//AF:Ա�����Ű�ʱ��ĵѿ����˻�
	//RI:Ա���ű�󲻿�ɾ��
	//ʹ�÷���ʽ����
	
	/**
	 * ��ʼ���Ű��������Ա��
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
	 * ���ݸ�ʽ���Ա��
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
				System.out.println("�ļ���ʽ����:��Ա�������ظ�");
				System.exit(0);
			}
			position=m.group(2);
			if(m.group(3).length()==3&&m.group(4).length()==4&&m.group(5).length()==4) {
				phone+=m.group(3)+m.group(4)+m.group(5);
				addemployee(name,position,phone);
			}
			else {
				System.out.println("�ļ���ʽ����:�ֻ��� 11 λ����Ϊ���Σ�3-4-4��");
				System.exit(0);
			}
		}
	}
	
	/**
	 * �����ļ���������
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
			System.out.println("�ļ���ʽ����");
			System.exit(0);
		}
	}
	
	/**
	 * �����ļ��Ű�
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
				System.out.println("�ļ���ʽ����:������ Roster �ڵ�Ա���������� Employee �����ж���");
				System.exit(1);
			}
			else
			insert(name,startyear,startmonth,startday,endyear,endmonth,endday);
		}
		else {
			System.out.println("�ļ���ʽ����");
			System.exit(0);
		}
	}
	
	/**
	 * ���ļ������Ű��
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
				employees=employeestr.split("��");
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
				rosters=rosterstr.split("��");
				for(int i=1;i<rosters.length;i++) {
					insertfile(rosters[i]);
				}
			}
		}
		return true;
	}
	
	/**
	 * �����Ű࿪ʼ����
	 * @param year
	 * @param month
	 * @param day
	 * @return ������������ղ����Ϲ淶����false����������ʧ�ܣ����Ϲ淶�򷵻�
	 * true���������óɹ�
	 */
	public boolean setstartdate(int year,int month,int day) {
		return startdate.setdate(year, month, day);
	}
	
	/**
	 * ��ӡ��ʼ����
	 */
	public void printstartdate() {
		System.out.println(startdate.toString());
	}
	
	/**
	 * ��ӡ��������
	 */
	public void printenddate() {
		System.out.println(enddate.toString());
	}
	
	/**
	 * �����Ű��������
	 * @param year
	 * @param month
	 * @param day
	 * @return ������������ղ����Ϲ淶����false����������ʧ�ܣ����Ϲ淶�򷵻�
	 * true���������óɹ�
	 */
	public boolean setenddate(int year,int month,int day) {
		return enddate.setdate(year, month, day);
	}
	/**
	 * �����������Ƿ�ȿ�ʼ���ڴ�
	 * @return ���Ϸ�����true�������Ϸ�����false
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
	 * ���Ա��
	 * @param name
	 * @param position
	 * @param phone
	 */
	public void addemployee(String name,String position,String phone) {
			Employee temp=new Employee(name,position,phone);
			employeelist.add(temp);
	}
	
	/**
	 * ɾ��Ա���������Ű����Ĳ���ɾ��
	 * @param name
	 * @return ɾ���ɹ�����true��ɾ��ʧ�ܷ���false
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
	 * ����Ա�����±�
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
	 * ��ָ��Ա���Ű�
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
			System.out.println("�����Ű�ʱ����");
		}
		else {
			int index=getemployeeindex(name);
			set.insert(start, end, employeelist.get(index));
		}
	}
	
	/**
	 * �Զ��Ű࣬��ʹÿ�����ŵ������������
	 */
	public void autoinsert() {
		long start=0;
		if(employeelist.size()==0) {
			System.out.println("�������Ա��");
			System.out.println("�Ű�ʧ��");
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
			System.out.println("�Ű����");
		}
	}
	
	/**
	 * ����ǰʱ��תΪ����
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
		System.out.println("��ʼ����\t\t��������\t\t����\tְλ\t�绰");
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
		System.out.println("����\tְλ\t�绰\t�Ƿ��Ű�");
		for(int i=0;i<employeelist.size();i++) {
			Employee temp=employeelist.get(i);
			System.out.print(temp.toString()+"\t");
			if(set.labels().contains(temp)) {
				System.out.println("���Ű�");
			}
			else {
				System.out.println("δ�Ű�");
			}
		}
	}
	
	/**
	 * �ͻ��˳���
	 * @param args
	 */
	public static void main(String args[]) {
		int flag=0;
		int choice;
		APIs<Employee> api=new APIs<>();
		Scanner input=new Scanner(System.in);
		DutyRosterApp roster=new DutyRosterApp();
		System.out.println("1 �ֶ�����");
		System.out.println("2 ���ļ�����");
		while(!input.hasNextInt()) {
			System.out.println("���������Ҫ�������");
			input.next();
		}
		choice=input.nextInt();
		if(choice==2) {
			System.out.println("������1~8ѡ�������ļ�");
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
					System.out.println("����1��ʼ���ÿ�ʼ����");
				}
				else if(flag==1) {
					System.out.println("����2��ʼ���ý�������");
				}
				else {
					System.out.print("��ʼ����:");
					roster.printstartdate();
					System.out.print("��������(���������죩:");
					roster.printenddate();
					System.out.println("3  ���Ա��");
					System.out.println("4  ɾ��Ա��");
					System.out.println("5  ָ��Ա���Ű�");
					System.out.println("6  ��ӡ�Ű��");
					System.out.println("7  ����Ƿ�����");
					System.out.println("8  �Զ������Ű��");
					System.out.println("9  ��ӡԱ����");
					System.out.println("0 �˳�");
					System.out.println("������3~9ѡ���ܣ�");
				}
				while(!input.hasNextInt()) {
					System.out.println("���������Ҫ�������");
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
						System.out.println("�������꣺");
						year=input.nextInt();
						System.out.println("�������£�");
						month=input.nextInt();
						System.out.println("�������գ�");
						day=input.nextInt();
						roster.setstartdate(year,month,day);
					}
					break;
				case 2:
					if(flag==1) {
						System.out.println("�������꣺");
						year=input.nextInt();
						System.out.println("�������£�");
						month=input.nextInt();
						System.out.println("�������գ�");
						day=input.nextInt();
						roster.setenddate(year,month,day);
						if(!roster.checkdate()) {
							System.out.println("�������ڱ�����ڿ�ʼ���ڣ�����������");
						}
						else {
							flag++;
						}
					}
					break;
				case 3:
					if(flag==2) {
						System.out.println("������Ա��������");
						name=input.next();
						System.out.println("������Ա��ְλ��");
						position=input.next();
						System.out.println("������Ա���绰��");
						phone=input.next();
						roster.addemployee(name, position, phone);
					}
					break;
				case 4:
					if(flag==2) {
						System.out.println("������Ҫɾ��Ա����������");
						name=input.next();
						if(roster.deleteemployee(name)) {
							System.out.println("ɾ���ɹ�");
						}
						else {
							System.out.println("ɾ��ʧ��");
						}
					}
					break;
				case 5:
					if(flag==2) {
						System.out.println("������Ա��������");
						name=input.next();
						if(roster.getemployeeindex(name)==-1) {
							System.out.println("Ա��������");
						}
						else {
							System.out.println("�������Ա����ʼֵ���ʱ�䣺");
							System.out.println("�������꣺");
							year=input.nextInt();
							System.out.println("�������£�");
							month=input.nextInt();
							System.out.println("�������գ�");
							day=input.nextInt();
							System.out.println("�������Ա������ֵ���ʱ�䣺");
							System.out.println("�������꣺");
							endyear=input.nextInt();
							System.out.println("�������£�");
							endmonth=input.nextInt();
							System.out.println("�������գ�");
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
							System.out.println("������");
						}
						else {
							System.out.println("δ����");
							System.out.println("������Ϊ��"+freerate);
							System.out.println("����ʱ���Ϊ��");
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
			System.out.println("���������Ҫ�������");
		}
	}
}