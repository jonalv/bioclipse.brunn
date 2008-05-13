package net.bioclipse.brunn.business.plateLayout;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IAnnotationInstanceDAO;
import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.genericDAO.ILayoutWellDAO;
import net.bioclipse.brunn.genericDAO.IPlateLayoutDAO;
import net.bioclipse.brunn.genericDAO.IPlateTypeDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;

/**
 * A base for PlateLayoutManager containing getters and setters for daos.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractDAOBasedPlateLayoutManager implements IPlateLayoutManager {
	
	protected IPlateLayoutDAO        plateLayoutDAO;
	protected IPlateTypeDAO          plateTypeDAO;
	protected IAnnotationInstanceDAO annotationInstanceDAO;
	protected IUniqueFolderDAO       uniqueFolderDAO;
	protected IFolderDAO             folderDAO;
	protected IAuditService          auditService;
	protected ILayoutWellDAO         layoutWellDAO;
	protected IUserDAO               userDAO;
	
    public IAuditService getAuditService() {
    	return auditService;
    }

    public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }

    public void setAnnotationInstanceDAO(IAnnotationInstanceDAO annotationDAO) {
    	this.annotationInstanceDAO = annotationDAO;
    }

    public void setPlateLayoutDAO(IPlateLayoutDAO plateLayoutDAO) {
    	this.plateLayoutDAO = plateLayoutDAO;
    }

    public void setPlateTypeDAO(IPlateTypeDAO plateTypeDAO) {
    	this.plateTypeDAO = plateTypeDAO;
    }
    
	public void setUniqueFolderDAO(IUniqueFolderDAO uniqueFolderDAO) {
    	this.uniqueFolderDAO = uniqueFolderDAO;
    }
	public void setFolderDAO(IFolderDAO folderDAO) {
    	this.folderDAO = folderDAO;
    }

	public void setLayoutWellDAO(ILayoutWellDAO layoutWellDAO) {
    	this.layoutWellDAO = layoutWellDAO;
    }

	public void setUserDAO(IUserDAO userDAO) {
    	this.userDAO = userDAO;
    }	
}
