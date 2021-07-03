package BasicInterfaceImpl;
import static org.junit.Assert.*;

import org.junit.Test;

import BasicInterface.IntervalSetTest;

public class CommonIntervalSetTest extends IntervalSetTest {

	@Test
	public void testtoString() {
		CommonIntervalSet<String>temp=new CommonIntervalSet<>();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		assertEquals("A=[10,20],B=[10,20],C=[30,40]",temp.toString());
	}
}
