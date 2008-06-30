package net.bioclipse.brunn.results;

public class PlateFunctionEvaluator {

	String   expression;
	double  goodFrom;
	double  goodTo;
	boolean hasSpecifiedValue;
	String   name;
	
	public PlateFunctionEvaluator( String expression, 
	                                double goodFrom, 
	                                double goodTo, 
	                                boolean hasSpecifiedValue, 
	                                String name ) {
	    super();
	    this.expression = expression;
	    this.goodFrom = goodFrom;
	    this.goodTo = goodTo;
	    this.hasSpecifiedValue = hasSpecifiedValue;
	    this.name = name;
    }
}
