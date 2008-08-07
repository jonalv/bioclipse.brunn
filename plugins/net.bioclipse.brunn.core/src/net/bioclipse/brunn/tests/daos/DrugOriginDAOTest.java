package net.bioclipse.brunn.tests.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.IDrugOriginDAO;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.tests.TestConstants;

import org.junit.Test;

/**
 * Tests all the functionality of the <code>DrugOriginDAOTest</code>
 * 
 * @author jonathan
 *
 */
public class DrugOriginDAOTest extends AbstractGenericDAOTest {

	public DrugOriginDAOTest() {
		super("drugOriginDAO");
	}
	
	public IDrugOriginDAO getDAO(){
		return (IDrugOriginDAO)dao;
	}

	@Test
	public void testDelete() throws IOException {
		
		DrugOrigin drugOrigin = new DrugOrigin(tester, "drugOrigin2", null, 123.0);
		assertFalse(drugOrigin.isDeleted());
		getDAO().save(drugOrigin);
		
		session.flush();
		session.clear();
		
		getDAO().delete(drugOrigin);
		
		session.flush();
		session.clear();
		
		DrugOrigin deleted = getDAO().getById(new Long(drugOrigin.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.DrugOriginDAO#getAll()}.
	 * @throws IOException 
	 */
	@Test
	public void testGetAll() throws IOException {
		
		DrugOrigin drugOrigin1 = new DrugOrigin(tester, "drugOrigin1", null, 24.0);
		DrugOrigin drugOrigin2 = new DrugOrigin(tester, "drugOrigin2", null, 30.0);
		getDAO().save(drugOrigin1);
		getDAO().save(drugOrigin2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(drugOrigin1));
		assertTrue(list.contains(drugOrigin2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.DrugOriginDAO#save(net.bioclipse.brunn.pojos.DrugOrigin)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testSave() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin = new DrugOrigin( tester, 
				                                "drugOrigin1", 
				                                new FileInputStream( TestConstants.getTestMolFile() ), 
				                                24.0);
		getDAO().save(drugOrigin);
		
		session.flush();
		session.clear();
		
		DrugOrigin savedDrugOrigin = getDAO().getById(drugOrigin.getId());
		assertEquals(drugOrigin, savedDrugOrigin);
		assertNotSame(drugOrigin, savedDrugOrigin);
		
		drugOrigin = new DrugOrigin( tester, 
                "drugOrigin1", 
                new FileInputStream( TestConstants.getTestMolFile() ), 
                24.0);
		try {
			getDAO().save(drugOrigin);
		}
		catch (org.hibernate.exception.ConstraintViolationException e) {
			// This is what we want
		}
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.DrugOriginDAO#getById(long)}.
	 * @throws IOException 
	 */
	@Test
	public void testGetById() throws IOException {
		
		DrugOrigin drugOrigin = new DrugOrigin(tester, "drugOrigin1", null, 24.0);
		getDAO().save(drugOrigin);
		
		session.flush();
		session.clear();
		
		DrugOrigin savedDrugOrigin = getDAO().getById(new Long(drugOrigin.getId()));
		assertEquals(drugOrigin, savedDrugOrigin);
		assertNotSame(drugOrigin, savedDrugOrigin);
	}
	
	@Test
	public void testFindAllNotDeleted() throws IOException {
		
		DrugOrigin drugOrigin1 = new DrugOrigin(tester, "drugOrigin1", null, 24.0);
		DrugOrigin drugOrigin2 = new DrugOrigin(tester, "drugOrigin2", null, 30.0);
		DrugOrigin drugOrigin3 = new DrugOrigin(tester, "deleted",     null, 16.0);
		drugOrigin3.delete();
		
		getDAO().save(drugOrigin1);
		getDAO().save(drugOrigin2);
		getDAO().save(drugOrigin3);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(drugOrigin1) );
		assertTrue(  list.contains(drugOrigin2) );
		assertFalse( list.contains(drugOrigin3) );
	}
}

