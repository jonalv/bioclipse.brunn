package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;


public class PatientOrigin extends AbstractAuditableObject {
	
	private String lid;
	private Set<PatientSample> patientSamples;

	public PatientOrigin() {
	    super();
	    patientSamples = new HashSet<PatientSample>();
    }

	public PatientOrigin(User creator, String name, String lid) {
	    super(creator, name);
	    this.lid = lid;
	    patientSamples = new HashSet<PatientSample>();
    }

	public Set<PatientSample> getPatientSamples() {
	
	    return patientSamples;
    }

	public String getLid() {
    	return lid;
    }

	public void setLid(String lid) {
    	this.lid = lid;
    }

	public void setPatientSamples(Set<PatientSample> patientSamples) {
    	this.patientSamples = patientSamples;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if (!(obj instanceof PatientOrigin))
		    return false;
	    final PatientOrigin other = (PatientOrigin) obj;
	    if (lid == null) {
		    if (other.getLid() != null)
			    return false;
	    }
	    else if (!lid.equals(other.getLid()))
		    return false;
	    if (patientSamples == null) {
		    if (other.getPatientSamples() != null)
			    return false;
	    }
	    return true;
    }

	public PatientOrigin deepCopy() {
	    PatientOrigin result = new PatientOrigin();
		result.setCreator(creator);
		result.setName(name);
		result.setDeleted(deleted);

		result.setHashCode(hashCode);
		result.setId(id);
		result.setLid(lid);
		
		HashSet<PatientSample> patientSamples = new HashSet<PatientSample>();
		for(PatientSample patientSample : this.getPatientSamples()){
			patientSamples.add(patientSample.deepCopy());
		}
		result.setPatientSamples(patientSamples);
		
	    return result;
    }
	
	@Override
	public void delete() {
		if(this.patientSamples.size() > 0) {
			throw new IllegalStateException("can not delete patientorigin that has patient samples");
		}
	    super.delete();
	} 
	
	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
	    super.accept(extractFolderObjects);
	    extractFolderObjects.visit(this);
	}
}
