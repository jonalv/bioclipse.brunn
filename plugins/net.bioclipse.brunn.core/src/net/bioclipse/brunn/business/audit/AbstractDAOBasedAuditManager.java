package net.bioclipse.brunn.business.audit;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IAuditLogDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;

public abstract class AbstractDAOBasedAuditManager implements IAuditManager {

	protected IUserDAO userDAO;
	protected IAuditLogDAO auditLogDAO;
	protected IAuditService auditService;
	
	public IAuditLogDAO getAuditLogDAO() {
    	return auditLogDAO;
    }
	
	public void setAuditLogDAO(IAuditLogDAO auditLogDAO) {
    	this.auditLogDAO = auditLogDAO;
    }
	
	public IUserDAO getUserDAO() {
    	return userDAO;
    }
	
	public void setUserDAO(IUserDAO userDAO) {
    	this.userDAO = userDAO;
    }

	public IAuditService getAuditService() {
    	return auditService;
    }

	public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }
}
