package net.bioclipse.brunn.pojos;

public class Picking extends AbstractBaseObject {

	private String wellA;
	private String wellB;

	public Picking() {
		
	}
	
	public Picking( User creator, String name, String wellA, String wellB ) {
	    super(creator, name);
	    this.wellA = wellA;
	    this.wellB = wellB;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    final Picking other = (Picking) obj;
	    if (wellA == null) {
		    if (other.getWellA() != null)
			    return false;
	    }
	    else if (!wellA.equals(other.getWellA()))
		    return false;
	    if (wellB == null) {
		    if (other.getWellB() != null)
			    return false;
	    }
	    else if (!wellB.equals(other.getWellB()))
		    return false;
	    return true;
    }

	public String getWellA() {
    	return wellA;
    }
	
	public void setWellA(String wellA) {
    	this.wellA = wellA;
    }
	
	public String getWellB() {
    	return wellB;
    }
	
	public void setWellB(String wellB) {
    	this.wellB = wellB;
    }

	public Picking deepCopy() {

		Picking result = new Picking();
		result.setName(name);
		result.setCreator(creator);
		result.setDeleted(deleted);
		result.setWellA(wellA);
		result.setWellB(wellB);
		
		result.setHashCode(hashCode);
		result.setId(id);
		
	    return result;
    }
	
	public Picking makeNewCopy() {

		Picking result = new Picking();
		result.setName(name);
		result.setCreator(creator);
		result.setDeleted(deleted);
		result.setWellA(wellA);
		result.setWellB(wellB);
		
	    return result;
    }
}