package net.bioclipse.brunn.tests.daos;

import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;

import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.genericDAO.IPatientOriginDAO;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.PatientOrigin;

public class PatientOriginDAOTest extends AbstractGenericDAOTest {

	public PatientOriginDAOTest() {
		super("patientOriginDAO");
	}
	
	public IPatientOriginDAO getDAO(){
		return (IPatientOriginDAO)dao;
	}

	@Test
	public void testDelete() {
		
		PatientOrigin patientOrigin = new PatientOrigin(tester, "patient name", "lid");
		assertFalse(patientOrigin.isDeleted());
		getDAO().save(patientOrigin);
		
		session.flush();
		session.clear();
		
		getDAO().delete(patientOrigin);
		
		session.flush();
		session.clear();
		
		PatientOrigin deleted = getDAO().getById(new Long(patientOrigin.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PatientOriginDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		PatientOrigin patientOrigin1 = new PatientOrigin(tester, "patient name", "lid");
		PatientOrigin patientOrigin2 = new PatientOrigin(tester, "patient name2", "lid2");
		getDAO().save(patientOrigin1);
		getDAO().save(patientOrigin2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(patientOrigin1));
		assertTrue(list.contains(patientOrigin2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PatientOriginDAO#save(net.bioclipse.brunn.pojos.PatientOrigin)}.
	 */
	@Test
	public void testSave() {
		
		PatientOrigin patientOrigin = new PatientOrigin(tester, "patient name", "lid");
		getDAO().save(patientOrigin);
		
		session.flush();
		session.clear();
		
		PatientOrigin savedPatientOrigin = getDAO().getById(patientOrigin.getId());
		assertEquals(patientOrigin, savedPatientOrigin);
		assertNotSame(patientOrigin, savedPatientOrigin);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PatientOriginDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		PatientOrigin patientOrigin = new PatientOrigin(tester, "patient name", "lid");
		getDAO().save(patientOrigin);
		
		session.flush();
		session.clear();
		
		PatientOrigin savedPatientOrigin = getDAO().getById(new Long(patientOrigin.getId()));
		assertEquals(patientOrigin, savedPatientOrigin);
		assertNotSame(patientOrigin, savedPatientOrigin);		
	}
	
	@Test
	public void testFindAllByName() {
		
		PatientOrigin patientOrigin1 = new PatientOrigin(tester, "patient name1", "lid1");
		PatientOrigin patientOrigin2 = new PatientOrigin(tester, "patient name2", "lid2");
		PatientOrigin patientOrigin3 = new PatientOrigin(tester, "deleted", "lid3");
		patientOrigin3.delete();
		
		getDAO().save(patientOrigin1);
		getDAO().save(patientOrigin2);
		getDAO().save(patientOrigin3);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(patientOrigin1) );
		assertTrue(  list.contains(patientOrigin2) );
		assertFalse( list.contains(patientOrigin3) );
	}
}
