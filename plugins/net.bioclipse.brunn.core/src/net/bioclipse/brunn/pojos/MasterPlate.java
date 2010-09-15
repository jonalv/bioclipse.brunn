package net.bioclipse.brunn.pojos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class MasterPlate extends AbstractPlate {

	private boolean locked;
	private Set<Plate> plates;
	private int platesLeft;

	MasterPlate() {
	    super();
	    plates = new HashSet<Plate>();
    }
	
	/**
	 * Package protected constructor not to be used when creating a MasterPlate.
	 * 
	 * Use a PlateManager instead
	 */
	MasterPlate(User creator, String name, int rows, int cols, int platesLeft) {
		
	    super(creator, name, rows, cols);
	    this.locked = false;
	    this.wells = new HashSet<Well>();
	    plates = new HashSet<Plate>();
	    this.platesLeft = platesLeft;
    }
	
	public boolean isLocked() {
    	return locked;
    }

	/**
	 * @param locked  the locked to set
	 */
	public void setLocked(boolean locked) {
    	this.locked = locked;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof MasterPlate) )
		    return false;
	    final MasterPlate other = (MasterPlate) obj;
	    if (locked != other.isLocked() &&
	    	platesLeft != other.getPlatesLeft()	)
		    return false;
	    return true;
    }

	public MasterPlate deepCopy() {
	    
		MasterPlate masterPlate = new MasterPlate();
		
		masterPlate.setCreator(creator);
		masterPlate.setName(name);
		masterPlate.setRows(rows);
		masterPlate.setCols(cols);
		masterPlate.setDeleted(deleted);
		masterPlate.setHashCode(hashCode);
		masterPlate.setId(id);
		masterPlate.setPlatesLeft(platesLeft);
		
		for (Well well : wells) {
	        Well copy = well.deepCopy();
	        copy.setPlate(masterPlate);
	        masterPlate.getWells().add(copy);
        }
		
		for (PlateFunction function : plateFunctions) {
	        PlateFunction copy = function.deepCopy();
	        copy.setPlate(masterPlate);
	        masterPlate.getPlateFunctions().add(copy);
        }
		
		for (AbstractAnnotationInstance annotationInstance : this.getAbstractAnnotationInstances()) {
			AbstractAnnotationInstance copy = annotationInstance.deepCopy();
	        copy.setAbstractAnnotatableObject(masterPlate);
	        masterPlate.getAbstractAnnotationInstances().add(copy);
        }
		
		return masterPlate;
    }

	public MasterPlate makeNewCopy(User creator) {
	    
		MasterPlate masterPlate = new MasterPlate();
		
		masterPlate.setCreator(creator);
		masterPlate.setName(name);
		masterPlate.setRows(rows);
		masterPlate.setCols(cols);
		masterPlate.setDeleted(deleted);
		
		for (Well well : wells) {
	        Well copy = well.makeNewCopy(creator);
	        copy.setPlate(masterPlate);
	        masterPlate.getWells().add(copy);
        }
		
		for (PlateFunction function : plateFunctions) {
	        PlateFunction copy = function.makeNewCopy(creator);
	        copy.setPlate(masterPlate);
	        masterPlate.getPlateFunctions().add(copy);
        }
		
		for (AbstractAnnotationInstance annotationInstance : this.getAbstractAnnotationInstances()) {
			AbstractAnnotationInstance copy = annotationInstance.makeNewCopy(creator);
	        copy.setAbstractAnnotatableObject(masterPlate);
	        masterPlate.getAbstractAnnotationInstances().add(copy);
        }
		
		return masterPlate;
    }

	
	//TODO: write JUnit test for this method
	public Set<SampleMarker> getDrugMarkers() {

		HashMap<String, SampleMarker> result = new HashMap<String, SampleMarker>();
		for( Well well : wells ) {
			for( SampleMarker sm : well.getSampleMarkers() ) {
				if( sm.getName().matches("M\\d+") ) {
					result.put(sm.getName(), sm);
				}
			}
		}
	    return new HashSet<SampleMarker>(result.values());
    }
	
	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
		super.accept(extractFolderObjects);
		extractFolderObjects.visit(this);
	}

	public Set<Plate> getPlates() {
    	return plates;
    }

	public void setPlates(Set<Plate> plates) {
    	this.plates = plates;
    }

	public int getPlatesLeft() {
    	return platesLeft;
    }

	public void setPlatesLeft(int platesLeft) {
    	this.platesLeft = platesLeft;
    }
}
