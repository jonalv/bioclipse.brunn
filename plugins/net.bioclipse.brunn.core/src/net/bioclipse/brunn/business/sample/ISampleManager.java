package net.bioclipse.brunn.business.sample;

import java.sql.Timestamp;
import java.util.Set;

import net.bioclipse.brunn.business.AuditService;
import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.User;

public interface ISampleManager {
	
//	public long addNewCellSampleToContainer( User creator,
//	                                         String name,
//	                                         CellOrigin cellOrigin,
//	                                         Timestamp defrostingDate,
//	                                         SampleContainer container );
//	
//	public long addNewDrugSampleToContainer( User creator,
//	                                         String name,
//	                                         DrugOrigin drugOrigin,
//	                                         double concentration,
//	                                         SampleContainer container,
//	                                         SampleMarker sampleMArker);
	
	public void setAuditService(IAuditService auditservice);

	public SampleContainer getSampleContainer(long id);

}
