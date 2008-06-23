package net.bioclipse.brunn.business.plateLayout;

import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.AbstractBasePlate;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.WellFunction;

/**
 * Definition of the methods in PlateLayoutManager. 
 * @author  jonathan
 */
public interface IPlateLayoutManager {
	
	public long createPlateType( User creator, int cols, int rows, String name, Folder folder );
	public void edit(User editor, PlateType plateType);
	
	public long createPlateLayout( User creator, String name, PlateType plateType, Folder folder );
	public void edit(User editor, PlateLayout plateLayout);
	
	public Collection<PlateLayout> getAllPlateLayouts();
	public Collection<PlateType> getAllPlateTypes();
	
	public IAuditService getAuditService();
	public void setAuditService( IAuditService auditService );
	
	public PlateLayout getPlateLayout( long plateLayoutId );
	public PlateType getPlateType( long plateTypeId );
	
	public long createWellFunction( User creator,
	                                String name,
	                                LayoutWell well,
	                                String expression);
	
	public long createPlateFunction( User creator,
	                                 String name,
	                                 AbstractBasePlate plate,
	                                 String expression, 
	                                 double goodFrom,
	                                 double goodTo );
	
	public long createPlateFunction( User creator,
	                                 String name,
	    	                         AbstractBasePlate plate,
	    	                         String expression );
	
	public Collection<PlateType> getAllPlateTypesNotDeleted();
	
	public Collection<PlateLayout> getAllPlateLayoutsNotDeleted();
	public void evictFromLazyLoading(PlateLayout toBeSaved);
}
