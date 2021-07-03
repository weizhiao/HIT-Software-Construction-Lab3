package ProcessSchedule;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessContextTest {

	@Test
	public void testrun() {
		Process temp=new Process(1,"A",10,20);
		ProcessContext c=new ProcessContext(temp);
		assertFalse(c.isfinished());
		c.run(15);
		assertTrue(c.isfinished());
		assertFalse(c.run(30));
	}
	
	@Test
	public void testmaxremaintime() {
		Process temp=new Process(1,"A",10,20);
		ProcessContext c=new ProcessContext(temp);
		c.run(5);
		assertEquals(15,c.maxremaintime());
	}
}
