/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class SampleContainer extends AbstractBaseObject {

	private Set<AbstractSample> samples;
	private Well well;
	private WorkList workList;
	
	public SampleContainer() {
	    super();
	    this.samples = new HashSet<AbstractSample>();
    }

	public SampleContainer(User creator, String name, Well well) {
	    super(creator, name);
	    this.well = well;
	    this.samples = new HashSet<AbstractSample>();
	    this.workList = new WorkList(creator, "", this);
    }

	/**
	 * @return  the samples
	 */
    public Set<AbstractSample> getSamples() {
    	return samples;
    }

	/**
	 * @param samples  the samples to set
	 */
    public void setSamples(Set<AbstractSample> samples) {
    	this.samples = samples;
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
	
	public WorkList getWorkList() {
    	return workList;
    }

	public void setWorkList(WorkList workList) {
    	this.workList = workList;
    	workList.setSampleContainer(this);
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof SampleContainer) )
		    return false;
	    final SampleContainer other = (SampleContainer) obj;
	    if (samples == null) {
		    if (other.getSamples() != null)
			    return false;
	    }
	    else if (!samples.equals(other.getSamples()))
		    return false;
	    if (workList == null) {
		    if (other.getWorkList() != null)
			    return false;
	    }
	    else if (!workList.equals(other.getWorkList()))
		    return false;
	    return true;
    }

	public SampleContainer deepCopy() {
	    
	    SampleContainer container = new SampleContainer();
	    container.setName(name);
	    container.setCreator(creator);
	    container.setDeleted(deleted);
	    
	    container.setId(id);
	    container.setHashCode(hashCode);
	    
	    for (AbstractSample sample : this.samples) {
	        AbstractSample copy = sample.deepCopy();
	    	copy.setSampleContainer(container);
	    	container.getSamples().add(copy);
        }

	    container.setWorkList(workList.deepCopy());
	    container.getWorkList().setSampleContainer(container);
	    
	    return container;
    }
	
	public void delete(){
		super.delete();
		for (AbstractSample sample : samples) {
	        sample.delete();
        }
		workList.delete();
	}

	public SampleContainer makeNewCopy(User creator) {
		
		SampleContainer container = new SampleContainer();
	    container.setName(name);
	    container.setCreator(creator);
	    container.setDeleted(deleted);
	    
	    for (AbstractSample sample : this.samples) {
	        AbstractSample copy = sample.makeNewCopy(creator);
	    	copy.setSampleContainer(container);
	    	container.getSamples().add(copy);
        }

	    container.setWorkList(workList.makeNewCopy(creator));
	    container.getWorkList().setSampleContainer(container);
	    
	    return container;
    }

	public void addSample(AbstractSample sample) {
		samples.add(sample);
	    sample.setSampleContainer(this);
    }
}
