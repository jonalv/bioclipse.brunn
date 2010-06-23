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
public class LayoutWell extends AbstractWell {
	
	private PlateLayout plateLayout;
	private Set<LayoutMarker> layoutMarkers;
	
	public LayoutWell() {
	    super();
	    this.layoutMarkers = new HashSet<LayoutMarker>();
    }

	public LayoutWell(User creator, 
	                   String name,
	                   int col, 
	                   char row, 
	                   PlateLayout plateLayout) {
		
	    super(creator, name, col, row);
	    this.plateLayout = plateLayout;
	    this.layoutMarkers = new HashSet<LayoutMarker>();
	    plateLayout.getLayoutWells().add(this);
    }

	/**
     * @return the layOutMarker
     */
    public Set<LayoutMarker> getLayoutMarkers() {
    	return layoutMarkers;
    }

	/**
     * @param layOutMarker the layOutMarker to set
     */
    public void setLayoutMarkers(Set<LayoutMarker> layoutMarkers) {
    	this.layoutMarkers = layoutMarkers;
    }

	/**
	 * @return  the plateLayout
	 */
    public PlateLayout getPlateLayout() {
    	return plateLayout;
    }

	/**
	 * @param plateLayout  the plateLayout to set
	 */
    public void setPlateLayout(PlateLayout plateLayout) {
    	this.plateLayout = plateLayout;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof LayoutWell) )
		    return false;
	    final LayoutWell other = (LayoutWell) obj;
	    if (layoutMarkers == null) {
		    if (other.getLayoutMarkers() != null)
			    return false;
	    }
	    else if (!layoutMarkers.equals(other.getLayoutMarkers()))
		    return false;
	    return true;
    }

	public LayoutWell deepCopy() {
		
	    LayoutWell layoutWell = new LayoutWell();
	    layoutWell.setName(name);
	    layoutWell.setCol(col);
	    layoutWell.setRow(row);
	    layoutWell.setCreator(creator);
	    layoutWell.setDeleted(deleted);
	    
	    layoutWell.setHashCode(hashCode);
	    layoutWell.setId(id);
	    
	    for (LayoutMarker layoutMarker : layoutMarkers) {
	    	LayoutMarker copy = layoutMarker.deepCopy();
	    	copy.setLayoutWell(layoutWell);
	    	layoutWell.getLayoutMarkers().add(copy);
        }
	    
	    for (WellFunction function : wellFunctions) {
	    	WellFunction copy = function.deepCopy();
	    	copy.setWell(layoutWell);
	    	layoutWell.getWellFunctions().add(copy);
        }
	    
	    return layoutWell;
    }
	
	public LayoutWell makeNewCopy(User creator) {
		
	    LayoutWell layoutWell = new LayoutWell();
	    layoutWell.setName(name);
	    layoutWell.setCol(col);
	    layoutWell.setRow(row);
	    layoutWell.setCreator(creator);
	    layoutWell.setDeleted(deleted);
	    
	    for (LayoutMarker layoutMarker : layoutMarkers) {
	    	LayoutMarker copy = layoutMarker.makeNewCopy();
	    	copy.setLayoutWell(layoutWell);
	    	layoutWell.getLayoutMarkers().add(copy);
        }
	    
	    for (WellFunction function : wellFunctions) {
	    	WellFunction copy = function.makeNewCopy(creator);
	    	copy.setWell(layoutWell);
	    	layoutWell.getWellFunctions().add(copy);
        }
	    
	    return layoutWell;
    }
	
	public void delete() {
		super.delete();
		for (LayoutMarker layoutMarker : layoutMarkers) {
	        layoutMarker.delete();
        }
		for (WellFunction wellFunction : wellFunctions) {
			wellFunction.delete();
		}
	}
}
