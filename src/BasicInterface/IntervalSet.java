package BasicInterface;
import java.util.List;
import java.util.Set;

import BasicInterfaceImpl.CommonIntervalSet;
import CommonADT.MyMap;

public interface IntervalSet<L>{
	public static <L> IntervalSet<L> empty() {
		return new CommonIntervalSet<L>();
	}
	
	/**
	 * ��label����һ��ʱ�䣬��label�����ظ�
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
	 * �Ƴ�label�����Ӧ��ʱ���
	 * @param label ��ǩ
	 * @return ��label���ڷ���true����label�����ڷ���false
	 */
	public boolean remove(L label);
	
	/**
	 * �õ�label��Ӧ����ʼʱ��
	 * @param label	��ǩ
	 * @return ��label���ڷ���label����ʼʱ�䣻��label�����ڷ���-1
	 */
	public long start(L label);
	
	/**
	 * �õ�label��Ӧ�Ľ���ʱ��
	 * @param label	��ǩ
	 * @return ��label���ڷ���label�Ľ���ʱ�䣻��label�����ڷ���-1
	 */
	public long end(L label);
	
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
	 * ����һ������ʼʱ���С����˳�������label�б�
	 * @return
	 */
	public List<MyMap<L>> arrangestart();
	
	/**
	 * ɾ������Ԫ��
	 */
	public void removeall();
}