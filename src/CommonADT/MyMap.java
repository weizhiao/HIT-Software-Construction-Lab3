package CommonADT;
/**
 * ����һ���ɱ��������ͣ���������java�Դ���Map
 * @author κ־��
 *	
 * @param <L>
 */
public class MyMap<L>{
	public L label;
	public Long start;
	public Long end;
	
	
	
	/**
	 * �ж�������ͻʱ����Ƿ�һ��
	 * @param m
	 * @return
	 */
	public boolean conflicttimeequal(MyMap<L> m) {
		if(start==m.start&&end==m.end) {
			return true;
		}
		else {
			return false;
		}
	}
}