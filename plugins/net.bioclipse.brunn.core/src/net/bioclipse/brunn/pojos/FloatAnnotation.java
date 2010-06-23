/**
 * 
 */
package net.bioclipse.brunn.pojos;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class FloatAnnotation extends AbstractAnnotationInstance {

	private double value;
	
	/**
	 * 
	 */
	public FloatAnnotation() {
	}

	/**
	 * @param id
	 * @param name
	 */
	public FloatAnnotation(User creator, double value, Annotation annotation) {
		super(creator, annotation.getName(), annotation);
		this.value = value;
		annotation.getAnnotationInstances().add(this);
	}

	/**
	 * @return  the value
	 */
    public double getValue() {
    	return value;
    }

	/**
	 * @param value  the value to set
	 */
    public void setValue(double value) {
    	this.value = value;
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
	    if ( !(obj instanceof FloatAnnotation) )
		    return false;
	    final FloatAnnotation other = (FloatAnnotation) obj;
	    if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.getValue()))
		    return false;
	    return true;
    }

	@Override
    public FloatAnnotation deepCopy() {
	    
		FloatAnnotation floatAnnotation = new FloatAnnotation();
	    floatAnnotation.setName(name);
	    floatAnnotation.setCreator(creator);
	    floatAnnotation.setDeleted(deleted);
	    floatAnnotation.setValue(value);
	    
	    floatAnnotation.setHashCode(hashCode);
	    floatAnnotation.setId(id);
	    
	    return floatAnnotation;
    }
	
	public FloatAnnotation makeNewCopy(User creator) {

		FloatAnnotation floatAnnotation = new FloatAnnotation();
	    floatAnnotation.setName(name);
	    floatAnnotation.setCreator(creator);
	    floatAnnotation.setDeleted(deleted);
	    floatAnnotation.setValue(value);
	    
	    return floatAnnotation;
    }
}
