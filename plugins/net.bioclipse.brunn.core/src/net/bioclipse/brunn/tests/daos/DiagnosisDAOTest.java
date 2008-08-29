package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IDiagnosisDAO;
import net.bioclipse.brunn.pojos.Diagnosis;

import org.junit.Test;

public class DiagnosisDAOTest extends AbstractGenericDAOTest {

	public DiagnosisDAOTest() {
		super("diagnosisDAO");
	}
	
	public IDiagnosisDAO getDAO(){
		return (IDiagnosisDAO)dao;
	}

	@Test
	public void testDelete() {
		
		Diagnosis diagnosis = new Diagnosis(tester, "diagnosis");
		assertFalse( diagnosis.isDeleted() );
		getDAO().save(diagnosis);
		
		session.flush();
		session.clear();
		
		getDAO().delete(diagnosis);
		
		session.flush();
		session.clear();
		
		Diagnosis deleted = getDAO().getById( diagnosis.getId() );
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testGetAll() {
		
		Diagnosis diagnosis1 = new Diagnosis(tester, "diagnosis");
		Diagnosis diagnosis2 = new Diagnosis(tester, "diagnosis 2");
		getDAO().save(diagnosis1);
		getDAO().save(diagnosis2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue( list.contains(diagnosis1) );
		assertTrue( list.contains(diagnosis2) );
	}

	@Test
	public void testSave() {
		
		Diagnosis diagnosis = new Diagnosis(tester, "diagnosis");
		getDAO().save(diagnosis);
		
		session.flush();
		session.clear();
		
		Diagnosis savedDiagnosis = getDAO().getById(diagnosis.getId());
		assertEquals(diagnosis, savedDiagnosis);
		assertNotSame(diagnosis, savedDiagnosis);
	}
	
	@Test
	public void testGetById() {
		
		Diagnosis diagnosis = new Diagnosis(tester, "diagnosis");
		getDAO().save(diagnosis);
		
		session.flush();
		session.clear();
		
		Diagnosis savedDiagnosis = getDAO().getById(new Long(diagnosis.getId()));
		assertEquals(diagnosis, savedDiagnosis);
		assertNotSame(diagnosis, savedDiagnosis);		
	}
	
	@Test
	public void testFindAllByName() {
		
		Diagnosis diagnosis1 = new Diagnosis(tester, "diagnosis1");
		Diagnosis diagnosis2 = new Diagnosis(tester, "diagnosis2");
		Diagnosis diagnosis3 = new Diagnosis(tester, "deleted");
		diagnosis3.delete();
		
		getDAO().save(diagnosis1);
		getDAO().save(diagnosis2);
		getDAO().save(diagnosis3);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(diagnosis1) );
		assertTrue(  list.contains(diagnosis2) );
		assertFalse( list.contains(diagnosis3) );
	}
}