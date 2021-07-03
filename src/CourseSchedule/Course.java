package CourseSchedule;

/**
 * ��ADT�ǲ��ɱ��
 * @author κ־��
 *
 */
public class Course{
	private final long courseid;
	private final String coursename;
	private final String teachername;
	private final String position;
	private final long weeklyhours;
	
	public Course(long courseid,String coursename,String teachername,String position,long weeklyhours) {
		this.courseid=courseid;
		this.coursename=coursename;
		this.teachername=teachername;
		this.position=position;
		this.weeklyhours=weeklyhours;
	}
	
	/**
	 * ��ÿγ�id
	 * @return
	 */
	public long getcourseid() {
		return courseid;
	}
	
	/**
	 * ��ÿγ�����
	 * @return
	 */
	public String getcoursename() {
		return coursename;
	}
	
	/**
	 * ��ý�ʦ����
	 * @return
	 */
	public String getteachername() {
		return teachername;
	}
	
	/**
	 * ����Ͽεص�
	 * @return
	 */
	public String getposition() {
		return position;
	}
	
	/**
	 * �����ѧʱ��
	 * @return
	 */
	public long getweeklyhours() {
		return weeklyhours;
	}
	
	/**
	 * ���ݿγ�id�ж������γ��Ƿ���ͬ
	 * @param a
	 * @return
	 */
	public boolean equal(Course a) {
		if(courseid==a.getcourseid()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override public String toString() {
		return "�γ�id:"+courseid+"\t�γ�����:"+coursename+"\t�ڿν�ʦ:"+teachername+"\t�Ͽεص�:"+position;
	}
}