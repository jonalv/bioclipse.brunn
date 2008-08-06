/*******************************************************************************
 * Copyright (c) 2007 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.bioclipse.expression.tests;


import java.util.HashMap;
import java.util.Map;

import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.FunctionBody;
import net.bioclipse.expression.FunctionBodyAdaptor;
import net.bioclipse.expression.FunctionBody.ParamType;

import org.junit.Before;

/**
 * @author jonalv, carl_masak
 *
 */
public class CalculatorBaseTest {

    public static final Map<String, FunctionBody> FUNCTIONS
        = new HashMap<String, FunctionBody>();
    public static final Map<String, Double>       VARIABLES
        = new HashMap<String, Double>();

    protected Calculator calc;
    protected Calculator calcWithFunctions;
    protected Calculator calcWithVariables;
	
	static {
		FUNCTIONS.put( "test", 
			           new FunctionBodyAdaptor( ParamType.ZERO ) {
				           public double eval(Calculator calc, Double[] args) {
				               return calc.safeValueOf( "A1 + A2" );
			               }
		               } );

        FUNCTIONS.put( "log", 
                       new FunctionBodyAdaptor( ParamType.ONE ) {
            public double eval(Calculator calc, Double[] args) {
                return Math.log( args[0] );
            }
        } );
        FUNCTIONS.put( "avg", 
                       new FunctionBodyAdaptor( ParamType.WHATEVER ) {
            public double eval(Calculator calc, Double[] args) {
                double sum = 0;
                for( Double arg : args ) {
                    sum += arg;
                }
                return sum / args.length;  // Yes, this will NaN on zero.
            }
        } );
        FUNCTIONS.put( "sin", 
                       new FunctionBodyAdaptor( ParamType.ONE ) {
            public double eval(Calculator calc, Double[] args) {
                return Math.sin( args[0] );
            }
        });
        VARIABLES.put("A1", 45000d);
        VARIABLES.put("A2",   340d);
        VARIABLES.put("A3",     1d);
        VARIABLES.put("B1",     0d);
        VARIABLES.put("B2",     1d);
		    VARIABLES.put("C1",     2d);
    }
    
    @Before
    public void createCalculators() throws Exception {
        calc              = new Calculator();
        calcWithVariables = new Calculator(VARIABLES);
        calcWithFunctions = new Calculator(VARIABLES, FUNCTIONS);
    }
}
