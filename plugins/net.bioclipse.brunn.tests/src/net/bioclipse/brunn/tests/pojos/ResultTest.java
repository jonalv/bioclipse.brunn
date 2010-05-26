package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

public class ResultTest {

	public ResultTest() {

	}

	@Test
	public void testDeepCopy() {
		
		double[] resultValue = {0.3, 0.5, 0.34};
		Result result = new Result(new User("tester"), "result", resultValue, 1);
		
		Result copy = result.deepCopy();
		
		assertEquals(copy, result);
		assertNotSame(copy, result);
	}
}
