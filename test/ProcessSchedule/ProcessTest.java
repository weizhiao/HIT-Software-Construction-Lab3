package ProcessSchedule;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessTest {

	@Test
	public void testequal() {
		Process temp=new Process(1,"A",10,20);
		Process temp1=new Process(0,"B",10,20);
		Process temp2=new Process(3,"A",10,20);
		Process temp3=new Process(1,"B",10,20);
		assertTrue(temp.equal(temp3));
		assertFalse(temp.equal(temp1));
		assertFalse(temp.equal(temp2));
	}

}
