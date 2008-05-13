package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class UniqueFolder extends Folder {

	private String uniqueName;

	public UniqueFolder() {
	    super();
    }

	public UniqueFolder( User creator, String name, String uniqueName ) {
	    super();
	    this.creator = creator;
	    this.name = name;
	    this.uniqueName = uniqueName;
    }

	public String getUniqueName() {
    	return uniqueName;
    }

	public void setUniqueName(String uniqueName) {
    	this.uniqueName = uniqueName;
    }
	
	public String toString() {
		return "UniqueFolder name: " + this.name + " uniqueName: " + this.uniqueName;
	}

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof UniqueFolder) )
		    return false;
	    final UniqueFolder other = (UniqueFolder) obj;
	    if (uniqueName == null) {
		    if (other.getUniqueName() != null)
			    return false;
	    }
	    return true;
    }
	
	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
		super.accept(extractFolderObjects);
		extractFolderObjects.visit(this);
	}
}
