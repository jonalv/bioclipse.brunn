package net.bioclipse.brunn.business.folder;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;

public abstract class AbstractDAOBasedFolderManager {
	
	protected IFolderDAO       folderDAO;
	protected IUniqueFolderDAO uniqueFolderDAO;
	protected IAuditService    auditService;
	
	public IAuditService getAuditService() {
    	return auditService;
    }
	public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }
	public void setFolderDAO(IFolderDAO folderDAO) {
    	this.folderDAO = folderDAO;
    }
	public void setUniqueFolderDAO(IUniqueFolderDAO uniqueFolderDAO) {
    	this.uniqueFolderDAO = uniqueFolderDAO;
    }
	
}
