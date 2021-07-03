package CommonADT;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ����һ���ɱ���������ͣ���������ʾʱ��
 * @author κ־��
 *
 */
public class Date{
	private Calendar calendar;
	
	//AF:ʱ�����ϵ�һ��
	//RI:�������ͱ����ǺϷ���
	//�����Կ���
	
	public Date() {
		calendar=new GregorianCalendar();
	}
	
	public Date(Calendar cal) {
		calendar=cal;
	}
	
	/**
	 * һ�����ж�����
	 * @param year
	 * @param month
	 * @return
	 */
	private int getdayofmonth(int year,int month) {
		int day=0;
		switch(month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day=31;
			break;
		case 2:
			if(year%4==0) {
				day=29;
			}
			else {
				day=28;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day=30;
			break;
		}
		return day;
	}
	
	/**
	 * ������������-��ǰ���ڵ�����
	 * @param a
	 * @return
	 */
	public long betweenday(Date a) {
		Calendar temp=a.getdata();
		long between=0;
		int ayear=temp.get(Calendar.YEAR);
		int amonth=temp.get(Calendar.MONTH)+1;
		int aday=temp.get(Calendar.DATE);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DATE);
		between+=(ayear-year)*365;
		for(int i=month;i<amonth;i++) {
			between+=getdayofmonth(year,i);
		}
		between+=aday-day;
		return between;
	}
	
	/**
	 * ������������-��ǰ���ڵ�Сʱ��
	 * @param a
	 * @return
	 */
	public long betweenhours(Date a) {
		long data1=a.getdata().getTimeInMillis();
		long data2=calendar.getTimeInMillis();
		long between= ((data1-data2)/(1000*3600));
		return between;
	}
	
	/**
	 * ��������
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public boolean setdate(int year,int month,int day) {
		if(year>=0&&month>=1&&month<=12&&day>=1&&day<=31) {
			calendar.set(year, month-1, day,0,0,0);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * ��������
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public boolean setdate(int year,int month,int day,int hours) {
		if(year>=0&&month>=1&&month<=12&&day>=1&&day<=31) {
			calendar.set(year, month-1, day,hours,0,0);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public Calendar getdata() {
		Calendar cal=new GregorianCalendar();
		cal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
		return cal;
	}
	
	/**
	 * ��������
	 * @param day
	 */
	public void addday(long day) {
		calendar.add(Calendar.DATE, (int) day);
	}
	
	/**
	 * ����Сʱ��
	 * @param day
	 */
	public void addhours(long hours) {
		calendar.add(Calendar.HOUR, (int) hours);
	}
	
	/**
	 * ���������
	 * @return
	 */
	public int getdayofweek() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public String gethourString() {
		return calendar.get(Calendar.HOUR_OF_DAY)+":00";
	}
	
	@Override public String toString() {
		return calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
	}
}