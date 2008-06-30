/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class Measurement extends AbstractOperation {

	private Set<Result> results;
	private Instrument instrument;
	private ResultType resultType;
	
	public Measurement() {
	    super();
	    this.results = new HashSet<Result>();
    }

	public Measurement( User creator, 
	                    String name, 
	                    WorkList workList,
	                    Instrument instrument, 
	                    ResultType resultType ) {
	    
		super(creator, name, workList);
	    this.results = new HashSet<Result>();
	    this.instrument = instrument;
	    this.resultType = resultType;
	    instrument.getMeasurements().add(this);
	    workList.getAbstractOperations().add(this);
    }

	public Instrument getInstrument() {
    	return instrument;
    }

	public void setInstrument(Instrument instrument) {
    	this.instrument = instrument;
    }

	public Set<Result> getResults() {
    	return results;
    }

	public void setResults(Set<Result> results) {
    	this.results = results;
    }

	public ResultType getResultType() {
    	return resultType;
    }

	public void setResultType(ResultType resultType) {
    	this.resultType = resultType;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof Measurement) )
		    return false;
	    final Measurement other = (Measurement) obj;
	    if (instrument == null) {
		    if (other.getInstrument() != null)
			    return false;
	    }
	    else if (!instrument.equals(other.getInstrument()))
		    return false;
	    if (resultType == null) {
		    if (other.getResultType() != null)
			    return false;
	    }
	    else if (!resultType.equals(other.getResultType()))
		    return false;
	    if (results == null) {
		    if (other.getResults() != null)
			    return false;
	    }
	    else if (!results.equals(other.getResults()))
		    return false;
	    return true;
    }
	
	public void delete(){
		super.delete();
		for (Result result : results) {
	        result.delete();
        }
	}
	
	public Measurement deepCopy(){
		
		Measurement measurement = new Measurement();
		measurement.setCreator(creator);
		measurement.setName(name);
		measurement.setInstrument(instrument);
		measurement.setResultType(resultType);
		measurement.setDeleted(deleted);

		measurement.setHashCode(hashCode);
		measurement.setId(id);
		
		for (Result result : results) {
	        measurement.getResults().add(result.deepCopy());
        }
		
		return measurement;
	}
	
	public Measurement makeNewCopy(){
		
		Measurement measurement = new Measurement();
		measurement.setCreator(creator);
		measurement.setName(name);
		measurement.setInstrument(instrument);
		measurement.setResultType(resultType);
		measurement.setDeleted(deleted);
		
		for (Result result : results) {
	        measurement.getResults().add( result.makeNewCopy() );
        }
		
		return measurement;
	}

	public void addResult(Result result) {
	    results.add(result);
    }
}
