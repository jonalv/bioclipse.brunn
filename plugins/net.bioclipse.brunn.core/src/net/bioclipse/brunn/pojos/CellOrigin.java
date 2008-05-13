package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class CellOrigin extends AbstractAuditableObject {

	private Set<CellSample> cellSamples;

	public CellOrigin() {
	    super();
	    this.cellSamples = new HashSet<CellSample>();
    }

	public CellOrigin(User creator, String name) {
	    
		super(creator, name);
	    this.cellSamples = new HashSet<CellSample>();
    }

	public Set<CellSample> getCellSamples() {
    	return cellSamples;
    }

	public void setCellSamples(Set<CellSample> cellSamples) {
    	this.cellSamples = cellSamples;
    }
	
	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof CellOrigin) )
		    return false;
	    return true;
    }

	public void delete() {
		
		if(cellSamples.size() != 0) {
			throw new IllegalStateException("Delete operation not allowed, Cellorigin " + this + " has cellsamples");
		}
		super.delete();
	}

	public CellOrigin deepCopy() {
		
		CellOrigin result = new CellOrigin();
		result.setCreator(creator);
		result.setName(name);
		result.setDeleted(deleted);
		
		result.setHashCode(hashCode);
		result.setId(id);
		
		HashSet<CellSample> cellSamples = new HashSet<CellSample>();
		for(CellSample cs : this.cellSamples) {
			cellSamples.add(cs.deepCopy());
		}
		result.setCellSamples(cellSamples);
		
	    return result;
    }
	
	public CellOrigin makeNewCopy() {
		
		CellOrigin result = new CellOrigin();
		result.setCreator(creator);
		result.setName(name);
		result.setDeleted(deleted);
		
		HashSet<CellSample> cellSamples = new HashSet<CellSample>();
		for(CellSample cs : this.cellSamples) {
			cellSamples.add(cs.makeNewCopy());
		}
		result.setCellSamples(cellSamples);
		
	    return result;
    }

	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
		super.accept(extractFolderObjects);
		extractFolderObjects.visit(this);
	}
}
