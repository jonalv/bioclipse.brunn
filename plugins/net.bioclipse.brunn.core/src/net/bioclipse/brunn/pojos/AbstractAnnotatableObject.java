package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractAnnotatableObject extends AbstractBaseObject{

	private Set<AbstractAnnotationInstance> abstractAnnotationInstances;
	
	public AbstractAnnotatableObject(){
		abstractAnnotationInstances = new HashSet<AbstractAnnotationInstance>();
	}
	
	public AbstractAnnotatableObject(User creator, String name) {
		super(creator, name);
		abstractAnnotationInstances = new HashSet<AbstractAnnotationInstance>();
	}

	public Set<AbstractAnnotationInstance> getAbstractAnnotationInstances() {
		return abstractAnnotationInstances;
	}

	/**
	 * @param abstractAnnotations
	 */
	public void setAbstractAnnotationInstances(Set<AbstractAnnotationInstance> abstractAnnotations) {
		this.abstractAnnotationInstances = abstractAnnotations;
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
	    if ( !(obj instanceof AbstractAnnotatableObject) )
		    return false;
	    final AbstractAnnotatableObject other = (AbstractAnnotatableObject) obj;
	    if (abstractAnnotationInstances == null) {
		    if (other.abstractAnnotationInstances != null)
			    return false;
	    }
	    else if (!abstractAnnotationInstances.equals(other.abstractAnnotationInstances))
		    return false;
	    return true;
    }
}
