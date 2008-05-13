/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.graphics.Point;

/**
 * @author jonathan
 *
 */
public class AbstractPlate extends AbstractBasePlate {

	
	protected Set<Well> wells;
	
	AbstractPlate() {
	    super();
	    wells = new HashSet<Well>();
    }

	AbstractPlate(User creator, String name, int rows, int cols) {
	    
		super(creator, name, new HashSet<PlateFunction>(), rows, cols);

    }

	public static Plate createPlate( User creator, 
	                                 String name, 
	                                 MasterPlate masterPlate, 
	                                 String barcode,
	                                 Folder folder) {
		
		if(masterPlate.getPlatesLeft() < 1) {
			throw new IllegalStateException("The number of plates created of this master plates has run out");
		}
		
		Plate plate = new Plate( creator, 
				                 name, 
				                 masterPlate.getRows(), 
				                 masterPlate.getCols(), 
				                 masterPlate );
		
		plate.setMasterPlate(masterPlate);
		
		masterPlate.setPlatesLeft(masterPlate.getPlatesLeft()-1);
		
		//copy wells
		Set<Well> wells = new HashSet<Well>();
		for(Well well : masterPlate.getWells()) {
			Well copy = well.makeNewCopy();
			copy.setPlate(plate);
			wells.add(copy);
		}
		plate.setWells(wells);
		
		plate.setBarcode(barcode);
		folder.getObjects().add(plate);
		
		//copy platefunctions
		Set<PlateFunction> plateFunctions = new HashSet<PlateFunction>();
		for( PlateFunction pf : masterPlate.getPlateFunctions() ) {
			PlateFunction copy = pf.makeNewCopy();
			copy.setPlate( plate );
			plateFunctions.add( copy );
		}
		plate.setPlateFunctions(plateFunctions);
		
		return plate;
	}
	
	public static MasterPlate createMasterPlate( User creator, 
	                                             String name, 
	                                             PlateLayout plateLayout,
	                                             Folder folder, 
	                                             int numOfPlates ) {
		
	    MasterPlate masterPlate = new MasterPlate( creator, 
	    										   name, 
	    										   plateLayout.getRows(),
	    										   plateLayout.getCols(), 
	    										   numOfPlates );
	    
	    //create wells from layoutWells
	    Set<Well> wells = new HashSet<Well>();
		for (LayoutWell layoutWell : plateLayout.getLayoutWells()) {
			
			Well well = new Well(creator,
								 layoutWell.getName(),
					             layoutWell.getCol(), 
					             layoutWell.getRow(), 
					             masterPlate
			);
			
			//copy wellFunctions
			Set<WellFunction> wellFunctions = new HashSet<WellFunction>();
			for( WellFunction wellFunction : layoutWell.getWellFunctions() ) {
				WellFunction newCopy = wellFunction.makeNewCopy();
				newCopy.setCreator(creator);
				wellFunctions.add(newCopy);
			}
			well.setWellFunctions(wellFunctions);
			
			//copy from layoutMarker to SampleMarker
			Set<SampleMarker> sampleMarkers = new HashSet<SampleMarker>();
			for (LayoutMarker layoutMarker : layoutWell.getLayoutMarkers()) {
				sampleMarkers.add(new SampleMarker(creator, layoutMarker.getName(), null, well));
			}
			well.setSampleMarkers(sampleMarkers);
			
			wells.add(well);
		}
		
		// copy platefunctions
		Set<PlateFunction> plateFunctions = new HashSet<PlateFunction>();
		for( PlateFunction pf : plateLayout.getPlateFunctions() ) {
			PlateFunction newCopy = pf.makeNewCopy();
			newCopy.setCreator(creator);
			plateFunctions.add( newCopy );
		}
		masterPlate.setPlateFunctions(plateFunctions);
		
		masterPlate.setWells(wells);
		
		folder.getObjects().add(masterPlate);
		return masterPlate;
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
	    if ( !(obj instanceof AbstractPlate) )
		    return false;
	    final AbstractPlate other = (AbstractPlate) obj;
	    if (wells == null) {
		    if (other.getWells() != null)
			    return false;
	    }
	    else if (!wells.equals(other.getWells()))
		    return false;
	    return true;
    }

	public Set<Well> getWells() {
    	return wells;
    }

	public void setWells(Set<Well> wells) {
    	this.wells = wells;
    }
	
	public void delete() {
		super.delete();
		for (Well well : wells) {
	        well.delete();
        }
		for (PlateFunction plateFunction : plateFunctions) {
	        plateFunction.delete();
        }
		for (AbstractAnnotationInstance annotationInstance : getAbstractAnnotationInstances()) {
	        annotationInstance.delete();
        }
	}
	
	//TODO: Redo this with hash to not get O(n) but O(1)?
	public Well getWell(int col, char row) {
		
		for(Well well : wells) {
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
	public Well getWell(Point point) {
		return getWell( point.x, (char)(point.y+'a'-1) );
    }
}
