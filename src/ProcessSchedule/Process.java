package ProcessSchedule;
public class Process{
	private final long pid;
	private final long mintime;
	private final long maxtime;
	private final String name;
	
	//AF:����
	//RI:ʱ�����0
	//���ز�����
	
	public Process(long pid,String name,long mintime,long maxtime) {
		this.pid=pid;
		this.name=name;
		this.mintime=mintime;
		this.maxtime=maxtime;
	}
	
	/**
	 *	���pid 
	 * @return
	 */
	public long getpid() {
		return pid;
	}
	
	/**
	 * ������ִ��ʱ��
	 * @return
	 */
	public long getmintime() {
		return mintime;
	}
	
	/**
	 * ����ִ��ʱ��
	 * @return
	 */
	public long getmaxtime() {
		return maxtime;
	}
	
	/**
	 * ��ý�������
	 * @return
	 */
	public String getname() {
		return name;
	}
	
	/**
	 * ���������̵�pid��ͬ����������������ͬ
	 * @param a
	 * @return
	 */
	public boolean equal(Process a) {
		if(this.getpid()==a.getpid()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override public String toString() {
		String str="����ID:"+pid+"\t"+"��������:"+name;
		return str;
	}
}