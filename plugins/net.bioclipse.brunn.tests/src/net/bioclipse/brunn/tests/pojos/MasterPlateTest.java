package net.bioclipse.brunn.tests.pojos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.Tools;

import static org.junit.Assert.*;

public class MasterPlateTest extends BaseTest {

	public MasterPlateTest() {
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
		
		MasterPlate masterPlate2 = masterPlate.deepCopy();
		
		assertEquals(  masterPlate, masterPlate2 );
		assertNotSame( masterPlate, masterPlate2 );
		
		assertEquals(  masterPlate.getWells(), masterPlate2.getWells() );
		assertFalse( Tools.inludesSame(masterPlate.getWells(), masterPlate2.getWells()) );
		
		assertEquals(  masterPlate.getPlateFunctions(), masterPlate2.getPlateFunctions() );
		assertFalse( Tools.inludesSame(masterPlate.getPlateFunctions(), masterPlate2.getPlateFunctions()) );
		
		assertEquals(  masterPlate.getAbstractAnnotationInstances(), masterPlate2.getAbstractAnnotationInstances() );
		assertFalse( Tools.inludesSame( masterPlate.getAbstractAnnotationInstances(), masterPlate2.getAbstractAnnotationInstances()) );
		
	}
	
	@Test
	public void testDeepDelete(){
		
		assertFalse( masterPlate.isDeleted() );
		for ( Well well : masterPlate.getWells() ) {
	        assertFalse( well.isDeleted() );
		}
		for ( PlateFunction function : masterPlate.getPlateFunctions() ) {
			assertFalse( function.isDeleted() );
		}
		for ( AbstractAnnotationInstance annotationInstance : masterPlate.getAbstractAnnotationInstances() ) {
			assertFalse( annotationInstance.isDeleted() );
		}
		
		masterPlate.delete();
		
		assertTrue( masterPlate.isDeleted() );
		for ( Well well : masterPlate.getWells() ) {
	        assertTrue( well.isDeleted() );
		}
		for ( PlateFunction function : masterPlate.getPlateFunctions() ) {
			assertTrue( function.isDeleted() );
		}
		for ( AbstractAnnotationInstance annotationInstance : masterPlate.getAbstractAnnotationInstances() ) {
			assertTrue( annotationInstance.isDeleted() );
		}
	}
}
