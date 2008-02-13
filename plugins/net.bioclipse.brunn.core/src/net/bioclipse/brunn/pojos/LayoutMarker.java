package net.bioclipse.brunn.pojos;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class LayoutMarker extends AbstractBaseObject {

	private LayoutWell layoutWell;
	
	public LayoutMarker() {
	    super();
    }

	public LayoutMarker(User creator, String name, LayoutWell layoutWell) {
	    super(creator, name);
	    layoutWell.getLayoutMarkers().add(this);
	    this.layoutWell = layoutWell;
    }

	/**
	 * @return  the layOutWell
	 */
    public LayoutWell getLayoutWell() {
    	return layoutWell;
    }

	/**
	 * @param layOutWell  the layOutWell to set
	 */
    public void setLayoutWell(LayoutWell layoutWell) {
    	this.layoutWell = layoutWell;
    }
	
	public LayoutMarker clone() {
		return new LayoutMarker(getCreator(), getName(), null);
	}

	public LayoutMarker deepCopy() {
	    LayoutMarker layoutMarker = new LayoutMarker();
	    layoutMarker.setName(name);
	    layoutMarker.setCreator(creator);
	    layoutMarker.setDeleted(deleted);
	    
	    layoutMarker.setHashCode(hashCode);
	    layoutMarker.setId(id);
	    
	    return layoutMarker;
    }
	
	public LayoutMarker makeNewCopy() {
	    LayoutMarker layoutMarker = new LayoutMarker();
	    layoutMarker.setName(name);
	    layoutMarker.setCreator(creator);
	    layoutMarker.setDeleted(deleted);
	    
	    return layoutMarker;
    }
}