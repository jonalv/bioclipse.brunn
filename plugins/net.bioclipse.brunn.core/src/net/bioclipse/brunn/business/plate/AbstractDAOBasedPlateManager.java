package net.bioclipse.brunn.business.plate;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IAnnotationInstanceDAO;
import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.genericDAO.ICellSampleDAO;
import net.bioclipse.brunn.genericDAO.IDrugOriginDAO;
import net.bioclipse.brunn.genericDAO.IDrugSampleDAO;
import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.genericDAO.IPatientOriginDAO;
import net.bioclipse.brunn.genericDAO.IPatientSampleDAO;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.genericDAO.IWellDAO;

/**
 * A base for PlateManager containing getters and setters for daos.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractDAOBasedPlateManager implements
		IPlateManager {

	protected IMasterPlateDAO         masterPlateDAO;
	protected IPlateDAO               plateDAO;
	protected IAnnotationInstanceDAO  annotationInstanceDAO;
	protected IAuditService           auditService;
	protected IFolderDAO              folderDAO;
	protected IUserDAO                userDAO;
	protected ISampleContainerDAO     sampleContainerDAO;
	protected IDrugSampleDAO          drugSampleDAO;
	protected ICellSampleDAO          cellSampleDAO;
	protected IWellDAO                wellDAO;
	protected IPatientSampleDAO       patientSampleDAO;
	protected IPatientOriginDAO		  patientOriginDAO;
	protected ICellOriginDAO		  cellOriginDAO;
	protected IDrugOriginDAO		  drugOriginDAO;
	
	public IDrugOriginDAO getDrugOriginDAO() {
    	return drugOriginDAO;
    }

	public void setDrugOriginDAO(IDrugOriginDAO drugOriginDAO) {
    	this.drugOriginDAO = drugOriginDAO;
    }

	public IPatientOriginDAO getPatientOriginDAO() {
    	return patientOriginDAO;
    }

	public void setPatientOriginDAO(IPatientOriginDAO patientOriginDAO) {
    	this.patientOriginDAO = patientOriginDAO;
    }

	public ICellOriginDAO getCellOriginDAO() {
    	return cellOriginDAO;
    }

	public void setCellOriginDAO(ICellOriginDAO cellOriginDAO) {
    	this.cellOriginDAO = cellOriginDAO;
    }

	public void setCellSampleDAO(ICellSampleDAO cellSampleDAO) {
    	this.cellSampleDAO = cellSampleDAO;
    }

	public void setDrugSampleDAO(IDrugSampleDAO drugSampleDAO) {
    	this.drugSampleDAO = drugSampleDAO;
    }

	public void setSampleContainerDAO(ISampleContainerDAO sampleContainerDAO) {
    	this.sampleContainerDAO = sampleContainerDAO;
    }

	/**
	 * @param plateDAO the plateDAO to set
	 */
	public void setPlateDAO(IPlateDAO plateDAO) {
		this.plateDAO = plateDAO;
	}
		
    /**
	 * @return the auditService
	 */
    public IAuditService getAuditService() {
    	return auditService;
    }

    /**
	 * @param auditService the auditService to set
	 */
    public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }

    /**
	 * @param annotationDAO the annotationDAO to set
	 */
    public void setAnnotationInstanceDAO(IAnnotationInstanceDAO annotationDAO) {
    	this.annotationInstanceDAO = annotationDAO;
    }

	/**
	 * @param masterPlateDAO the masterPlateDAO to set 
	 */
	public void setMasterPlateDAO(IMasterPlateDAO masterPlateDAO) {
    	this.masterPlateDAO = masterPlateDAO;
    }

	public void setFolderDAO(IFolderDAO folderDAO) {
    	this.folderDAO = folderDAO;
    }

	public void setUserDAO(IUserDAO userDAO) {
    	this.userDAO = userDAO;
    }

	public void setWellDAO(IWellDAO wellDAO) {
    	this.wellDAO = wellDAO;
    }

	public void setPatientSampleDAO(IPatientSampleDAO patientSampleDAO) {
    	this.patientSampleDAO = patientSampleDAO;
    }
	
}
