package net.bioclipse.brunn.business;

import java.sql.Timestamp;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.genericDAO.IAuditLogDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.AbstractAuditableObject;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.User;

/**
 * Performs auditing on auditable objects when called upon.
 * 
 * @author jonathan
 *
 */
public class AuditService implements IAuditService {

	private IAuditLogDAO auditLogDAO;
	
	public void audit( User user, 
			            AuditType auditType,
			            AbstractAuditableObject auditedObject) {
		user = ((IUserDAO) Springcontact.getBean("userDAO")).merge(user);
		AuditLog auditLog = new AuditLog(
				user, 
				auditType, 
				auditedObject, 
				auditedObject.toString(), 
				new Timestamp(System.currentTimeMillis())
		);
		
		auditLogDAO.save(auditLog);
		
	}

	/**
	 * @param auditLogDAO  the auditLogDAO to set
	 */
	public void setAuditLogDAO(IAuditLogDAO auditLogDAO) {
	    this.auditLogDAO = auditLogDAO;
    }
}