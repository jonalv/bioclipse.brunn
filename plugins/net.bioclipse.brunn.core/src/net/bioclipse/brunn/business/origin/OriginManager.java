package net.bioclipse.brunn.business.origin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;

/**
* This class handles everything that has to do with the Origin classes:
*  CellOrigin
*  DrugOrigin 
*  
* It should be instantiated as a Spring bean for everything to work.
*
* @author jonathan
*
*/
public class OriginManager extends AbstractDAOBasedOriginManager 
                           implements IOriginManager {
	
	private IAuditService auditService;
	
	public long createCellOrigin(User creator, String name, Folder folder) {

		folder = folderDAO.merge(folder);
		
		CellOrigin cellOrigin = new CellOrigin(creator, name);
		folder.getObjects().add(cellOrigin);
		
		folderDAO.save(folder);
		cellOriginDAO.save(cellOrigin);
		
		getAuditService().audit(creator, AuditType.UPDATE_EVENT, folder);
		getAuditService().audit(creator, AuditType.CREATE_EVENT, cellOrigin);
		
		return cellOrigin.getId();
    }

	public long createDrugOrigin( User creator, 
	                              String name, 
	                              InputStream structure, 
	                              double molecularWeight,
	                              Folder folder ) throws IOException {
		
		DrugOrigin drugOrigin = new DrugOrigin(creator, name, structure, molecularWeight);
		folder.getObjects().add(drugOrigin);
		
		drugOriginDAO.save(drugOrigin);
		folder = folderDAO.merge(folder);
		folderDAO.save(folder);
		
		getAuditService().audit(creator, AuditType.UPDATE_EVENT, folder);
		getAuditService().audit(creator, AuditType.CREATE_EVENT, drugOrigin);
		
		return drugOrigin.getId();
    }

	public void delete(User user, CellOrigin cellOrigin) {
	    
		getAuditService().audit(user, AuditType.DELETE_EVENT, cellOrigin);
	    cellOriginDAO.delete(cellOrigin);
    }

	public void delete(User user, DrugOrigin drugOrigin) {

		getAuditService().audit(user, AuditType.DELETE_EVENT, drugOrigin);
		drugOriginDAO.delete(drugOrigin);
    }

	public void edit(User user, CellOrigin cellOrigin) {

		cellOrigin = cellOriginDAO.merge(cellOrigin);
		cellOriginDAO.save(cellOrigin);
	    
		getAuditService().audit(user, AuditType.UPDATE_EVENT, cellOrigin);
    }

	public void edit(User user, DrugOrigin drugOrigin) {

		drugOrigin = drugOriginDAO.merge(drugOrigin);
		drugOriginDAO.save(drugOrigin);
		getAuditService().audit(user, AuditType.UPDATE_EVENT, drugOrigin);
    }

	public Collection<CellOrigin> getAllCellOrigins() {

	    return cellOriginDAO.findAll();
    }

	public Collection<DrugOrigin> getAllDrugOrigins() {

	    return drugOriginDAO.findAll();
    }

	public IAuditService getAuditService() {
	    return this.auditService;
    }

	public void setAuditService(IAuditService auditService) {
	    this.auditService = auditService;
    }

	public DrugOrigin getDrugOrigin(long id) {
	    
		DrugOrigin d = drugOriginDAO.getById(id);
		return d;
    }

	public CellOrigin getCellOrigin(long id) {
	    
		return cellOriginDAO.getById(id);
    }

	public Collection<CellOrigin> getAllCellOriginsNotDeleted() {
	    return cellOriginDAO.findAllNotDeleted();
    }

	public Collection<DrugOrigin> getAllDrugOriginsNotDeleted() {
	    return drugOriginDAO.findAllNotDeleted();
    }

    public long createPatientOrigin( User creator, 
                                     String name, 
                                     String lid, 
                                     Folder folder) {
		
		PatientOrigin patientOrigin = new PatientOrigin( creator, 
				                                         name, 
				                                         lid );
		folder.getObjects().add(patientOrigin);
		
		patientOriginDAO.save(patientOrigin);
		folder = folderDAO.merge(folder);
		folderDAO.save(folder);
		
		getAuditService().audit(creator, AuditType.UPDATE_EVENT, folder);
		getAuditService().audit(creator, AuditType.CREATE_EVENT, patientOrigin);
		
		return patientOrigin.getId();
    }

    public void delete(User user, PatientOrigin patientOrigin) {
		getAuditService().audit(user, AuditType.DELETE_EVENT, patientOrigin);
		drugOriginDAO.delete(patientOrigin);
    }

    public void edit(User user, PatientOrigin patientOrigin) {
		patientOrigin = patientOriginDAO.merge(patientOrigin);
		patientOriginDAO.save(patientOrigin);
	    
		getAuditService().audit(user, AuditType.UPDATE_EVENT, patientOrigin);
    }

    public Collection<PatientOrigin> getAllPatientOrigins() {
	    return patientOriginDAO.findAll();
    }

    public Collection<PatientOrigin> getAllPatientOriginsNotDeleted() {
		return patientOriginDAO.findAllNotDeleted();
	}

    public PatientOrigin getPatientOrigin(long id) {
		return patientOriginDAO.getById(id);
    }
}
