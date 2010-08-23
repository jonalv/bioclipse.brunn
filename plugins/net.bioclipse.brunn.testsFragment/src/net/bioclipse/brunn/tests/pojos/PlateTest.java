package net.bioclipse.brunn.tests.pojos;

import java.sql.Timestamp;

import org.eclipse.swt.graphics.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.business.annotation.IAnnotationManager;
import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.AbstractPlate;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.Tools;
import static org.junit.Assert.*;

public class PlateTest extends BaseTest {

	public PlateTest() {
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
	public void testDeepDelete(){
		
		assertFalse(plate.isDeleted());
		for ( Well well : plate.getWells() ) {
	        assertFalse(well.isDeleted());
        }
		for ( PlateFunction function : plate.getPlateFunctions() ) {
	        assertFalse(function.isDeleted());
        }
		for ( AbstractAnnotationInstance annotationInstance : plate.getAbstractAnnotationInstances() ) {
	        assertFalse(annotationInstance.isDeleted());
        }
		
		plate.delete();
		
		assertTrue(plate.isDeleted());
		for ( Well well : plate.getWells() ) {
	        assertTrue(well.isDeleted());
        }
		for ( PlateFunction function : plate.getPlateFunctions() ) {
	        assertTrue(function.isDeleted());
        }
		for ( AbstractAnnotationInstance annotationInstance : plate.getAbstractAnnotationInstances() ) {
	        assertTrue(annotationInstance.isDeleted());
        }
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		
		Plate plate = pm.getPlate(pm.createPlate(tester, "plate2", "", folder,  masterPlate, cellOrigin, new Timestamp(0)));

		assertTrue(folder.getObjects().contains(plate));

		pm.createPlateFunction(tester, "pf", plate, "");
		
		assertTrue(plate.getWells().size() != 0);
		for (Well well : plate.getWells()) {
	        assertTrue(well.getPlate() == plate);
        }
		
		assertTrue(plate.getPlateFunctions().size() != 0);
		for (PlateFunction function : plate.getPlateFunctions()) {
	        assertTrue(function.getPlate() == plate);
        }
		
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
		
		Annotation annotation = am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, ""));
		am.annotate(tester, plate, annotation, "testing");
		
		assertTrue(plate.getAbstractAnnotationInstances().size() != 0);
		for (AbstractAnnotationInstance ai : plate.getAbstractAnnotationInstances()) {
	        assertEquals(ai.getAbstractAnnotatableObject(), plate);
        }
	}
	
	@Test
	public void testGetWellFunctions() {
		final String FUNCTIONNAME = "new we11Function";
		plm.createWellFunction(tester, FUNCTIONNAME, plateLayout.getLayoutWell(new Point(1,1)), "5");
		MasterPlate m = pm.getMasterPlate( pm.createMasterPlate(tester, "", plateLayout, folder, 1) );
		Plate p = pm.getPlate( pm.createPlate(tester, "", "", folder, m, cellOrigin, new Timestamp(System.currentTimeMillis())));
		assertTrue( p.getWellFunctionNames().contains(FUNCTIONNAME));
	}
	
	@Test
	public void testDeepCopy() {
		Plate plate2 = plate.deepCopy();
		
		assertEquals(  plate, plate2 );
		assertNotSame( plate, plate2 );
		
		assertFalse(  Tools.inludesSame(plate.getWells(), plate2.getWells()) );
		assertNotSame( plate.getWells(), plate2.getWells() );
		
		assertFalse(  Tools.inludesSame(plate.getPlateFunctions(), plate2.getPlateFunctions()) );
		assertNotSame( plate.getPlateFunctions(), plate2.getPlateFunctions() );
		
		assertFalse(  Tools.inludesSame(plate.getAbstractAnnotationInstances(), plate2.getAbstractAnnotationInstances() ));
		assertNotSame( plate.getAbstractAnnotationInstances(), plate2.getAbstractAnnotationInstances() );
	}
}
