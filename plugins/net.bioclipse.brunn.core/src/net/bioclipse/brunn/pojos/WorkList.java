package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

public class WorkList extends AbstractBaseObject {

	private Set<AbstractOperation> abstractOperations;
	private SampleContainer sampleContainer;
	
	public WorkList() {
	    super();
	    this.abstractOperations = new HashSet<AbstractOperation>();
    }

	public WorkList(User creator, String name, SampleContainer sampleContainer) {
	    super(creator, name);
	    this.sampleContainer = sampleContainer;
	    this.abstractOperations = new HashSet<AbstractOperation>();
	    sampleContainer.setWorkList(this);
	    
	    
    }	
	
	public Set<AbstractOperation> getAbstractOperations() {
    	return abstractOperations;
    }

	public void setAbstractOperations(Set<AbstractOperation> abstractOperations) {
    	this.abstractOperations = abstractOperations;
    }

	public SampleContainer getSampleContainer() {
    	return sampleContainer;
    }
	
	public void setSampleContainer(SampleContainer sampleContainer) {
    	this.sampleContainer = sampleContainer;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof WorkList) )
		    return false;
	    final WorkList other = (WorkList) obj;
	    if (abstractOperations == null) {
		    if (other.getAbstractOperations() != null)
			    return false;
	    }
	    else if (!abstractOperations.equals(other.getAbstractOperations())) {
	    	abstractOperations.equals(other.getAbstractOperations());
	    	return false;
	    }
		    
	    return true;
    }
	
	public void delete(){
		super.delete();
		for (AbstractOperation operation : abstractOperations) {
	        operation.delete();
        }
	}
	
	public WorkList deepCopy() {
		
		WorkList workList = new WorkList();
		workList.setCreator(creator);
		workList.setName(name);
		workList.setHashCode(hashCode);
		workList.setDeleted(deleted);
		workList.setId(id);
		
		for (AbstractOperation operation : abstractOperations) {
	        
			AbstractOperation copy = operation.deepCopy();
			copy.setWorkList(workList);
			workList.abstractOperations.add(copy);
        }
		
		return workList;
	}
	
	public WorkList makeNewCopy(User creator) {
		
		WorkList workList = new WorkList();
		workList.setCreator(creator);
		workList.setName(name);
		workList.setDeleted(deleted);
		
		for (AbstractOperation operation : abstractOperations) {
	        
			AbstractOperation copy = operation.makeNewCopy(creator);
			copy.setWorkList(workList);
			workList.abstractOperations.add(copy);
        }
		
		return workList;
	}
}
