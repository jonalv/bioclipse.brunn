package net.bioclipse.brunn.tests.daos;

import java.util.List;

import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;
import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.Well;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests all the functionality of the <code>SampleContainerDAOTest</code>
 * 
 * @author jonathan
 *
 */
public class SampleContainerDAOTest extends AbstractGenericDAOTest {

	public SampleContainerDAOTest() {
		super("sampleContainerDAO");
	}

	public ISampleContainerDAO getDAO(){
		return (ISampleContainerDAO)dao;
	}

	@Test
	public void testDelete() {
		
		//Just some well
		SampleContainer sampleContainer = new SampleContainer(tester, "sampleContainer", (Well)plate.getWells().toArray()[0]);
		
		assertFalse(sampleContainer.isDeleted());
		getDAO().save(sampleContainer);
		
		session.flush();
		session.clear();
		
		getDAO().delete(sampleContainer);
		
		session.flush();
		session.clear();
		
		SampleContainer deleted = getDAO().getById(new Long(sampleContainer.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.SampleContainerDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
//		Just some well
		SampleContainer sampleContainer1 = new SampleContainer(tester, "sampleContainer1", (Well)plate.getWells().toArray()[0]);
//		Just some well
		SampleContainer sampleContainer2 = new SampleContainer(tester, "sampleContainer2", (Well)plate.getWells().toArray()[0]);
		getDAO().save(sampleContainer1);
		getDAO().save(sampleContainer2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(sampleContainer1));
		assertTrue(list.contains(sampleContainer2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.SampleContainerDAO#save(net.bioclipse.brunn.pojos.SampleContainer)}.
	 */
	@Test(timeout=timeout)
	public void testSave() {
		
//		Just some well
		SampleContainer sampleContainer = new SampleContainer(tester, "sampleContainer", (Well)plate.getWells().toArray()[0]);
		
		DrugSample sample = new DrugSample(tester, "a sample", 23, drugOrigin, sampleContainer, ConcUnit.UNIT);
		Measurement m = new Measurement();
		sampleContainer.getWorkList().getAbstractOperations().add( m );
		getDAO().save(sampleContainer);
		
		session.flush();
		session.clear();
		
		SampleContainer savedSampleContainer = getDAO().getById(sampleContainer.getId());
		sampleContainer.equals(savedSampleContainer);
		assertEquals(sampleContainer, savedSampleContainer);
		
		assertNotSame(sampleContainer, savedSampleContainer);
		assertEquals( m ,  savedSampleContainer.getWorkList().getAbstractOperations().toArray()[0] );
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.SampleContainerDAO#getById(long)}.
	 */
	@Test(timeout = timeout)
	public void testGetById() {
		
//		Just some well
		SampleContainer sampleContainer = new SampleContainer(tester, "sampleContainer", (Well)plate.getWells().toArray()[0]);
		
		getDAO().save(sampleContainer);
		
		session.flush();
		session.clear();
		
		SampleContainer savedSampleContainer = getDAO().getById(new Long(sampleContainer.getId()));
		assertEquals(sampleContainer, savedSampleContainer);
		assertNotSame(sampleContainer, savedSampleContainer);		
	}
}
