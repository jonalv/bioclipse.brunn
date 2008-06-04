package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IAnnotationInstanceDAO;
import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.EnumAnnotation;
import net.bioclipse.brunn.pojos.FloatAnnotation;
import net.bioclipse.brunn.pojos.TextAnnotation;

import org.junit.Test;

/**
 * Tests all the functionality of the <code>AnnotationDAO</code>
 * 
 * @author jonathan
 *
 */
public class AnnotationInstanceDAOTest extends AbstractGenericDAOTest {
	
	public AnnotationInstanceDAOTest() {
		super("annotationInstanceDAO");
	}

	private IAnnotationInstanceDAO getDAO(){
		return (IAnnotationInstanceDAO)this.dao;
	}

	@Test(timeout = timeout)
	public void testDelete() {
	
		EnumAnnotation enumAnnotation = new EnumAnnotation( tester, "blue", this.enumAnnotation );		
		getDAO().save(enumAnnotation);
		
		FloatAnnotation floatAnnotation = new FloatAnnotation( tester,  0.1314, this.floatAnnotation );		
		getDAO().save(floatAnnotation);
		
		TextAnnotation textAnnotation = new TextAnnotation( tester, "comment", this.textAnnotation );		
		getDAO().save(textAnnotation);

		session.flush();
		session.clear();

		getDAO().delete( enumAnnotation  );
		getDAO().delete( floatAnnotation );		
		getDAO().delete( textAnnotation  );
		
		session.flush();
		session.clear();
		
		AbstractAnnotationInstance deletedEnum = getDAO().getById(new Long(enumAnnotation.getId()));
		assertTrue(deletedEnum.isDeleted());
		
		AbstractAnnotationInstance deletedText = getDAO().getById(new Long(textAnnotation.getId()));
		assertTrue(deletedText.isDeleted());
		
		AbstractAnnotationInstance deletedFloat = getDAO().getById(new Long(floatAnnotation.getId()));
		assertTrue(deletedFloat.isDeleted());
	}

	@Test(timeout = timeout)
	public void testGetAll() {
		
		EnumAnnotation enumAnnotation = new EnumAnnotation( tester, "blue", this.enumAnnotation );		
		getDAO().save(enumAnnotation);
		
		FloatAnnotation floatAnnotation = new FloatAnnotation( tester,  0.1314, this.floatAnnotation );		
		getDAO().save(floatAnnotation);
		
		TextAnnotation textAnnotation = new TextAnnotation( tester, "comment", this.textAnnotation );		
		getDAO().save(textAnnotation);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue( list.contains(enumAnnotation) );
		assertTrue( list.contains(floatAnnotation) );
		assertTrue( list.contains(textAnnotation) );
	}

	@Test(timeout = timeout)
	public void testSaveAndGetEnumAnnotation() {

		EnumAnnotation enumAnnotation = new EnumAnnotation( tester, "blue", this.enumAnnotation );		
		getDAO().save(enumAnnotation);

		session.flush();
		session.clear();
		
		AbstractAnnotationInstance savedAnnotation = getDAO().getById(new Long(enumAnnotation.getId()));
		assertEquals(enumAnnotation, savedAnnotation);
		assertNotSame(enumAnnotation, savedAnnotation);
	}

	@Test(timeout = timeout)
	public void testSaveAndGetFloatAnnotation() {
		
		FloatAnnotation floatAnnotation = new FloatAnnotation( tester,  0.1314, this.floatAnnotation );		
		getDAO().save(floatAnnotation);

		session.flush();
		session.clear();
		
		AbstractAnnotationInstance savedAnnotation = getDAO().getById(new Long(floatAnnotation.getId()));
		assertEquals(floatAnnotation, savedAnnotation);
		assertNotSame(floatAnnotation, savedAnnotation);
	}

	@Test(timeout = timeout)
	public void testSaveAndGetTextAnnotation() {
		
		TextAnnotation textAnnotation = new TextAnnotation( tester, "comment", this.textAnnotation );		
		getDAO().save(textAnnotation);
		
		session.flush();
		session.clear();
		
		AbstractAnnotationInstance savedAnnotation = getDAO().getById(new Long(textAnnotation.getId()));
		assertEquals(textAnnotation, savedAnnotation);
		assertNotSame(textAnnotation, savedAnnotation);
	}
}
