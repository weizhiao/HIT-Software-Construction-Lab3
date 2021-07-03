package BasicInterfaceImpl;

import static org.junit.Assert.*;

import org.junit.Test;

import BasicInterface.MultiIntervalSetTest;

public class CommonMultiIntervalSetTest extends MultiIntervalSetTest {
	
	@Test
	public void testtoString() {
		CommonMultiIntervalSet<String>temp=new CommonMultiIntervalSet<>();
		temp.insert(10, 20, "A");
		temp.insert(15, 25, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(30, 50, "A");
		assertEquals("A={0=[10,20]1=[30,50]}B={0=[10,20]}C={0=[30,40]}",temp.toString());
	}
}
