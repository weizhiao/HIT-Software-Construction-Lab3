package CommonADT;
public class MyMap<L>{
	public L label;
	public Long start;
	public Long end;
	
	/**
	 * 判断两个冲突时间段是否一样
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