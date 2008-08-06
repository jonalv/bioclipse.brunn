package net.bioclipse.brunn.results;

import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.FunctionBodyAdaptor;
import net.bioclipse.expression.FunctionBody.ParamType;

public class PlateFunctionBody extends FunctionBodyAdaptor {

	private String expression; 
	
	public PlateFunctionBody(String expression) {
	    super(ParamType.ZERO);
	    this.expression = expression; 
    }

	public double eval(Calculator calc, Double[] args) {
		assert args.length == 0;
		return calc.safeValueOf(expression);
	}
}
