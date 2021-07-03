package DutyRoster;
public class Employee{
	private final String name;
	private final String position;
	private final String phone;
	
	public Employee(String name,String position,String phone) {
		this.name=name;
		this.position=position;
		this.phone=phone;
	}
	
	/**
	 * 获得员工名字
	 * @return
	 */
	public String getname() {
		return name;
	}
	
	/**
	 * 获得员工职位
	 * @return
	 */
	public String getposition() {
		return position;
	}
	
	/**
	 * 获得员工电话
	 */
	public String getphone() {
		return phone;
	}
	
	@Override public String toString() {
		String str=name+"\t"+position+"\t"+phone;
		return str;
	}
}