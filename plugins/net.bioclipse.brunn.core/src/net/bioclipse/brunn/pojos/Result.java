package net.bioclipse.brunn.pojos;

import java.util.Arrays;

/**
 * @author jonathan
 *
 */
public class Result extends AbstractBaseObject {

	private double[] resultValue;
	private int version;
	
	public Result() {
	    super();
    }

	public Result(User creator, String name, double[] resultValue, int version) {
	    super(creator, name);
	    this.resultValue = resultValue;
	    this.version = version;
    }

	public double[] getResultValue() {
    	return resultValue;
    }

	public void setResultValue(double[] resultValue) {
    	this.resultValue = resultValue;
    }

	public int getVersion() {
    	return version;
    }

	public void setVersion(int version) {
    	this.version = version;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof Result) )
		    return false;
	    final Result other = (Result) obj;
	    if (!Arrays.equals(resultValue, other.getResultValue()))
		    return false;
	    if (version != other.getVersion())
		    return false;
	    return true;
    }

	public Result deepCopy() {
	    
		Result result = new Result();
		result.creator = creator;
		result.name = name;
		result.resultValue = resultValue.clone();
		result.version = version;
		result.hashCode = hashCode;
		result.id = id;
		
	    return result;
    }
	
	public Result makeNewCopy() {
	    
		Result result = new Result();
		result.creator = creator;
		result.name = name;
		result.resultValue = resultValue.clone();
		result.version = version;
	    return result;
    }
}
