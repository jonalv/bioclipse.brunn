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
public class EnumAnnotation extends AbstractAnnotationInstance {

	private String value;
	
	public EnumAnnotation(){
		
	}
	
	public EnumAnnotation(User creator, String value, Annotation annotation) {
	    super(creator, annotation.getName(), annotation);
	    if(!annotation.getPossibleValues().contains(value)) {
	    	throw new IllegalArgumentException( value + " is not a possibleValue for the enumAnnotation " +
	    			annotation.getName() );
	    }
	    this.value = value;
	}
	
	/**
	 * @param value  the value to set
	 */
	public void setValue(String value){
		this.value=value;
	}
	
	/**
	 * @return  the value
	 */
	public String getValue(){
		return value;
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
	    if ( !(obj instanceof EnumAnnotation) )
		    return false;
	    final EnumAnnotation other = (EnumAnnotation) obj;
	    if (value == null) {
		    if (other.getValue() != null)
			    return false;
	    }
	    else if (!value.equals(other.getValue()))
		    return false;
	    return true;
    }

	@Override
    public EnumAnnotation deepCopy() {
		EnumAnnotation result = new EnumAnnotation();
		result.setName(name);
		result.setCreator(creator);
		result.setDeleted(deleted);
		result.setValue(value);
		
		result.setHashCode(hashCode);
		result.setId(id);
		
		return result;
    }
	
    public EnumAnnotation makeNewCopy(User creator) {
		EnumAnnotation result = new EnumAnnotation();
		result.setName(name);
		result.setCreator(creator);
		result.setDeleted(deleted);
		result.setValue(value);
		
		return result;
    }
}
