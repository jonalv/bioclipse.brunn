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
public abstract class AbstractAnnotationInstance extends AbstractBaseObject{

	protected AbstractAnnotatableObject abstractAnnotatableObject;
	protected Annotation annotation;
	
	public AbstractAnnotationInstance(){
		
	}
	
	public AbstractAnnotationInstance(User creator, String name, Annotation annotation) {
		super(creator, name);
		this.annotation = annotation;
	}

	public AbstractAnnotatableObject getAbstractAnnotatableObject() {
    	return abstractAnnotatableObject;
    }

	public void setAbstractAnnotatableObject(
                                             AbstractAnnotatableObject abstractAnnotatableObject) {
    	this.abstractAnnotatableObject = abstractAnnotatableObject;
    }

	public void setAnnotation(Annotation annotation) {
    	this.annotation = annotation;
    }

	public Annotation getAnnotation() {
    	return annotation;
    }

	public abstract AbstractAnnotationInstance deepCopy();
	public abstract AbstractAnnotationInstance makeNewCopy(User creator);
}
