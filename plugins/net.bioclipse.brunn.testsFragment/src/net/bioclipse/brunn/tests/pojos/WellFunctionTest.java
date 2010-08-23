package net.bioclipse.brunn.tests.pojos;

import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.pojos.WellFunction;
import junit.framework.TestCase;
import static org.junit.Assert.*;

public class WellFunctionTest {

	public WellFunctionTest() {
	}

	@Test
	public void testDeepCopy() {
		
		User tester = new User("tester");
		WellFunction wellFunction = new WellFunction(tester, "wellFunction", "", new Well());
		
		WellFunction wellFunction2 = wellFunction.deepCopy();
		
		assertEquals(wellFunction, wellFunction2);
		assertNotSame(wellFunction, wellFunction2);
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester");
		Well well = new Well(tester, "well", 4,'d', null);
		WellFunction wellFunction = new WellFunction(tester, "wellFunction", "", well);
		
		assertTrue(well.getWellFunctions().contains(wellFunction));
		assertTrue(wellFunction.getWell() == well);
	}
}
