package net.bioclipse.brunn.business.sample;

import net.bioclipse.brunn.business.AuditService;
import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;

public abstract class AbstractDAOBasedSampleManager implements ISampleManager {

	protected IAuditService auditService;
	protected ISampleContainerDAO sampleContainerDAO;
	
	public AbstractDAOBasedSampleManager() {
	    super();
    }

	public AbstractDAOBasedSampleManager(AuditService auditService, ISampleContainerDAO sampleContainerDAO) {
	    super();
	    this.auditService = auditService;
	    this.sampleContainerDAO = sampleContainerDAO;
    }

	public void setAuditService(IAuditService auditService) {
    	this.auditService = auditService;
    }
	
	public void setSampleContainerDAO(ISampleContainerDAO sampleContainerDAO) {
    	this.sampleContainerDAO = sampleContainerDAO;
    }
}
