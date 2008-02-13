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

	@Override
	public double eval(Calculator calc, Double[] args) {
		assert args.length == 1;
		calc.addVariable( "cell", calc.valueOf(wellName) );
		double result = calc.valueOf(expression);
		calc.removeVariable( "cell" );
		return result;
	}
}
