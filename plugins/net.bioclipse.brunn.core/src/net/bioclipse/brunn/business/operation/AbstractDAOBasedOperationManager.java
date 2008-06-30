package net.bioclipse.brunn.business.operation;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.IInstrumentDAO;
import net.bioclipse.brunn.genericDAO.IMeasurementDAO;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.genericDAO.IResultTypeDAO;
import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;

public abstract class AbstractDAOBasedOperationManager implements IOperationManager {

	protected IInstrumentDAO      instrumentDAO;
	protected IMeasurementDAO     measurementDAO;
	protected IResultTypeDAO      resultTypeDAO;
	protected IUserDAO            userDAO;
	protected ISampleContainerDAO sampleContainerDAO;
	protected IAuditService       auditService;
	protected IPlateDAO           plateDAO;
	
	public void setInstrumentDAO(IInstrumentDAO instrumentDAO) {
    	this.instrumentDAO = instrumentDAO;
    }
	
	public void setMeasurementDAO(IMeasurementDAO measurementDAO) {
    	this.measurementDAO = measurementDAO;
    }
	
	public void setResultTypeDAO(IResultTypeDAO resultTypeDAO) {
    	this.resultTypeDAO = resultTypeDAO;
    }

	public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }

	public void setUserDAO(IUserDAO userDAO) {
    	this.userDAO = userDAO;
    }

	public void setSampleContainerDAO(ISampleContainerDAO sampleContainerDAO) {
    	this.sampleContainerDAO = sampleContainerDAO;
    }

	public void setPlateDAO(IPlateDAO plateDAO) {
    	this.plateDAO = plateDAO;
    }
}
