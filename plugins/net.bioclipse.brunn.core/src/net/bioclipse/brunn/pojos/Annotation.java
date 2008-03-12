package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class Annotation extends AbstractAuditableObject{
	
	private Set<String> possibleValues;
	private AnnotationType annotationType;
	private Set<AbstractAnnotationInstance> annotationInstances;
	
	public Annotation() {
		
	}

	public Annotation( User creator, 
	                   String name, 
	                   Set<String> possibleValues, 
	                   AnnotationType annotationType ) {
	    
		super(creator, name);
	    
		this.possibleValues = possibleValues;
	    this.annotationType = annotationType;
	    this.annotationInstances    = new HashSet<AbstractAnnotationInstance>();
    }
	
	/**
	 * @return  the annotationType
	 */
	public AnnotationType getAnnotationType() {
    	return annotationType;
    }

	/**
	 * @param annotationType  the annotationType to set
	 */
	public void setAnnotationType(AnnotationType annotationType) {
    	this.annotationType = annotationType;
    }

	public Set<String> getPossibleValues() {
    	return possibleValues;
    }

	public void setPossibleValues(Set<String> possibleValues) {
    	this.possibleValues = possibleValues;
    }

	public Set<AbstractAnnotationInstance> getAnnotationInstances() {
    	return annotationInstances;
    }

	public void setAnnotationInstances(Set<AbstractAnnotationInstance> annotations) {
    	this.annotationInstances = annotations;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof Annotation) )
		    return false;
	    final Annotation other = (Annotation) obj;
	    if (annotationType == null) {
		    if (other.getAnnotationType() != null)
			    return false;
	    }
	    else if (annotationType != other.getAnnotationType())
		    return false;
	    if (possibleValues == null) {
		    if (other.getPossibleValues() != null)
			    return false;
	    }
	    else if (!possibleValues.equals(other.getPossibleValues()))
		    return false;
	    return true;
    }
	
	public void delete() {
		super.delete();
		for (AbstractAnnotationInstance ai : annotationInstances) {
	        ai.delete();
        }
	}
}
