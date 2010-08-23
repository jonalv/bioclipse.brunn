package net.bioclipse.brunn.tests.daos;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.IAuditLogDAO;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;

/**
 * Tests all the functionality of the <code>AuditLogDAO</code>
 * 
 * @author jonathan
 *
 */
public class AuditLogDAOTest extends AbstractGenericDAOTest {

	private IAuditLogDAO getDAO(){
		return (IAuditLogDAO)dao;
	}
	
	public AuditLogDAOTest(){
		super("auditLogDAO");
	}

	@Test
	public void testDelete() {
		
		AuditLog auditLog = new AuditLog( tester,
                                          AuditType.CREATE_EVENT,
				                          this.plate,
				                          this.plate.toString(),
		                                  new Timestamp(System.currentTimeMillis())
		);
		
		assertFalse(auditLog.isDeleted());
		getDAO().save(auditLog);
		
		session.flush();
		session.clear();
		
		getDAO().delete(auditLog);
		
		session.flush();
		session.clear();
		
		AuditLog deleted = getDAO().getById(auditLog.getId());
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testGetAll() {
		
		AuditLog auditLog1 = new AuditLog( tester,
                                           AuditType.CREATE_EVENT,
                                           this.plate,
                                           this.plate.toString(),
		                                   new Timestamp(System.currentTimeMillis() + 2000)
		);
		AuditLog auditLog2 = new AuditLog( tester,
                                           AuditType.CREATE_EVENT,
                                           this.cellOrigin,
                                           this.cellOrigin.toString(),
                                           new Timestamp(System.currentTimeMillis() + 3000)
		);
		getDAO().save(auditLog1);
		getDAO().save(auditLog2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(auditLog1));
		assertTrue(list.contains(auditLog2));
		
	}

	@Test
	public void testGetById() {
		
		AuditLog auditLog = new AuditLog( tester,
                                          AuditType.CREATE_EVENT,
                                          this.plate,
                                          this.plate.toString(),
                                          new Timestamp( System.currentTimeMillis() )
		);
		getDAO().save(auditLog);
		
		session.flush();
		session.clear();
		
		AuditLog savedAuditLog = getDAO().getById(new Long(auditLog.getId()));
		assertEquals(auditLog, savedAuditLog);
		assertNotSame(auditLog, savedAuditLog);
		assertTrue(auditLog.getId() == savedAuditLog.getId());
		
	}

	@Test
	public void testSave() {
		
		AuditLog auditLog = new AuditLog( tester,
                                          AuditType.CREATE_EVENT,
                                          this.plate,
                                          this.plate.toString(),
                                          new Timestamp( System.currentTimeMillis() )
		);

		getDAO().save(auditLog);
		
		session.flush();
		session.clear();
		
		AuditLog savedAuditLog = getDAO().getById(new Long(auditLog.getId()));
		assertEquals( auditLog, savedAuditLog );
		assertEquals( auditLog.getUser().getDoneAuditings(), savedAuditLog.getUser().getDoneAuditings() );
		assertNotSame(auditLog, savedAuditLog);
	}
}
