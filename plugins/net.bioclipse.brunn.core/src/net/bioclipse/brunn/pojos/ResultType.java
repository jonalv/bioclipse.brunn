package net.bioclipse.brunn.pojos;

/**
 * @author jonathan
 *
 */
public class ResultType extends AbstractAuditableObject {

	private int length;

	public ResultType() {
	    super();
    }

	public ResultType( User creator, String name, int length) {
	    super(creator, name);
	    this.length = length;
    }

	public int getLength() {
    	return length;
    }

	public void setLength(int length) {
    	this.length = length;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof ResultType) )
		    return false;
	    final ResultType other = (ResultType) obj;
	    if (length != other.getLength())
		    return false;
	    return true;
    }
}
