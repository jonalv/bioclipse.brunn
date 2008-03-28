package net.bioclipse.brunn.business.exportScript;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IExportScriptDAO;
import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;

public class AbstractDAOBasedExportScriptManager {

	protected IFolderDAO       folderDAO;
	protected IUniqueFolderDAO uniqueFolderDAO;      
	protected IAuditService    auditService;
	protected IExportScriptDAO exportScriptDAO;

	public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }
	
	public void setExportScriptDAO(IExportScriptDAO exportScriptDAO) {
    	this.exportScriptDAO = exportScriptDAO;
    }
	
	public void setFolderDAO(IFolderDAO folderDAO) {
    	this.folderDAO = folderDAO;
    }

	public void setUniqueFolderDAO(IUniqueFolderDAO uniqueFolderDAO) {
    	this.uniqueFolderDAO = uniqueFolderDAO;
    }
}
