package CourseSchedule;

/**
 * 该ADT是不可变的
 * @author 魏志豪
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
	 * 获得课程id
	 * @return
	 */
	public long getcourseid() {
		return courseid;
	}
	
	/**
	 * 获得课程名称
	 * @return
	 */
	public String getcoursename() {
		return coursename;
	}
	
	/**
	 * 获得教师名字
	 * @return
	 */
	public String getteachername() {
		return teachername;
	}
	
	/**
	 * 获得上课地点
	 * @return
	 */
	public String getposition() {
		return position;
	}
	
	/**
	 * 获得周学时数
	 * @return
	 */
	public long getweeklyhours() {
		return weeklyhours;
	}
	
	/**
	 * 根据课程id判断两个课程是否相同
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
		return "课程id:"+courseid+"\t课程名称:"+coursename+"\t授课教师:"+teachername+"\t上课地点:"+position;
	}
}