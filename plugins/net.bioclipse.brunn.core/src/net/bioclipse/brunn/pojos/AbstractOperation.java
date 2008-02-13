package net.bioclipse.brunn.pojos;

public abstract class AbstractOperation extends AbstractAuditableObject {

	private WorkList workList;
	
	public AbstractOperation() {
	    super();
    }

	public AbstractOperation(User creator, String name, WorkList workList) {
	    super(creator, name);
	    this.workList = workList;
    }

	public WorkList getWorkList() {
    	return workList;
    }

	public void setWorkList(WorkList workList) {
    	this.workList = workList;
    }
	
	public abstract AbstractOperation deepCopy();
	public abstract AbstractOperation makeNewCopy();
}

