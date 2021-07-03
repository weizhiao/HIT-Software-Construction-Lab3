package ProcessSchedule;
public class Process{
	private final long pid;
	private final long mintime;
	private final long maxtime;
	private final String name;
	
	//AF:进程
	//RI:时间大于0
	//返回不变量
	
	public Process(long pid,String name,long mintime,long maxtime) {
		this.pid=pid;
		this.name=name;
		this.mintime=mintime;
		this.maxtime=maxtime;
	}
	
	/**
	 *	获得pid 
	 * @return
	 */
	public long getpid() {
		return pid;
	}
	
	/**
	 * 获得最短执行时间
	 * @return
	 */
	public long getmintime() {
		return mintime;
	}
	
	/**
	 * 获得最长执行时间
	 * @return
	 */
	public long getmaxtime() {
		return maxtime;
	}
	
	/**
	 * 获得进程名称
	 * @return
	 */
	public String getname() {
		return name;
	}
	
	/**
	 * 若两个进程的pid相同，则这两个进程相同
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
		String str="进程ID:"+pid+"\t"+"进程名称:"+name;
		return str;
	}
}