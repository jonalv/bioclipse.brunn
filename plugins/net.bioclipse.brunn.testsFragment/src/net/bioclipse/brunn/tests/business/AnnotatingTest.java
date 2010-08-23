package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import net.bioclipse.brunn.business.annotation.IAnnotationManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.tests.BaseTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that the different annotations are possible
 * 
 * @author jonathan
 *
 */
public class AnnotatingTest extends BaseTest {

	public AnnotatingTest() {
		super();
	}

	/* 
	 * creates a new clean database
	 * opens a Hibernate session
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/* 
	 * closes the session
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests to annotate a plate
	 */
	@Test
	public void testPlateAnnotation(){
		
		/*
		 * Setup
		 */
		
		IPlateManager       pm = (IPlateManager)context.getBean("plateManager");
		IAnnotationManager  am = (IAnnotationManager)context.getBean("annotationManager");
		IPlateLayoutManager plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		
		User tester = new User("tester");
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		userDAO.save(tester);
		
		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(
				tester, "test", plm.getPlateType(plm.createPlateType(tester, 9, 6, "test", plateTypes)), plateLayouts ));
		
		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "test", plateLayout, masterPlates, 1));
		
		Plate plate = pm.getPlate( pm.createPlate(tester, "testplate","barcode", folder, masterPlate, cellOrigin, null) );
		
		/*
		 * The annotation
		 */
		
		// FloatAnnotation
		
		Annotation floatAnnotation = am.getAnnotation(am.createAnnotation(
				tester, 
				AnnotationType.FLOAT_ANNOTATION, 
				"test float annotation", 
				new HashSet<String>()
		));
				
		AbstractAnnotationInstance floatAnnotationInstance = 
			am.getAnnotationInstance(am.annotate(tester, plate, floatAnnotation, 45.32));
		
		// EnumAnnotation
		
		Set<String> possibleValues = new HashSet<String>();
		possibleValues.add("green");
		possibleValues.add("blue");
		Annotation enumAnnotation = am.getAnnotation(am.createAnnotation(
				tester, 
				AnnotationType.ENUM_ANNOTATION, 
				"test enum annotation", 
				possibleValues
		));
		
		AbstractAnnotationInstance enumAnnotationInstance = 
			am.getAnnotationInstance(am.annotate(tester, plate, enumAnnotation, "blue"));
		
		// TextAnnotation
		
		Annotation textAnnotation = am.getAnnotation(am.createAnnotation(
				tester, 
				AnnotationType.TEXT_ANNOTATION, 
				"test text annotation", 
				new HashSet<String>()
		));
		
		AbstractAnnotationInstance textAnnotationInstance =
			am.getAnnotationInstance(am.annotate(tester, plate, textAnnotation, "test"));
		
		/*
		 * Assertions
		 */
		//FloatAnnotation
		Plate savedPlate = pm.getPlate(plate.getId());
		assertTrue(savedPlate.getAbstractAnnotationInstances().containsAll(
				floatAnnotation.getAnnotationInstances())
				);
		
		assertEquals(floatAnnotationInstance.getAbstractAnnotatableObject(), plate);
		
		assertTrue(plate.getAbstractAnnotationInstances().contains(floatAnnotationInstance));
		assertEquals(floatAnnotationInstance.getAbstractAnnotatableObject(), plate);
		
		//TextAnnotation
		assertTrue(savedPlate.getAbstractAnnotationInstances().containsAll(
				textAnnotation.getAnnotationInstances())
				);
		
		assertEquals(textAnnotationInstance.getAbstractAnnotatableObject(), plate);
		
		assertTrue(plate.getAbstractAnnotationInstances().contains(textAnnotationInstance));
		assertEquals(textAnnotationInstance.getAbstractAnnotatableObject(), plate);
		
		//EnumAnnotation
		
		assertTrue(savedPlate.getAbstractAnnotationInstances().containsAll(
				enumAnnotation.getAnnotationInstances()));

		assertEquals(enumAnnotationInstance.getAbstractAnnotatableObject(), plate);
		
		assertTrue(plate.getAbstractAnnotationInstances().contains(enumAnnotationInstance));
		assertEquals(enumAnnotationInstance.getAbstractAnnotatableObject(), plate);

	}
}
