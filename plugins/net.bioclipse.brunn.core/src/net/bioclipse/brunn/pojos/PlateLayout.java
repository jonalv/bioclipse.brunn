/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class PlateLayout extends AbstractBasePlate {

	private PlateType plateType;
	private Set<LayoutWell> layoutWells;
	
	/**
	 * 
	 */
	public PlateLayout() {
		super();
		layoutWells    = new HashSet<LayoutWell>();
		plateFunctions = new HashSet<PlateFunction>();
	}

	public PlateLayout( User creator, 
	                    String name, 
	                    PlateType plateType) {
		
	    super(creator, name, new HashSet<PlateFunction>(), plateType.getRows(), plateType.getCols());
	    this.plateType = plateType;
	    
	    this.layoutWells = new HashSet<LayoutWell>();
	    for(char row = 'a' ; row <= 'a'+plateType.getRows() - 1; row++){
			for(int col = 1; col <= plateType.getCols() ; col++){
				
				LayoutWell layoutWell = new LayoutWell(creator,
													   ""+row+col,
													   col,
													   row,
													   this
				);
				
				layoutWells.add(layoutWell);
			}
		}
	    plateType.getPlateLayouts().add(this);
    }

	/**
     * @return the layOutWells
     */
    public Set<LayoutWell> getLayoutWells() {
    	return layoutWells;
    }

	/**
     * @param layOutWells the layOutWells to set
     */
    public void setLayoutWells(Set<LayoutWell> layoutWells) {
    	this.layoutWells = layoutWells;
    }

	/**
	 * @return  the plateType
	 */
    public PlateType getPlateType() {
    	return plateType;
    }

	/**
	 * @param plateType  the plateType to set
	 */
    public void setPlateType(PlateType plateType) {
    	this.plateType = plateType;
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
	    if ( !(obj instanceof PlateLayout) )
		    return false;
	    final PlateLayout other = (PlateLayout) obj;
	    if (layoutWells == null) {
		    if (other.getLayoutWells() != null)
			    return false;
	    }
	    else if (!layoutWells.equals(other.getLayoutWells()))
		    return false;
	    if (plateType == null) {
		    if (other.getPlateType() != null)
			    return false;
	    }
	    else if (!plateType.equals(other.getPlateType()))
		    return false;
	    return true;
    }
    
	public PlateLayout deepCopy() {
	    
		PlateLayout plateLayout = new PlateLayout();
		plateLayout.setCreator(creator);
		plateLayout.setName(name);
		plateLayout.setDeleted(deleted);
		plateLayout.setPlateType(plateType);
		plateLayout.setRows(rows);
		plateLayout.setCols(cols);

		plateLayout.setHashCode(hashCode);
		plateLayout.setId(id);
		
	    for (LayoutWell well : layoutWells) {
	        LayoutWell copy = well.deepCopy();
	        copy.setPlateLayout(plateLayout);
	        plateLayout.getLayoutWells().add(copy);
        }
	    
	    for (AbstractAnnotationInstance annotationInstance : getAbstractAnnotationInstances()) {
	    	AbstractAnnotationInstance copy = annotationInstance.deepCopy();
	    	copy.setAbstractAnnotatableObject(plateLayout);
	    	plateLayout.getAbstractAnnotationInstances().add(copy);
	    }
	    
	    for (PlateFunction function : plateFunctions) {
	    	PlateFunction copy = function.deepCopy();
	    	copy.setPlate(plateLayout);
	    	plateLayout.getPlateFunctions().add(copy);
	    }
	    
	    return plateLayout;
    }

	public PlateLayout makeNewCopy() {
	    
		PlateLayout plateLayout = new PlateLayout();
		plateLayout.setCreator(creator);
		plateLayout.setName(name);
		plateLayout.setDeleted(deleted);
		plateLayout.setPlateType(plateType);
		plateLayout.setRows(rows);
		plateLayout.setCols(cols);
		
	    for (LayoutWell well : layoutWells) {
	        LayoutWell copy = well.makeNewCopy();
	        copy.setPlateLayout(plateLayout);
	        plateLayout.getLayoutWells().add(copy);
        }
	    
	    for (AbstractAnnotationInstance annotationInstance : getAbstractAnnotationInstances()) {
	    	AbstractAnnotationInstance copy = annotationInstance.makeNewCopy();
	    	copy.setAbstractAnnotatableObject(plateLayout);
	    	plateLayout.getAbstractAnnotationInstances().add(copy);
	    }
	    
	    for (PlateFunction function : plateFunctions) {
	    	PlateFunction copy = function.makeNewCopy();
	    	copy.setPlate(plateLayout);
	    	plateLayout.getPlateFunctions().add(copy);
	    }
	    
	    return plateLayout;
    }

	
	public void delete() {
		super.delete();
		for (LayoutWell layoutWell : layoutWells) {
	        layoutWell.delete();
        }
	}

	//TODO: Redo this with hash to not get O(n) but O(1)?
	public LayoutWell getWell(int col, char row) {
		
		for(LayoutWell well : layoutWells) {
			if( well.getRow() == row && well.getCol() == col) {
				return well;
			}
		}
	    throw new IllegalArgumentException("There is no well: " + row  + col);
    }

	/**
	 * @param point with the col stored in x and row stored in y
	 * @return
	 */
	public LayoutWell getLayoutWell(Point point) {
		return getWell( point.x, (char)(point.y+'a'-1) );
    }
	
	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
		super.accept(extractFolderObjects);
		extractFolderObjects.visit(this);
	}
}
