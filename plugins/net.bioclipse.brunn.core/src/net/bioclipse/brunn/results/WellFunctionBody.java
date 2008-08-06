package net.bioclipse.brunn.results;

import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.FunctionBodyAdaptor;

public class WellFunctionBody extends FunctionBodyAdaptor {

	private String expression;
	private String wellName;
	
	public WellFunctionBody(String expression, String wellName) {
	    super(ParamType.ZERO);
	    this.expression = expression;
	    this.wellName   = wellName;
    }

	public double eval(Calculator calc, Double[] args) {
		assert args.length == 1;
		calc.addVariable( "cell", calc.safeValueOf(wellName) );
		double result = calc.safeValueOf(expression);
		calc.removeVariable( "cell" );
		return result;
	}
}
