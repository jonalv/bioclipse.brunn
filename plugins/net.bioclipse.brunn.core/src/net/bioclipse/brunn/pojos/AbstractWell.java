/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class AbstractWell extends AbstractBaseObject {

	protected Set<WellFunction> wellFunctions;
	protected int col;
	protected char row;
	
	public AbstractWell() {
	    super();
	    wellFunctions = new HashSet<WellFunction>();
    }

	public AbstractWell(User creator, String name, int col, char row) {
	    super(creator, name);
	    this.wellFunctions = new HashSet<WellFunction>();
	    this.wellFunctions.add( new WellFunction(creator, "raw", "" + row + col, this) );
	    this.col = col;
	    this.row = row;
    }

	/**
	 * @return  the col
	 */
	public int getCol() {
    	return col;
    }

	/**
	 * @param col  the col to set
	 */
	public void setCol(int col) {
    	this.col = col;
    }

	/**
	 * @return  the row
	 */
	public char getRow() {
    	return row;
    }

	/**
	 * @param row  the row to set
	 */
	public void setRow(char row) {
    	this.row = row;
    }

	public Set<WellFunction> getWellFunctions() {
    	return wellFunctions;
    }

	public void setWellFunctions(Set<WellFunction> wellFunctions) {
    	this.wellFunctions = wellFunctions;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof AbstractWell) )
		    return false;
	    final AbstractWell other = (AbstractWell) obj;
	    if (col != other.getCol())
		    return false;
	    if (row != other.getRow())
		    return false;
	    if (wellFunctions == null) {
		    if (other.getWellFunctions() != null)
			    return false;
	    }
	    else if (!wellFunctions.equals(other.getWellFunctions()))
		    return false;
	    return true;
    }
}
