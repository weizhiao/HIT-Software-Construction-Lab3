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
	 * ���Ա������
	 * @return
	 */
	public String getname() {
		return name;
	}
	
	/**
	 * ���Ա��ְλ
	 * @return
	 */
	public String getposition() {
		return position;
	}
	
	/**
	 * ���Ա���绰
	 */
	public String getphone() {
		return phone;
	}
	
	@Override public String toString() {
		String str=name+"\t"+position+"\t"+phone;
		return str;
	}
}