package net.bioclipse.brunn.business.origin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.User;

/**
 * Definition of the methods in OriginManager. 
 * @author  jonalv
 */
public interface IOriginManager {

	public IAuditService getAuditService();
	public void setAuditService(IAuditService auditService);
	
	public Collection<CellOrigin>    getAllCellOrigins();
	public Collection<DrugOrigin>    getAllDrugOrigins();
	public Collection<PatientOrigin> getAllPatientOrigins();
	
	public long createDrugOrigin( User        creator,
	                              String      name, 
	                              InputStream structure,
	                              double      molecularWeight,
	                              Folder      folder ) throws IOException;
	
	public long createCellOrigin( User creator,
	                              String name, 
	                              Folder folder );
	
	public long createPatientOrigin( User creator,
	                                 String name,
	                                 String lid,
	                                 Folder folder );
	
	public void edit(User user, CellOrigin cellOrigin);
	public void edit(User user, DrugOrigin drugOrigin);
	public void edit(User user, PatientOrigin patientOrigin);
	
	public void delete(User user, CellOrigin cellOrigin);
	public void delete(User user, DrugOrigin drugOrigin);
	public void delete(User user, PatientOrigin patientOrigin);
	
	public DrugOrigin getDrugOrigin(long id);
	public CellOrigin getCellOrigin(long id);
	public PatientOrigin getPatientOrigin(long id);
	
	public Collection<CellOrigin>    getAllCellOriginsNotDeleted();
	public Collection<DrugOrigin>    getAllDrugOriginsNotDeleted();
	public Collection<PatientOrigin> getAllPatientOriginsNotDeleted();
}
