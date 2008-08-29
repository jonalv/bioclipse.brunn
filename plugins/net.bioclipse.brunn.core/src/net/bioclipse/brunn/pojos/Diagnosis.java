package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

public class Diagnosis extends AbstractAuditableObject {

	private Set<PatientOrigin> patientOrigins;

	public Diagnosis() {
	    super();
    }

	public Diagnosis( User creator, 
	                  String name ) {
	    super(creator, name);
	    this.patientOrigins = new HashSet<PatientOrigin>();
    }

	public Set<PatientOrigin> getPatientOrigins() {
    	return patientOrigins;
    }

	public void setPatientOrigins(Set<PatientOrigin> patientOrigins) {
    	this.patientOrigins = patientOrigins;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof Diagnosis) )
		    return false;
	    final Diagnosis other = (Diagnosis) obj;
	    if (patientOrigins == null) {
		    if (other.getPatientOrigins() != null)
			    return false;
	    }
	    else if (!patientOrigins.equals(other.getPatientOrigins()))
		    return false;
	    return true;
    }

	public Diagnosis deepCopy() {
		Diagnosis diagnosis = new Diagnosis(creator, name);
		diagnosis.setId(id);
		diagnosis.setHashCode(hashCode);
	    return diagnosis;
    }
	
}
