package BasicInterface;
import java.util.List;
import java.util.Set;

import BasicInterfaceImpl.CommonMultiIntervalSet;
import CommonADT.MyMap;

public interface MultiIntervalSet<L>{
	public static <L> MultiIntervalSet<L> empty(){
		return new CommonMultiIntervalSet<L>();
	}
	
	public static <L> MultiIntervalSet<L> CreateMultiIntervalSet(IntervalSet<L> initial){
		CommonMultiIntervalSet<L> temp=new CommonMultiIntervalSet<L>();
		for(L label:initial.labels()) {
			temp.insert(initial.start(label), initial.end(label), label);
		}
		return temp;
	}
	
	/**
	 * ��label����һ��ʱ��
	 * @param start ��ʼʱ��
	 * @param end	����ʱ��
	 * @param label	��ǩ
	 */
	public void insert(long start,long end,L label);
	
	/**
	 * �������б�ǩ�ļ���
	 * @return
	 */
	public Set<L> labels();
	
	/**
	 * �Ƴ�label�������е�ʱ���
	 * @param label ��ǩ
	 * @return	��label���ڷ���true����label�����ڷ���false
	 */
	public boolean remove(L label);
	
	/**
	 * �õ�label��Ӧ������ʱ���
	 * @param label	��ǩ
	 * @return	ʱ��δ�С��������
	 */
	public IntervalSet<Integer> intervals(L label);
	
	/**
	 * �õ���Сʱ��
	 * @return
	 */
	public long getmin();
	
	/**
	 * �õ����ʱ��
	 * @return
	 */
	public long getmax();
	
	/**
	 * ����һ��������ǰʱ���label�ļ���
	 * @param currenttime
	 * @return
	 */
	public Set<L> containtime(long currenttime);
	
	/**
	 * ����һ������ʼʱ���С����˳�������label�б�,ͬһ��label�����ظ�����
	 * @return
	 */
	public List<MyMap<L>> arrangestart();
	
	/**
	 * ɾ������Ԫ��
	 */
	public void removeall();
}