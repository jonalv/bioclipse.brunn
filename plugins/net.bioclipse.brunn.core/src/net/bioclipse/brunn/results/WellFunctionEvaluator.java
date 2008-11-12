package net.bioclipse.brunn.results;

class WellFunctionEvaluator {

	boolean outlier;
	String   expression;
	int      col;
	char     row;
	String   name;
	
	WellFunctionEvaluator( boolean outlier, 
	                       String expression, 
                           int col, 
                           char row, 
                           String name ) {
	    super();
	    this.outlier = outlier;
	    this.expression = expression;
	    this.col = col;
	    this.row = row;
	    this.name = name;
    }
}
