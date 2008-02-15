package net.bioclipse.brunn.business.plate;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.results.PlateResults;
import net.bioclipse.brunn.results.orcaParser.OrcaParser;

/**
 * Definition of the methods in PlateManager. 
 * @author  jonathan
 */
public interface IPlateManager {
	
	public Collection<Plate> getAllPlates();
	public IAuditService getAuditService();

	public void setAuditService(IAuditService auditService);

	public Plate getPlate(long plateId);

	public long createPlate( User creator, 
	                         String name, 
	                         String barcode,
	                         Folder folder,
	                         MasterPlate masterPlate, 
	                         CellOrigin cellOrigin, 
	                         Timestamp defrostingDate );
		
	public long createMasterPlate( User creator, 
	                               String name, 
	                               PlateLayout plateLayout, 
	                               Folder folder,
	                               int numOfPlates );
	
	public void edit(User editor, MasterPlate masterPlate);
	
	public long createWellFunction( User creator,
	                                String name,
	                                Well well,
	                                String expression );
	
	public long createPlateFunction( User creator,
	                                 String name,
	                                 Plate plate,
	                                 String expression, 
	                                 double goodFrom,
	                                 double goodTo );
	
	public long createPlateFunction( User creator,
	                                 String name,
	                                 MasterPlate plate,
	                                 String expression, 
	                                 double goodFrom,
	                                 double goodTo );
	
	public long createPlateFunction( User creator,
	                                 String name,
	    	                         MasterPlate plate,
	    	                         String expression );
	
	public long createPlateFunction( User creator,
	                                 String name,
	    	                         Plate plate,
	    	                         String expression );
	
	public Collection<MasterPlate> getAllMasterPlates();
	public MasterPlate getMasterPlate(long id);
	public PlateResults getPlateResults(Plate plate, IProgressMonitor monitor);
	public void edit(User editor, Plate p);
	public List<String> getAllPlateBarcodes();
	public Plate getPlate(String barCode);
	public void addResult(User user, OrcaParser parser, List<String> barcodesOfPlatesToGetResults, IProgressMonitor monitor);
	public Collection<Plate> getAllPlatesNotDeleted();
	public Collection<MasterPlate> getAllMasterPlatesNotDeleted();
	public void editMerging(User currentUser, Plate toBeSaved);
	public void evictfromLazyLoading(Plate toBeSaved);
}
