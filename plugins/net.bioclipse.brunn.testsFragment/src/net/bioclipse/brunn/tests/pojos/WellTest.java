package net.bioclipse.brunn.tests.pojos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.pojos.WellFunction;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.Tools;
import static org.junit.Assert.*;


/**
 * Tests the functionality of the persistant class 
 * <code>Well</code>
 * 
 * @author jonathan
 *
 */
public class WellTest extends BaseTest {

	public WellTest() {
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
	public void testEqualsAndHashCode() {
		
		User creator = new User("tester");
		Well well1 = new Well(creator, "test well", 12, 'a', null);
		Well well2 = well1.deepCopy();
		Well well3 = new Well(new User("other guy"), "test well", 11, 'c', null);
		
		assertEquals(well1, well2);
		assertEquals(well2, well1);
		assertFalse(well1.equals(well3));
		assertFalse(well3.equals(well1));
		assertEquals(well1.hashCode(), well2.hashCode());
	}

	@Test
	public void testDeepCopy() {
		
		User creator = new User("tester");
		Well well = new Well(creator, "test well", 12, 'a', null);
		well.getWellFunctions().add(new WellFunction(creator, "testfunction", "100", well));
		
		Well copy = well.deepCopy();
		
		assertEquals(well, copy);
		assertNotSame(well, copy);
		
		assertNotSame(well.getSampleContainer(), copy.getSampleContainer());
		
		assertFalse( Tools.inludesSame( well.getSampleMarkers(), copy.getSampleMarkers() ) );
	    assertFalse( Tools.inludesSame( well.getWellFunctions(), copy.getWellFunctions() ) );
        
	}
	
	@Test
	public void testDeepDelete(){
		
		Well well = (Well)plate.getWells().toArray()[0];
		
		assertFalse(well.isDeleted());
		assertFalse(well.getSampleContainer().isDeleted());
		for (SampleMarker sampleMarker : well.getSampleMarkers()) {
	        assertFalse(sampleMarker.isDeleted());
        }
		assertFalse(well.getPlate().isDeleted());
		
		well.delete();
		
		assertTrue(well.isDeleted());
		assertTrue(well.getSampleContainer().isDeleted());
		for (SampleMarker sampleMarker : well.getSampleMarkers()) {
	        assertTrue(sampleMarker.isDeleted());
        }
		assertFalse(well.getPlate().isDeleted());
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		Well well = (Well)plate.getWells().toArray()[0];
		SampleMarker sampleMarker = new SampleMarker(tester, "sampleMarker", null, well);
		WellFunction wellFunction = new WellFunction(tester, "wellFunction", "", well);
		
		assertTrue(well.getPlate() == plate);
		
		assertTrue(sampleMarker.getWell() == well);
		assertTrue(well.getSampleMarkers().contains(sampleMarker));
		
		assertTrue(well.getSampleContainer() != null);
		assertTrue(well.getSampleContainer().getWell() == well);
		
		assertTrue(well.getWellFunctions().contains(wellFunction));
		for (WellFunction function : well.getWellFunctions()) {
	        assertTrue(function.getWell() == well);
        }	
	}
}
