package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class Instrument extends AbstractAuditableObject{

	private Set<Measurement> measurements;

	public Instrument() {
	    super();
	    measurements = new HashSet<Measurement>();
    }

	public Instrument( User creator, String name) {
	    super(creator, name);
	    measurements = new HashSet<Measurement>();
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof Instrument) )
		    return false;
	    return true;
    }
	
	public void delete() {
		
		if(measurements.size() != 0)
			throw new IllegalStateException("Delete operation is not allowed, instrument " + this + "has measurements");
		super.delete();
	}

	public Set<Measurement> getMeasurements() {
    	return measurements;
    }

	public void setMeasurements(Set<Measurement> measurements) {
    	this.measurements = measurements;
    }
}
