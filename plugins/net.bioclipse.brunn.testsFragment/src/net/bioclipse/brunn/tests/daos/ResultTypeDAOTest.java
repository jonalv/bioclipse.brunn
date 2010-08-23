package net.bioclipse.brunn.tests.daos;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IResultTypeDAO;
import net.bioclipse.brunn.pojos.ResultType;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests all the functionality of the <code>ResultTypeDAOTest</code>
 * 
 * @author jonathan
 *
 */
public class ResultTypeDAOTest extends AbstractGenericDAOTest {

	public ResultTypeDAOTest() {
		super("resultTypeDAO");
	}
	
	public IResultTypeDAO getDAO(){
		return (IResultTypeDAO)dao;
	}

	@Test
	public void testDelete() {
		
		ResultType resultType = new ResultType(tester, "resulttype", 2);
		assertFalse(resultType.isDeleted());
		getDAO().save(resultType);
		
		session.flush();
		session.clear();
		
		getDAO().delete(resultType);
		
		session.flush();
		session.clear();
		
		ResultType deleted = getDAO().getById(new Long(resultType.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.ResultTypeDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		ResultType resultType1 = new ResultType(tester, "resultType1", 2);
		ResultType resultType2 = new ResultType(tester, "resultType2", 2);
		getDAO().save(resultType1);
		getDAO().save(resultType2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(resultType1));
		assertTrue(list.contains(resultType2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.ResultTypeDAO#save(net.bioclipse.brunn.pojos.ResultType)}.
	 */
	@Test
	public void testSave() {
		
		ResultType resultType = new ResultType(tester, "resultType", 2);
		getDAO().save(resultType);
		
		session.flush();
		session.clear();
		
		ResultType savedResultType = getDAO().getById(resultType.getId());
		assertEquals(resultType, savedResultType);
		assertNotSame(resultType, savedResultType);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.ResultTypeDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		ResultType resultType = new ResultType(tester, "resultType", 2);
		getDAO().save(resultType);
		
		session.flush();
		session.clear();
		
		ResultType savedResultType = getDAO().getById(new Long(resultType.getId()));
		assertEquals(resultType, savedResultType);
		assertNotSame(resultType, savedResultType);		
	}
	
	@Test
	public void testFindByName() {
		
		ResultType resultType = new ResultType(tester, "someUniqueName", 2);
		getDAO().save(resultType);
		
		session.flush();
		session.clear();
		
		ResultType savedResultType = getDAO().findByName(resultType.getName()).get(0);
		assertEquals(resultType, savedResultType);
		assertNotSame(resultType, savedResultType);		
	}
}
