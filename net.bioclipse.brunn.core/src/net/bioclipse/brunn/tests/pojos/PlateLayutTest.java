package net.bioclipse.brunn.tests.pojos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.Tools;
import static org.junit.Assert.*;

/**
 * Tests the functionality of the persistant class 
 * <code>PlateLayoutTest</code>
 * 
 * @author jonathan
 *
 */
public class PlateLayutTest extends BaseTest {

	public PlateLayutTest() {
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
	public void testEqualsAndHashCode() {
		
		User creator = new User("tester");;
		PlateType plateType = new PlateType(creator, 9, 6, "type");
		
		PlateLayout plateLayout1 = new PlateLayout(
				creator, "test plateLayout", plateType);
		PlateLayout plateLayout2 = plateLayout1.deepCopy();
		PlateLayout plateLayout3 = new PlateLayout(
				new User("other guy"), "test plateLayout", plateType);
		
		assertEquals(plateLayout1, plateLayout2);
		assertEquals(plateLayout2, plateLayout1);
		assertFalse(plateLayout1.equals(plateLayout3));
		assertFalse(plateLayout3.equals(plateLayout1));
		assertEquals(plateLayout1.hashCode(), plateLayout2.hashCode());
	}
	
	@Test
	public void testDeepCopy() {
		
		PlateLayout plateLayout2 = plateLayout.deepCopy();
		
		assertEquals(  plateLayout, plateLayout2 );
		assertNotSame( plateLayout, plateLayout2 );
		
		assertFalse(  Tools.inludesSame(plateLayout.getLayoutWells(), plateLayout2.getLayoutWells() ));
		assertNotSame( plateLayout.getLayoutWells(), plateLayout2.getLayoutWells() );
		
		assertFalse(  Tools.inludesSame(plateLayout.getPlateFunctions(), plateLayout2.getPlateFunctions() ));
		assertNotSame( plateLayout.getPlateFunctions(), plateLayout2.getPlateFunctions() );
		
		assertFalse(  Tools.inludesSame(plateLayout.getAbstractAnnotationInstances(), plateLayout2.getAbstractAnnotationInstances() ));
		assertNotSame( plateLayout.getAbstractAnnotationInstances(), plateLayout2.getAbstractAnnotationInstances() );
	}
	
	@Test
	public void testDeepDelete(){
		
		assertFalse(plateLayout.isDeleted());
		for ( LayoutWell well : plateLayout.getLayoutWells() ) {
	        assertFalse(well.isDeleted());
        }
		for ( PlateFunction function : plateLayout.getPlateFunctions() ) {
	        assertFalse(function.isDeleted());
        }
		for ( AbstractAnnotationInstance annotationInstance : plateLayout.getAbstractAnnotationInstances() ) {
	        assertFalse(annotationInstance.isDeleted());
        }
		assertFalse(plateLayout.getPlateType().isDeleted());
		
		plateLayout.delete();
		
		assertTrue(plateLayout.isDeleted());
		for ( LayoutWell well : plateLayout.getLayoutWells() ) {
	        assertTrue(well.isDeleted());
        }
		for ( PlateFunction function : plateLayout.getPlateFunctions() ) {
	        assertTrue(function.isDeleted());
        }
		for ( AbstractAnnotationInstance annotationInstance : plateLayout.getAbstractAnnotationInstances() ) {
	        assertTrue(annotationInstance.isDeleted());
        }
		assertFalse(plateLayout.getPlateType().isDeleted());
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		for (LayoutWell well : plateLayout.getLayoutWells()) {
			assertSame(well.getPlateLayout(), plateLayout);
        }
		
		assertTrue(plateLayout.getPlateType().getPlateLayouts().contains(plateLayout));
	}
}
