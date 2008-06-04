package net.bioclipse.brunn.business.annotation;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IAnnotationDAO;
import net.bioclipse.brunn.genericDAO.IAnnotationInstanceDAO;

/**
 * A base for AnnotationManager containing getters and setters for daos.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractDAOBasedAnnotationManager implements IAnnotationManager {

	protected IAnnotationInstanceDAO annotationInstanceDAO;
	protected IAnnotationDAO         annotationDAO;
	protected IAuditService          auditService;
	
	public AbstractDAOBasedAnnotationManager(){
		
	}
	
	public IAuditService getAuditService() {
    	return auditService;
    }

	public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }

	public AbstractDAOBasedAnnotationManager(IAnnotationInstanceDAO annotationInstanceDAO, IAnnotationDAO annotationDAO, IAuditService auditService) {
	    super();
	    this.annotationInstanceDAO = annotationInstanceDAO;
	    this.annotationDAO = annotationDAO;
	    this.auditService = auditService;
    }

	/**
	 * @return  the annotationDaO
	 */
    public IAnnotationInstanceDAO getAnnotationInstanceDAO() {
    	return annotationInstanceDAO;
    }

	/**
	 * @param annotationDaO  the annotationDaO to set
	 */
    public void setAnnotationInstanceDAO(IAnnotationInstanceDAO annotationDAO) {
    	this.annotationInstanceDAO = annotationDAO;
    }

	/**
	 * @return  the masterAnnotationTypeDAO
	 */
	public IAnnotationDAO getAnnotationDAO() {
    	return annotationDAO;
    }

	/**
	 * @param masterAnnotationTypeDAO  the masterAnnotationTypeDAO to set
	 */
	public void setAnnotationDAO(
                                           IAnnotationDAO masterAnnotationTypeDAO) {
    	this.annotationDAO = masterAnnotationTypeDAO;
    }
}
