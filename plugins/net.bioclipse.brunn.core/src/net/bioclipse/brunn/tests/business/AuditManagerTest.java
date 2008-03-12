package net.bioclipse.brunn.tests.business;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import net.bioclipse.brunn.business.audit.IAuditManager;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.tests.BaseTest;

/**
 * Tests all the methods of the AuditManager. 
 * 
 * Things that should be checked for:
 * 1. If something should be created/edited/deleted, 
 *    that thing gets created/edited/deleted happends
 * 2. If something should be audited, 
 *    there is a audit trail for it
 * 3. That only users that should be able to call the methods,
 *    are able to call the methods
 *    
 *    @author jonathan
 */
public class AuditManagerTest extends BaseTest {

	private IAuditManager am = (IAuditManager)context.getBean("auditManager");
	
	public AuditManagerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDeleteUser() {
		
		//TODO check with a User not allowed
		
		User toBeDeleted = am.getUser(am.createUser(tester, "test", "", false));
		assertFalse(toBeDeleted.isDeleted());
		am.delete(toBeDeleted);
		User deleted = am.getUser(toBeDeleted.getId());
		assertTrue(deleted.isDeleted());
		
		boolean hasBeenCreated = false;
		boolean hasBeenDeleted = false;
		
		assertTrue( deleted.getAuditLogs().size() == 2 );
		
		for (AuditLog auditLog : deleted.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
	        if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
	        	hasBeenDeleted = true;
        }
		
		assertTrue(hasBeenCreated);
		assertTrue(hasBeenDeleted);
	}

	@Test
	public void testEditUser() {

		//TODO check with a User not allowed
		
		User toBeEdited = am.getUser(am.createUser(tester, "test", "", false));
		
		toBeEdited.setName("edited");
		am.edit(toBeEdited);
		
		User edited = am.getUser(toBeEdited.getId());
		assertEquals(edited.getName(), "edited");
		
		boolean hasBeenCreated = false;
		boolean hasBeenEdited  = false;
		
		assertTrue( edited.getAuditLogs().size() == 2 );
		
		for (AuditLog auditLog : edited.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenCreated);
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testGetAllUsers() {
		
		User user1 = am.getUser(am.createUser(tester, "user1", "", false));
		User user2 = am.getUser(am.createUser(tester, "user2", "", false));
		
		Collection users = am.getAllUsers();
		assertTrue(users.contains(user1));
		assertTrue(users.contains(user2));	
	}

	@Test
	public void testNewUser() {
		
		//TODO check with a User not allowed
		
		User user = am.getUser(am.createUser(tester, "test", "", false));
		
		assertTrue( user.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : user.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
	}

	@Test
	public void testGetUser() {
		
		User user = am.getUser(am.createUser(tester, "test", "", false));
		
		session.flush();
		session.clear();
		
		assertEquals(user, am.getUser(user.getId()));
	}
}
