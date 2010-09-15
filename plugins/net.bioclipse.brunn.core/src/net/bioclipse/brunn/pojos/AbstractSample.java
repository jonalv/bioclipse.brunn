/**
 * 
 */
package net.bioclipse.brunn.pojos;


/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractSample extends AbstractAuditableObject {

	private SampleContainer sampleContainer;
	protected SampleMarker  sampleMarker;
		
	public AbstractSample() {
	}

	/**
	 * @param creator
	 * @param name
	 */
	public AbstractSample(User creator, String name, SampleContainer sampleContainer) {
		super(creator, name);
		this.sampleContainer = sampleContainer;
	}

	/**
	 * @return  the sampleContainer
	 */
    public SampleContainer getSampleContainer() {
    	return sampleContainer;
    }

	/**
	 * @param sampleContainer  the sampleContainer to set
	 */
    public void setSampleContainer(SampleContainer sampleContainer) {
    	this.sampleContainer = sampleContainer;
    }
    
    public abstract AbstractSample deepCopy();
    public abstract AbstractSample makeNewCopy(User creator);
    
	public SampleMarker getSampleMarker() {
    	return sampleMarker;
    }

	public void setSampleMarker(SampleMarker sampleMarker) {
    	this.sampleMarker = sampleMarker;
    }

}
