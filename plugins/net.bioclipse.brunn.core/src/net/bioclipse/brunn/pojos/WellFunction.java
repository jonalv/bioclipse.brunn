/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.util.HashSet;

/**
 * @author jonathan
 *
 */
public class WellFunction extends AbstractBaseObject {

	private String expression;
	private AbstractWell well;
	
	public WellFunction() {
	    super();
    }
	
	public WellFunction(User creator, String name, String expression, AbstractWell well) {
	    super(creator, name);
	    this.expression = expression;
	    this.well = well;
	    well.getWellFunctions().add(this);
    }

	/**
	 * @return  the expression
	 */
	public String getExpression() {
    	return expression;
    }

	/**
	 * @param expression  the expression to set
	 */
	public void setExpression(String expression) {
    	this.expression = expression;
    }
	
	/**
	 * @return  the well
	 * 
	 */
	public AbstractWell getWell() {
    	return well;
    }
	
	/**
	 * @param well  the well to set
	 * 
	 */
	public void setWell(AbstractWell well) {
    	this.well = well;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof WellFunction) )
		    return false;
	    final WellFunction other = (WellFunction) obj;
	    if (expression == null) {
		    if (other.getExpression() != null)
			    return false;
	    }
	    else if (!expression.equals(other.getExpression()))
		    return false;
	    return true;
    }

	public WellFunction deepCopy() {
	    
		WellFunction function = new WellFunction();
		function.setName(name);
		function.setCreator(creator);
		function.setDeleted(deleted);
		function.setExpression(expression);
		
		function.setHashCode(hashCode);
		function.setId(id);

		return function;
    }

	public WellFunction makeNewCopy() {
		WellFunction function = new WellFunction();
		function.setName(name);
		function.setCreator(creator);
		function.setDeleted(deleted);
		function.setExpression(expression);
	    return function;
    }
}
