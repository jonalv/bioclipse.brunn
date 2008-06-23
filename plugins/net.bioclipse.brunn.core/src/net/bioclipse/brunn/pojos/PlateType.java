package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class PlateType extends AbstractAuditableObject {

	private int cols;
	private int rows;
	private Set<PlateLayout> plateLayouts;
	
    public PlateType() {
	    super();
	    cols=6;
	    rows=9;
	    plateLayouts = new HashSet<PlateLayout>();
    }

	public PlateType( User creator, 
	                  int cols, 
	                  int rows, 
	                  String name ) {
		
	    super(creator, name);
	    this.cols = cols;
	    this.rows = rows;
	    plateLayouts = new HashSet<PlateLayout>();
    }

	/**
	 * @return  the cols
	 */
    public int getCols() {
    	return cols;
    }

	/**
	 * @param cols  the cols to set
	 */
    public void setCols(int cols) {
    	this.cols = cols;
    }

    
	public Set<PlateLayout> getPlateLayouts() {
    	return plateLayouts;
    }

	public void setPlateLayouts(Set<PlateLayout> plateLayout) {
    	this.plateLayouts = plateLayout;
    }

	/**
	 * @return  the rows
	 */
    public int getRows() {
    	return rows;
    }

	/**
	 * @param rows  the rows to set
	 */
    public void setRows(int rows) {
    	this.rows = rows;
    }

	/* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof PlateType) )
		    return false;
	    final PlateType other = (PlateType) obj;
	    if (cols != other.getCols())
		    return false;
	    if (rows != other.getRows())
		    return false;
	    return true;
    }
    
    @Override
    public void accept(LisObjectVisitor extractFolderObjects) {
    	super.accept(extractFolderObjects);
    	extractFolderObjects.visit(this);
    }
}
