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
public class TextAnnotation extends AbstractAnnotationInstance {

	private String value;
	
	public TextAnnotation(){
		
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public TextAnnotation(User creator, String value, Annotation annotation) {
		super(creator, annotation.getName(), annotation);
		this.value = value;
	}

	/**
	 * @return  the value
	 */
    public String getValue() {
    	return value;
    }

	/**
	 * @param value  the value to set
	 */
    public void setValue(String value) {
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
	    if ( !(obj instanceof TextAnnotation) )
		    return false;
	    final TextAnnotation other = (TextAnnotation) obj;
	    if (value == null) {
		    if (other.getValue() != null)
			    return false;
	    }
	    else if (!value.equals(other.getValue()))
		    return false;
	    return true;
    }

    public TextAnnotation deepCopy() {
    	TextAnnotation result = new TextAnnotation();
    	result.setName(name);
    	result.setCreator(creator);
    	result.setValue(value);
    	result.setDeleted(deleted);
    	result.setHashCode(hashCode);
    	result.setId(id);
    	return result;
    }
    
    public TextAnnotation makeNewCopy(User creator) {
    	TextAnnotation result = new TextAnnotation();
    	result.setName(name);
    	result.setCreator(creator);
    	result.setValue(value);
    	result.setDeleted(deleted);
    	return result;
    }
}
