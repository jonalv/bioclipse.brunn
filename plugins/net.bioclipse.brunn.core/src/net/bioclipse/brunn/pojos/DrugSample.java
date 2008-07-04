package net.bioclipse.brunn.pojos;

/**
 * @author jonathan
 *
 */
public class DrugSample extends AbstractSample {
	
	private double concentration;
	private DrugOrigin drugOrigin;
	private ConcUnit concUnit; 
	
	public DrugSample() {
	    super();
    }

	public DrugSample( User creator, 
	                   String name, 
	                   double concentration, 
	                   DrugOrigin drugOrigin, 
	                   SampleContainer sampleContainer,
	                   ConcUnit concUnit ) {
		
	    super(creator, name, sampleContainer);
	    this.concentration = concentration;
	    this.drugOrigin = drugOrigin;
	    drugOrigin.getDrugSamples().add(this);
	    sampleContainer.getSamples().add(this);
	    this.concUnit = concUnit;
    }

	public double getConcentration() {
    	return concentration;
    }

	public void setConcentration(double concentration) {
    	this.concentration = concentration;
    }

	public DrugOrigin getDrugOrigin() {
    	return drugOrigin;
    }

	public void setDrugOrigin(DrugOrigin drugOrigin) {
    	this.drugOrigin = drugOrigin;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof DrugSample) )
		    return false;
	    final DrugSample other = (DrugSample) obj;
	    if (Double.doubleToLongBits(concentration) != Double.doubleToLongBits(other.getConcentration()))
		    return false;
	    if (drugOrigin == null) {
		    if (other.getDrugOrigin() != null)
			    return false;
	    }
	    else if (!drugOrigin.equals(other.getDrugOrigin()))
		    return false;
	    else if( concUnit!=other.getConcUnit() ) {
	    	return false;
	    }
	    return true;
    }
	
	public ConcUnit getConcUnit() {
    	return concUnit;
    }

	public void setConcUnit(ConcUnit concUnit) {
    	this.concUnit = concUnit;
    }

	public DrugSample deepCopy(){
		
		DrugSample drugSample = new DrugSample();
		drugSample.setName(name);
		drugSample.setCreator(creator);
		drugSample.setConcentration(concentration);	
		drugSample.setDrugOrigin(drugOrigin);
		drugSample.setDeleted(deleted);
		drugOrigin.getDrugSamples().add(drugSample);
		drugSample.setSampleMarker(sampleMarker);
		drugSample.setConcUnit(concUnit);
		
		drugSample.setHashCode(hashCode);
		drugSample.setId(id);
		
		for (AbstractAnnotationInstance ai : getAbstractAnnotationInstances()) {
			AbstractAnnotationInstance copy = ai.deepCopy();
	        drugSample.getAbstractAnnotationInstances().add(ai);
	        copy.setAbstractAnnotatableObject(drugSample);
        }
		
		return drugSample;
	}
	
	public DrugSample makeNewCopy(){
		
		DrugSample drugSample = new DrugSample();
		drugSample.setName(name);
		drugSample.setCreator(creator);
		drugSample.setConcentration(concentration);
		drugSample.setConcUnit(concUnit);
		drugSample.setDrugOrigin(drugOrigin);
		drugSample.setDeleted(deleted);
		drugOrigin.getDrugSamples().add(drugSample);
		drugSample.setSampleMarker(sampleMarker);
		
		for (AbstractAnnotationInstance ai : getAbstractAnnotationInstances()) {
			AbstractAnnotationInstance copy = ai.makeNewCopy();
	        drugSample.getAbstractAnnotationInstances().add(ai);
	        copy.setAbstractAnnotatableObject(drugSample);
        }
		
		return drugSample;
	}
	
	public String toString() {
		return " Drugsample - name: " + this.name + " concentration: " + this.concentration; 
	}
}
