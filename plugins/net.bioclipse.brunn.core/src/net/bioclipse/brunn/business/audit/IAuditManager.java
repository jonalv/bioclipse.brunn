package net.bioclipse.brunn.business.audit;

import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.User;

/**
 * Definition of the methods in AuditManager. 
 * @author  jonathan
 */
public interface IAuditManager {

	public IAuditService getAuditService();
	
	public void setAuditService(IAuditService auditService);
	
	public Collection<User> getAllUsers();
	
	public void edit(User user);
	
	public void delete(User user);
	
	public User getUser(long id);
	
	public long createUser(User creator, String name, String password, boolean isAdmin);
}
