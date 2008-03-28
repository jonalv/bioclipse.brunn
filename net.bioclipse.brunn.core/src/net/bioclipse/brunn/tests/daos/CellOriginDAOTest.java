package net.bioclipse.brunn.tests.daos;

import java.util.List;
import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.pojos.CellOrigin;

import org.junit.Test;


/**
 * Tests all the functionality of the <code>CellOriginDAOTest</code>
 * 
 * @author jonathan
 *
 */
public class CellOriginDAOTest extends AbstractGenericDAOTest {

	public CellOriginDAOTest() {
		super("cellOriginDAO");
	}
	
	public ICellOriginDAO getDAO(){
		return (ICellOriginDAO)dao;
	}

	@Test
	public void testDelete() {
		
		CellOrigin cellOrigin = new CellOrigin(tester, "cell origin");
		assertFalse(cellOrigin.isDeleted());
		getDAO().save(cellOrigin);
		
		session.flush();
		session.clear();
		
		getDAO().delete(cellOrigin);
		
		session.flush();
		session.clear();
		
		CellOrigin deleted = getDAO().getById(new Long(cellOrigin.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.CellOriginDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		CellOrigin cellOrigin1 = new CellOrigin(tester, "cellOrigin1");
		CellOrigin cellOrigin2 = new CellOrigin(tester, "cellOrigin2");
		getDAO().save(cellOrigin1);
		getDAO().save(cellOrigin2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(cellOrigin1));
		assertTrue(list.contains(cellOrigin2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.CellOriginDAO#save(net.bioclipse.brunn.pojos.CellOrigin)}.
	 */
	@Test
	public void testSave() {
		
		CellOrigin cellOrigin = new CellOrigin(tester, "cellOrigin");
		getDAO().save(cellOrigin);
		
		session.flush();
		session.clear();
		
		CellOrigin savedCellOrigin = getDAO().getById(cellOrigin.getId());
		assertEquals(cellOrigin, savedCellOrigin);
		assertNotSame(cellOrigin, savedCellOrigin);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.CellOriginDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		CellOrigin cellOrigin = new CellOrigin(tester, "cellOrigin");
		getDAO().save(cellOrigin);
		
		session.flush();
		session.clear();
		
		CellOrigin savedCellOrigin = getDAO().getById(new Long(cellOrigin.getId()));
		assertEquals(cellOrigin, savedCellOrigin);
		assertNotSame(cellOrigin, savedCellOrigin);		
	}
	
	@Test
	public void testFindAllByName() {
		
		CellOrigin cellOrigin1 = new CellOrigin(tester, "cellOrigin1");
		CellOrigin cellOrigin2 = new CellOrigin(tester, "cellOrigin2");
		CellOrigin cellOrigin3 = new CellOrigin(tester, "deleted");
		cellOrigin3.delete();
		
		getDAO().save(cellOrigin1);
		getDAO().save(cellOrigin2);
		getDAO().save(cellOrigin3);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(cellOrigin1) );
		assertTrue(  list.contains(cellOrigin2) );
		assertFalse( list.contains(cellOrigin3) );
	}
}
