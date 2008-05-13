package net.bioclipse.brunn.tests.pojos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.tests.BaseTest;
import static org.junit.Assert.*;

public class PlateFunctionTest extends BaseTest {

	public PlateFunctionTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDeepCopy() {
		
		User tester = new User("tester");
		PlateFunction plateFunction = new PlateFunction(tester, "plateFunction", "", 12, 24, true, null);
		
		PlateFunction plateFunction2 = plateFunction.deepCopy();
		
		assertEquals(  plateFunction, plateFunction2 );
		assertNotSame( plateFunction, plateFunction2 );
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {

		int before = plate.getPlateFunctions().size();
		
		plm.createPlateFunction(tester, "", plate, "");

		assertEquals(before + 1,plate.getPlateFunctions().size());
		assertNotNull(plate.getPlateFunctions().toArray()[0]);
		
		PlateFunction plateFunction = (PlateFunction)plate.getPlateFunctions().toArray()[0];
		
		assertTrue(plate.getPlateFunctions().contains(plateFunction));
		assertTrue(plateFunction.getPlate() == plate);
	}
}
