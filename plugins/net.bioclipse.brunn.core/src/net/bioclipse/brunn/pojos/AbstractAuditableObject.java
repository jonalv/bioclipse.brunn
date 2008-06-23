package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractAuditableObject extends AbstractAnnotatableObject {
	
	protected Set<AuditLog> auditLogs;
	
	public AbstractAuditableObject(){
		auditLogs = new HashSet<AuditLog>();
	}
	
	public AbstractAuditableObject(User creator, String name) {
		super(creator, name);
		auditLogs = new HashSet<AuditLog>();
	}

	public Set<AuditLog> getAuditLogs() {
    	return auditLogs;
    }

	public void setAuditLogs(Set<AuditLog> auditLogs) {
    	this.auditLogs = auditLogs;
    }
}
