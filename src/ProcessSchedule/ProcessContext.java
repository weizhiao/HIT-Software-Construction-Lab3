package ProcessSchedule;

public class ProcessContext{
	private Process p;
	private boolean status;
	private long runtime;
	
	//AF:����ִ�е������Ϣ
	//RI:runtime>=0
	//ʹ�ò�����
	
	public ProcessContext(Process p) {
		this.p=p;
		status=false;
		runtime=0;
	}
	
	/**
	 * ���н���
	 * @param time
	 * @return ���������г�ʱ����false
	 */
	public boolean run(long time) {
		runtime+=time;
		if(runtime>p.getmaxtime()) {
			System.out.println("�������г�ʱ");
			return false;
		}
		else if(runtime>=p.getmintime()) {
			status=true;
		}
		return true;
	}
	
	/**
	 * ��ý��̵�״̬
	 * @return
	 */
	public boolean isfinished() {
		return status;
	}
	
	/**
	 * ��ý���ʣ������ִ��ʱ��
	 * @return
	 */
	public long maxremaintime() {
		return p.getmaxtime()-runtime;
	}
	
	/**
	 * ��þ������
	 * @return
	 */
	public Process getprocess() {
		return p;
	}
	
	/**
	 * �ָ���ʼ״̬
	 */
	public void clear() {
		status=false;
		runtime=0;
	}
}