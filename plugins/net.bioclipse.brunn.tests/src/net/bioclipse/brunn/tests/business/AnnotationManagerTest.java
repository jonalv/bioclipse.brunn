package net.bioclipse.brunn.tests.business;

import java.util.Collection;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import net.bioclipse.brunn.business.annotation.IAnnotationManager;
import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.TextAnnotation;
import net.bioclipse.brunn.tests.BaseTest;

/**
 * Tests all the methods of the AnnotationManager. 
 * 
 * Things that should be checked for:
 * 1. If something should be created/edited/deleted, 
 *    that thing gets created/edited/deleted happends
 * 2. If something should be audited, 
 *    there is a audit trail for it
 * 3. That only users that should be able to call the methods,
 *    are able to call the methods
 *     
 * @author jonathan
 */
public class AnnotationManagerTest extends BaseTest {

	IAnnotationManager am = (IAnnotationManager) context.getBean("annotationManager");
	
	public AnnotationManagerTest() {
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
	public void testDeleteUserAbstractAnnotationInstance() {
		
		Annotation annotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "textAnnotation"));
		
		int before = plate.getAuditLogs().size();
		
		AbstractAnnotationInstance textAnnotationInstance = 
			am.getAnnotationInstance(am.annotate(tester, plate, annotation, "testing"));
		
		am.delete(tester, textAnnotationInstance);
		
		boolean hasBeenEdited = false;
		
		assertEquals( before + 1, 2 );
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testDeleteUserAnnotation() {
		
		Annotation annotation = am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "annotation"));
		
		am.delete(tester, annotation);
		
		boolean hasBeenDeleted = false;
		
		assertTrue( annotation.getAuditLogs().size() == 2 );
		
		for (AuditLog auditLog : annotation.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenDeleted = true;
        }
		
		assertTrue(hasBeenDeleted);
	}

	@Test
	public void testEditUserAnnotation() {
		
		Annotation annotation = am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "annotation"));
		annotation.setName("edited");
		
		am.edit(tester, annotation);
		
		boolean hasBeenEdited = false;
		
		assertTrue( annotation.getAuditLogs().size() == 2 );
		
		for (AuditLog auditLog : annotation.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testGetAnnotationInstance() {
		
		Annotation annotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "textAnnotation"));
		
		AbstractAnnotationInstance textAnnotationInstance = 
			am.getAnnotationInstance(am.annotate(tester, tester, annotation, "testing"));
		
		session.flush();
		session.clear();
		
		AbstractAnnotationInstance fetched = am.getAnnotationInstance(textAnnotationInstance.getId());
		
		assertEquals(fetched, textAnnotationInstance);
	}

	@Test
	public void testGetAnnotation() {
		
		Annotation annotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "textAnnotation"));
		
		session.flush();
		session.clear();
		
		Annotation fetched = am.getAnnotation(annotation.getId());
		
		assertEquals(annotation, fetched);
	}

	@Test
	public void testNewAnnotationUserAnnotationTypeStringSetOfString() {
		
		HashSet<String> possibleValues = new HashSet<String>();
		possibleValues.add("green");
		possibleValues.add("blue");
		Annotation annotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "annotation", possibleValues));
		
		assertTrue( annotation.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : annotation.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);	
	}

	@Test
	public void testEditUserAbstractAnnotationInstance() {

		Annotation annotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "textAnnotation"));
		
		AbstractAnnotationInstance textAnnotationInstance = 
			am.getAnnotationInstance(am.annotate(tester, plate, annotation, "testing"));
		
		textAnnotationInstance.setName("edited");
		
		int before = plate.getAuditLogs().size();
		
		am.edit(tester, textAnnotationInstance);
		
		boolean hasBeenEdited = false;
		
		assertEquals( before + 1, plate.getAuditLogs().size() );
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testAnnotate() {
		
		Annotation annotation = am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "color"));
		AbstractAnnotationInstance ai = am.getAnnotationInstance(am.annotate(tester, plate, annotation, "blue"));
		
		boolean hasBeenEdited = false;
		
		assertTrue( plate.getAuditLogs().size() == 2 );
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
		
		assertTrue( plate.getAbstractAnnotationInstances().contains(ai) );
	}

	@Test
	public void testGetAllAnnotations() {
		
		Annotation annotation1 = am.getAnnotation(am.createAnnotation(tester, AnnotationType.FLOAT_ANNOTATION, "annotation1")); 
		Annotation annotation2 = am.getAnnotation(am.createAnnotation(tester, AnnotationType.FLOAT_ANNOTATION, "annotation2")); 
		
		Collection<Annotation> annotations = am.getAllAnnotations();
		
		assertTrue(annotations.contains(annotation1));
		assertTrue(annotations.contains(annotation2));
	}

	@Test
	public void testGetAllAnnotationInstances() {
		
		Annotation annotation1 = am.getAnnotation(am.createAnnotation(tester, AnnotationType.FLOAT_ANNOTATION, "annotation1")); 
		Annotation annotation2 = am.getAnnotation(am.createAnnotation(tester, AnnotationType.FLOAT_ANNOTATION, "annotation2")); 
		
		AbstractAnnotationInstance ai1 = am.getAnnotationInstance(am.annotate(tester, tester, annotation1, 23.0));
		AbstractAnnotationInstance ai2 = am.getAnnotationInstance(am.annotate(tester, tester, annotation2, 46.0));
		
		Collection<AbstractAnnotationInstance> ais = am.getAllAnnotationInstances();
		
		assertTrue(ais.contains(ai1));
		assertTrue(ais.contains(ai2));
	}

	@Test
	public void testNewAnnotationUserAnnotationTypeString() {
		
		Annotation annotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "annotation"));
		
		assertTrue( annotation.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : annotation.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
	}
}
