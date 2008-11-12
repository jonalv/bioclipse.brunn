package net.bioclipse.brunn.results;

class PlateFunctionEvaluator {
	String  expression;
	double  goodFrom;
	double  goodTo;
	boolean hasSpecifiedValue;
	String  name;
	
	PlateFunctionEvaluator( String expression, 
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
