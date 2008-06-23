package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class AbstractBasePlate extends AbstractAuditableObject{

	protected int rows;
	protected int cols;
	protected Set<PlateFunction> plateFunctions;
	
	public AbstractBasePlate() {
	    super();
	    plateFunctions = new HashSet<PlateFunction>();
    }

	public AbstractBasePlate( User creator, 
	                          String name, 
	                          Set<PlateFunction> plateFunctions,
	                          int rows,
	                          int cols ) {
		
	    super(creator, name);
	    this.plateFunctions = plateFunctions;
	    this.rows = rows;
	    this.cols = cols;
    }

	public Set<PlateFunction> getPlateFunctions() {
    	return plateFunctions;
    }

	public void setPlateFunctions(Set<PlateFunction> plateFunctions) {
    	this.plateFunctions = plateFunctions;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof AbstractBasePlate) )
		    return false;
	    final AbstractBasePlate other = (AbstractBasePlate) obj;
	    if (cols != other.getCols())
		    return false;
	    if (plateFunctions == null) {
		    if (other.getPlateFunctions() != null)
			    return false;
	    }
	    else if (!plateFunctions.equals(other.getPlateFunctions()))
		    return false;
	    if (rows != other.getRows())
		    return false;
	    return true;
    }
	
	public void delete() {
		super.delete();
		for(PlateFunction p : plateFunctions) {
			p.delete();
		}
	}

	public int getCols() {
    	return cols;
    }

	public void setCols(int cols) {
    	this.cols = cols;
    }

	public int getRows() {
    	return rows;
    }

	public void setRows(int rows) {
    	this.rows = rows;
    }
}
