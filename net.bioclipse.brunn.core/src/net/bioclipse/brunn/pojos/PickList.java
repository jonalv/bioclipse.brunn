package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

public class PickList extends AbstractAuditableObject {

	private PlateLayout  plateLayout;
	private Set<Picking> pickings;
	
	public PickList() {
	    super();
	    this.pickings = new HashSet<Picking>();
    }

	public PickList(User creator, String name, PlateLayout plateLayout) {
	    super(creator, name);
	    this.plateLayout = plateLayout;
	    this.pickings = new HashSet<Picking>();
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    final PickList other = (PickList) obj;
	    if (pickings == null) {
		    if (other.getPickings() != null)
			    return false;
	    }
	    else if (!pickings.equals(other.getPickings()))
		    return false;
	    return true;
    }

	public Set<Picking> getPickings() {
    	return pickings;
    }

	public void setPickings(Set<Picking> pickings) {
    	this.pickings = pickings;
    }

	public PlateLayout getPlateLayout() {
    	return plateLayout;
    }

	public void setPlateLayout(PlateLayout plateLayout) {
    	this.plateLayout = plateLayout;
    }

	//TODO: check all deeppCopy for the rest of the classes. Perhaps write some sort of 
	//method that splits the work over the parent classes 
	public PickList deepCopy() {
	    
		PickList result = new PickList();
		result.setName(name);
		result.setCreator(creator);
		result.setDeleted(deleted);
		result.setAuditLogs(auditLogs);
		
		result.setId(id);
		result.setHashCode(hashCode);
		
		Set<Picking> pickings = new HashSet<Picking>();
		for(Picking p : this.pickings) {
			pickings.add(p.deepCopy());
		}
		
		return result;
    }
	
	public PickList makeNewCopy() {
	    
		PickList result = new PickList();
		result.setName(name);
		result.setCreator(creator);
		result.setDeleted(deleted);
		result.setAuditLogs(auditLogs);

		Set<Picking> pickings = new HashSet<Picking>();
		for(Picking p : this.pickings) {
			pickings.add(p.deepCopy());
		}
		
		return result;
    }

	
	public void delete() {
		
		super.delete();
		for( Picking p : pickings ) {
			p.delete();
		}
	}
}
