package net.bioclipse.brunn.business;

import net.bioclipse.brunn.pojos.AbstractAuditableObject;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.User;

/**
 *  Defines the methods of an AuditService.
 * 
 * @author jonathan
 *
 */
public interface IAuditService {

	public void audit( User user, 
                        AuditType auditType, 
                        AbstractAuditableObject auditedObject
	);
}
	