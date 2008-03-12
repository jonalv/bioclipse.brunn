package net.bioclipse.brunn.business.audit;

import java.util.Collection;

import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.User;

public class AuditManager extends AbstractDAOBasedAuditManager implements
        IAuditManager {

	public void delete(User user) {

		auditService.audit(user, AuditType.DELETE_EVENT, user);
	    userDAO.delete(user);
	    
    }

	public void edit(User user) {
		user = userDAO.merge(user);
	    userDAO.save(user);
	    auditService.audit(user, AuditType.UPDATE_EVENT, user);
    }

	public Collection<User> getAllUsers() {
	    
	    return userDAO.findAll();
    }

	public long createUser(User creator, String name, String password, boolean isAdmin) {
	    User user = new User(creator, name);
	    user.setAdmin(isAdmin);
	    user.setPassword(password);
	    userDAO.save(user);
	    auditService.audit(user, AuditType.CREATE_EVENT, user);
	    return user.getId();
    }

	public User getUser(long id) {
	    return userDAO.getById(id);
    }
}
