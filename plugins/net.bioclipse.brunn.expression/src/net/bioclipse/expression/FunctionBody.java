/* *****************************************************************************
 * Copyright (c) 2007 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.bioclipse.expression;

/**
 * @author jonalv, carl_masak
 *
 */
public interface FunctionBody {

    /**
     * The actual function to be evaluated.
     * 
     * @param calc A clone of the Calculator in which the expression
     *        is evaluated. This must be a clone, because calling some methods
     *        (for example <code>getNextSymbol()</code> on
     *        the original Calculator might jeopardize the correctness of the
     *        parsing. Note that the fact that this is a clone means that
     *        although variables and functions can be evaluated, any changes
     *        made during this method execution will not be propagated back to
     *        the calling calculator.
     * @param args An array of values sent in as parameters to the function.
     * @return The result of the function.
     */
	public double eval(Calculator calc, Double[] args);
	
	/**
	 * The parameter type of the function.
	 * 
	 * @return The parameter type.
	 */
	public ParamType getParamType();
	
	/**
	 * The three parameter types correspond to exactly zero, exactly one, and
	 * any number of parameters, respectively. 
	 * 
	 * @author jonalv, masak
	 *
	 */
	public static enum ParamType {
		ZERO,
		ONE,
		WHATEVER;
	}
}
