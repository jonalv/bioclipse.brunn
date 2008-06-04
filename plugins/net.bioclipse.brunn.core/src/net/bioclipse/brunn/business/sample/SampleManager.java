package net.bioclipse.brunn.business.sample;

import java.sql.Timestamp;
import java.util.Set;

import net.bioclipse.brunn.business.AuditService;
import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.User;

public class SampleManager extends AbstractDAOBasedSampleManager implements
        ISampleManager {
	
	public SampleManager() {
	    super();
    }

	public SampleManager(AuditService auditService, ISampleContainerDAO sampleContainerDAO) {
	    super(auditService, sampleContainerDAO);
    }

//	public long addNewCellSampleToContainer( User creator, String name,
//	                                         CellOrigin cellOrigin,
//	                                         Timestamp defrostingDate,
//	                                         SampleContainer container ) {
//		
//		sampleContainerDAO.update(container);
//		CellSample cellSample = new CellSample(creator, name, cellOrigin, defrostingDate, container);
//		sampleContainerDAO.save(container);
//		auditService.audit(creator, AuditType.CREATE_EVENT, cellSample);
//		
//		return cellSample.getId();
//	}
//
//	public long addNewDrugSampleToContainer( User creator, String name,
//	                                         DrugOrigin drugOrigin,
//	                                         double concentration,
//	                                         SampleContainer container,
//	                                         SampleMarker sampleMarker ) {
//	
//		container = sampleContainerDAO.getById(container.getId());
//		DrugSample drugSample = new DrugSample(creator, name, concentration, drugOrigin, container);
//		drugSample.setSampleMarker(sampleMarker);
//		sampleMarker.setSample(drugSample);
//		sampleContainerDAO.save(container);
//		auditService.audit(creator, AuditType.CREATE_EVENT, drugSample);
//		return drugSample.getId();
//	}

	public SampleContainer getSampleContainer(long id) {
	    return sampleContainerDAO.getById(id);
    }

}
