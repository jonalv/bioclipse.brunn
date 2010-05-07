package net.bioclipse.brunn.results;

class PlateFunctionEvaluator {
	String  expression;
	double  goodFrom;
	double  goodTo;
	boolean hasSpecifiedToValue;
	boolean hasSpecifiedFromValue;
	String  name;
	
	PlateFunctionEvaluator( String expression, 
                            double goodFrom, 
                            double goodTo, 
                            boolean hasSpecifiedFromValue, 
                            boolean hasSpecifiedToValue, 
                            String name ) {
	    super();
	    this.expression = expression;
	    this.goodFrom = goodFrom;
	    this.goodTo = goodTo;
	    this.hasSpecifiedFromValue = hasSpecifiedFromValue;
	    this.hasSpecifiedToValue = hasSpecifiedToValue;
	    this.name = name;
    }
}
