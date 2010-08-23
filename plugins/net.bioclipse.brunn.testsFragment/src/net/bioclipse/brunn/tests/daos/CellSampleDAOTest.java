package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import net.bioclipse.brunn.genericDAO.ICellSampleDAO;
import net.bioclipse.brunn.pojos.CellSample;

import org.junit.Test;

public class CellSampleDAOTest extends AbstractGenericDAOTest {
	
	public CellSampleDAOTest() {
		super("cellSampleDAO");
	}
	
	public ICellSampleDAO getDAO(){
		return (ICellSampleDAO)dao;
	}

	@Test
	public void testDelete() {
		
		CellSample cellSample = new CellSample( tester,
				                                "cellSample", 
				                                cellOrigin, 
				                                new Timestamp(System.currentTimeMillis()), 
				                                sampleContainer );
		assertFalse( cellSample.isDeleted() );
		getDAO().save(cellSample);
		
		session.flush();
		session.clear();
		
		getDAO().delete(cellSample);
		
		session.flush();
		session.clear();
		
		CellSample deleted = getDAO().getById( new Long(cellSample.getId()) );
		assertTrue( deleted.isDeleted() );
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.CellSampleDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		CellSample cellSample1 = new CellSample( tester,
				                                 "cellSample", 
				                                 cellOrigin, 
				                                 new Timestamp(System.currentTimeMillis()), 
				                                 sampleContainer ); 
		CellSample cellSample2 = new CellSample( tester,
                                                 "cellSample", 
                                                 cellOrigin, 
                                                 new Timestamp(System.currentTimeMillis()), 
                                                 sampleContainer );
		getDAO().save(cellSample1);
		getDAO().save(cellSample2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue( list.contains(cellSample1) );
		assertTrue( list.contains(cellSample2) );
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.CellSampleDAO#save(net.bioclipse.brunn.pojos.CellSample)}.
	 */
	@Test
	public void testSave() {
		
		CellSample cellSample = new CellSample( tester,
                                                "cellSample", 
                                                cellOrigin, 
                                                new Timestamp(System.currentTimeMillis()), 
                                                sampleContainer );
		getDAO().save(cellSample);
		
		session.flush();
		session.clear();
		
		CellSample savedCellSample = getDAO().getById(cellSample.getId());
		assertEquals(cellSample, savedCellSample);
		assertNotSame(cellSample, savedCellSample);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.CellSampleDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		CellSample cellSample = new CellSample( tester,
                                                "cellSample", 
                                                cellOrigin, 
                                                new Timestamp(System.currentTimeMillis()), 
                                                sampleContainer );
		getDAO().save(cellSample);
		
		session.flush();
		session.clear();
		
		CellSample savedCellSample = getDAO().getById(new Long(cellSample.getId()));
		assertEquals(cellSample, savedCellSample);
		assertNotSame(cellSample, savedCellSample);		
	}
}
