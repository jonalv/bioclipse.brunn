/**
 * 
 */
package net.bioclipse.brunn.pojos;

/**
 * @author jonathan
 *
 */
public class SampleMarker extends AbstractBaseObject {

	private AbstractSample sample;
	private Well well;
	
	public SampleMarker() {
	    super();
    }

	public SampleMarker(User creator, String name, AbstractSample sample, Well well) {
	    super(creator, name);
	    this.sample = sample;
	    this.well = well;
	    well.getSampleMarkers().add(this);
    }

	/**
	 * @return  the sample
	 */
	public AbstractSample getSample() {
    	return sample;
    }

	/**
	 * @param sample  the sample to set
	 */
	public void setSample(AbstractSample sample) {
    	this.sample = sample;
    }

	/**
	 * @return  the well
	 */
	public Well getWell() {
    	return well;
    }

	/**
	 * @param well  the well to set
	 */
	public void setWell(Well well) {
    	this.well = well;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof SampleMarker) )
		    return false;
	    final SampleMarker other = (SampleMarker) obj;
	    if (sample == null) {
		    if (other.getSample() != null)
			    return false;
	    }
	    else if (!sample.equals(other.getSample()))
		    return false;
	    return true;
    }

	public SampleMarker deepCopy() {

		SampleMarker sampleMarker = new SampleMarker();
		sampleMarker.setName(name);
		sampleMarker.setCreator(creator);
		sampleMarker.setDeleted(deleted);
		sampleMarker.setHashCode(hashCode);
		sampleMarker.setId(id);
		if( sample != null ) {
			sampleMarker.setSample(sample.deepCopy());
		}
	    return sampleMarker;
    }

	public SampleMarker makeNewCopy(User creator) {
		
		SampleMarker sampleMarker = new SampleMarker();
		sampleMarker.setName(name);
		sampleMarker.setCreator(creator);
		sampleMarker.setDeleted(deleted);
		if( sample != null ) {
			sampleMarker.setSample(sample.makeNewCopy(creator));
			sampleMarker.getSample().setSampleMarker(sampleMarker);
		}
	    return sampleMarker;
    }
}
