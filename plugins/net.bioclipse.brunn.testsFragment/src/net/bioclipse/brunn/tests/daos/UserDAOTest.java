package net.bioclipse.brunn.tests.daos;

import java.util.ArrayList;
import java.util.List;

import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests all the functionality of the <code>UserDAO</code>
 * 
 * @author jonathan
 *
 */
public class UserDAOTest extends AbstractGenericDAOTest {
	
	public IUserDAO getDAO(){
		return (IUserDAO)dao;
	}
	
	public UserDAOTest() {
		super("userDAO");
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.UserDAO#delete(long)}.
	 */
	@Test
	public void testDelete() {
		User user   = User.createUser("Delete Tester");
		assertFalse(user.isDeleted());
		getDAO().save(user);
		
		session.flush();
		session.clear();
		
		getDAO().delete(user);
		
		session.flush();
		session.clear();
		
		User deleted = getDAO().getById(new Long(user.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.UserDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		User user1 = User.createUser("Getall Tester1");
		User user2 = User.createUser("Getall Tester2");
		getDAO().save(user1);
		getDAO().save(user2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(user1));
		assertTrue(list.contains(user2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.UserDAO#save(net.bioclipse.brunn.pojos.User)}.
	 */
	@Test
	public void testSave() {
		User user = User.createUser("Save Tester");
		getDAO().save(user);
		
		session.flush();
		session.clear();
		
		User savedUser = getDAO().getById(user.getId());
		assertEquals(user, savedUser);
		assertNotSame(user, savedUser);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.UserDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		User user = User.createUser("Getbyid Tester");
		user.setAdmin(true);
		user.setPassword("password");
		getDAO().save(user);
		
		session.flush();
		session.clear();
		
		User loadedUser = getDAO().getById(new Long(user.getId()));
		assertEquals(user, loadedUser);
		assertEquals( user.getEncryptedPassword(), loadedUser.getEncryptedPassword() );
		assertNotSame(user, loadedUser);		
	}
	
	@Test
	public void testFindByName() {
		User user = User.createUser("findMe");
		getDAO().save(user);
		
		session.flush();
		session.clear();
		
		ArrayList<User> retrievedUsers = (ArrayList<User>)getDAO().findByName("findMe");
		User savedUser = retrievedUsers.get(0);
		assertEquals(user, savedUser);
		assertNotSame(user, savedUser);	
	}
}