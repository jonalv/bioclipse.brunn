package net.bioclipse.brunn.business.operation;

import java.util.Collection;
import java.util.List;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.WorkList;
import net.bioclipse.brunn.results.PlateRead;
import net.bioclipse.brunn.results.orcaParser.OrcaParser;
import net.bioclipse.brunn.results.orcaParser.OrcaParser.OrcaPlateRead;

/**
 * Definition of the methods in OperationManager. 
 * @author  jonathan
 */
public interface IOperationManager {
	
	public void setAuditService(IAuditService auditService);
	
	public Collection<Measurement> getAllMeasurements();
	public Collection<Instrument>  getAllInstruments();
	
	public long createInstrument( User creator, 
	                               String name );
	
	public long createResultType( User creator,
	                              String name,
	                              int length );
	
	public long createMeasurement( User creator,
	                               String name,
	                               WorkList workList,
	                               Instrument instrument,
	                               ResultType resultType );
	
	public void addResult(User user, Measurement measurement, double[] result);
	
	public void edit(User user, Measurement measurement);
	public void edit(User user, Instrument instrument);
	public void edit(User user, ResultType resultType);
	
	public void delete(User user, Measurement measurement);
	public void delete(User user, Instrument instrument);
	public void delete(User user, ResultType resultType);

	public Collection<ResultType> getAllResultTypes();

	public Instrument getInstrument(long id);

	public ResultType getResultType(long id);

	public Measurement getMeasurement(long id);

	public List<ResultType> getResultTypeByName(String name);

	public List<Instrument> getInstrumentByName(String name);
	
	public void addResult(User activeUser, PlateRead pr, Plate plate);
}
