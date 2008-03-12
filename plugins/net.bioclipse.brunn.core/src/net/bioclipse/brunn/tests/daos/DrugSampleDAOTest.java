package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IDrugSampleDAO;
import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.pojos.DrugSample;

import org.junit.Test;

public class DrugSampleDAOTest extends AbstractGenericDAOTest {
	
	public DrugSampleDAOTest() {
		super("drugSampleDAO");
	}
	
	public IDrugSampleDAO getDAO(){
		return (IDrugSampleDAO)dao;
	}

	@Test
	public void testDelete() {
		
		DrugSample drugSample = new DrugSample( tester,
                                                "drugSample", 
                                                23.0, 
                                                drugOrigin, 
                                                sampleContainer, 
                                                ConcUnit.UNIT );	
		assertFalse( drugSample.isDeleted() );
		getDAO().save(drugSample);
		
		session.flush();
		session.clear();
		
		getDAO().delete(drugSample);
		
		session.flush();
		session.clear();
		
		DrugSample deleted = getDAO().getById( new Long(drugSample.getId()) );
		assertTrue( deleted.isDeleted() );
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.DrugSampleDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		DrugSample drugSample1 = new DrugSample( tester,
                                                 "drugSample", 
                                                 23.0, 
                                                 drugOrigin, 
                                                 sampleContainer, 
                                                 ConcUnit.UNIT ); 
		DrugSample drugSample2 = new DrugSample( tester,
                                                 "drugSample", 
                                                 23.0, 
                                                 drugOrigin, 
                                                 sampleContainer,
                                                 ConcUnit.UNIT );
		getDAO().save(drugSample1);
		getDAO().save(drugSample2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue( list.contains(drugSample1) );
		assertTrue( list.contains(drugSample2) );
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.DrugSampleDAO#save(net.bioclipse.brunn.pojos.DrugSample)}.
	 */
	@Test
	public void testSave() {
		
		DrugSample drugSample = new DrugSample( tester,
                                                "drugSample", 
                                                23.0, 
                                                drugOrigin, 
                                                sampleContainer, 
                                                ConcUnit.UNIT );
		getDAO().save(drugSample);
		
		session.flush();
		session.clear();
		
		DrugSample savedDrugSample = getDAO().getById(drugSample.getId());
		assertEquals(drugSample, savedDrugSample);
		assertNotSame(drugSample, savedDrugSample);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.DrugSampleDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		DrugSample drugSample = new DrugSample( tester,
                                                "drugSample", 
                                                23.0, 
                                                drugOrigin, 
                                                sampleContainer,
                                                ConcUnit.UNIT );
		getDAO().save(drugSample);
		
		session.flush();
		session.clear();
		
		DrugSample savedDrugSample = getDAO().getById( new Long(drugSample.getId()) );
		assertEquals(drugSample, savedDrugSample);
		assertNotSame(drugSample, savedDrugSample);		
	}
}
