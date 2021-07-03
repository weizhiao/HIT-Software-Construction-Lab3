package ProcessSchedule;

public class ProcessContext{
	private Process p;
	private boolean status;
	private long runtime;
	
	//AF:进程执行的相关信息
	//RI:runtime>=0
	//使用不变量
	
	public ProcessContext(Process p) {
		this.p=p;
		status=false;
		runtime=0;
	}
	
	/**
	 * 运行进程
	 * @param time
	 * @return 若进程运行超时返回false
	 */
	public boolean run(long time) {
		runtime+=time;
		if(runtime>p.getmaxtime()) {
			System.out.println("进程运行超时");
			return false;
		}
		else if(runtime>=p.getmintime()) {
			status=true;
		}
		return true;
	}
	
	/**
	 * 获得进程的状态
	 * @return
	 */
	public boolean isfinished() {
		return status;
	}
	
	/**
	 * 获得进程剩余的最大执行时间
	 * @return
	 */
	public long maxremaintime() {
		return p.getmaxtime()-runtime;
	}
	
	/**
	 * 获得具体进程
	 * @return
	 */
	public Process getprocess() {
		return p;
	}
	
	/**
	 * 恢复初始状态
	 */
	public void clear() {
		status=false;
		runtime=0;
	}
}