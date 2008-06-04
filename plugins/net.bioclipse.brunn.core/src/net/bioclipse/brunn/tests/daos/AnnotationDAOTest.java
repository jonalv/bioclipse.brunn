package net.bioclipse.brunn.tests.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.IAnnotationDAO;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;

/**
 * Tests all the functionality of the <code>MasterAnnotationTypeDAO</code>
 * 
 * @author jonathan
 *
 */
public class AnnotationDAOTest extends AbstractGenericDAOTest {
	
	public IAnnotationDAO getDAO(){
		return (IAnnotationDAO)dao;
	}
	
	public AnnotationDAOTest(){
		super("annotationDAO");
	}

	@Test
	public void testSave() {
		
		Set<String> possibleValues= new HashSet<String>();
		possibleValues.add("test");
		possibleValues.add("test2");
		
		Annotation masterAnnotationType = new Annotation(tester, "testing", possibleValues, AnnotationType.ENUM_ANNOTATION);		
		getDAO().save(masterAnnotationType);

		session.flush();
		session.clear();
		
		Annotation savedMasterAnnotationType = getDAO().getById(new Long(masterAnnotationType.getId()));
		assertEquals(masterAnnotationType, savedMasterAnnotationType);
		assertNotSame(masterAnnotationType, savedMasterAnnotationType);
	}
	
	@Test
	public void testDelete() {
		
		Annotation masterAnnotationType = new Annotation(tester, "testing", null, AnnotationType.FLOAT_ANNOTATION);
		assertFalse(masterAnnotationType.isDeleted());
		getDAO().save(masterAnnotationType);
		
		session.flush();
		session.clear();
		
		getDAO().delete(masterAnnotationType);
		
		session.flush();
		session.clear();
		
		Annotation deleted = getDAO().getById(new Long(masterAnnotationType.getId()));
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testGetAll() {
		
		Annotation masterAnnotationType1 = 
			new Annotation(tester, "testing", new HashSet<String>(), AnnotationType.FLOAT_ANNOTATION);
		Annotation masterAnnotationType2 = 
			new Annotation(tester, "testingMore", new HashSet<String>(), AnnotationType.FLOAT_ANNOTATION);
		getDAO().save(masterAnnotationType1);
		getDAO().save(masterAnnotationType2);
		
		session.flush();
		session.clear();
	
		List list = getDAO().findAll();
		assertTrue(list.contains(masterAnnotationType1));
		assertTrue(list.contains(masterAnnotationType2));
	}

	@Test
	public void testGetById() {

		Annotation masterAnnotationType = 
			new Annotation(tester, "testing", new HashSet<String>(), AnnotationType.FLOAT_ANNOTATION);
		getDAO().save(masterAnnotationType);
		
		session.flush();
		session.clear();
		
		Annotation savedMasterAnnotationType = getDAO().getById(new Long(masterAnnotationType.getId()));
		assertEquals(masterAnnotationType, savedMasterAnnotationType);
		assertNotSame(masterAnnotationType, savedMasterAnnotationType);
		assertTrue(masterAnnotationType.getId() == savedMasterAnnotationType.getId());
	}
}
