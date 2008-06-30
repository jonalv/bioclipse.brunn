package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import net.bioclipse.brunn.genericDAO.ICellSampleDAO;
import net.bioclipse.brunn.genericDAO.IPatientSampleDAO;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.PatientSample;
import net.bioclipse.brunn.pojos.SampleContainer;

import org.junit.Test;

public class PatientSampleDAOTest extends AbstractGenericDAOTest {
	
	public PatientSampleDAOTest() {
		super("patientSampleDAO");
	}
	
	public IPatientSampleDAO getDAO(){
		return (IPatientSampleDAO)dao;
	}

	@Test
	public void testDelete() {
		
		PatientSample patientSample = new PatientSample( tester, 
                                                          "sample", 
                                                          sampleContainer, 
                                                          patientOrigin, 
                                                          new Timestamp(0) );
		
		assertFalse( patientSample.isDeleted() );
		getDAO().save(patientSample);
		
		session.flush();
		session.clear();
		
		getDAO().delete(patientSample);
		
		session.flush();
		session.clear();
		
		PatientSample deleted = getDAO().getById( new Long(patientSample.getId()) );
		assertTrue( deleted.isDeleted() );
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PatientSampleDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		PatientSample patientSample1 = new PatientSample( tester, 
                                                          "sample", 
                                                          sampleContainer, 
                                                          patientOrigin, 
                                                          new Timestamp(0)  ); 
		PatientSample patientSample2 = new PatientSample( tester, 
                                                          "sample", 
                                                          sampleContainer, 
                                                          patientOrigin, 
                                                          new Timestamp(0)  );
		getDAO().save(patientSample1);
		getDAO().save(patientSample2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue( list.contains(patientSample1) );
		assertTrue( list.contains(patientSample2) );
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PatientSampleDAO#save(net.bioclipse.brunn.pojos.PatientSample)}.
	 */
	@Test
	public void testSave() {
		
		PatientSample patientSample = new PatientSample( tester, 
                                                         "sample", 
                                                          sampleContainer, 
                                                          patientOrigin, 
                                                          new Timestamp(0)  );
		getDAO().save(patientSample);
		
		session.flush();
		session.clear();
		
		PatientSample savedPatientSample = getDAO().getById(patientSample.getId());
		assertEquals(patientSample, savedPatientSample);
		assertNotSame(patientSample, savedPatientSample);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PatientSampleDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		PatientSample patientSample = new PatientSample( tester, 
				                                         "sample", 
				                                         sampleContainer, 
				                                         patientOrigin, 
                                                         new Timestamp(0)  );
		getDAO().save(patientSample);
		
		session.flush();
		session.clear();
		
		PatientSample savedPatientSample = getDAO().getById(new Long(patientSample.getId()));
		assertEquals(patientSample, savedPatientSample);
		assertNotSame(patientSample, savedPatientSample);		
	}
}
