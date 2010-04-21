/* *****************************************************************************
 * Copyright (c) 2007 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.bioclipse.expression.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import net.bioclipse.expression.CalculatorException;

import org.junit.Test;

/**
 * @author jonalv,  carl_masak
 */
public class VariablesTest extends CalculatorBaseTest {

    @Test
    public void variablesCaseInsensitivity() {
        
        assertTrue( "Variables are case insensitive",
                    calcWithVariables.isSyntactical("A1") );
        assertTrue( "Variables are case insensitive",
                    calcWithVariables.isSyntactical("a1") );
    }

    @Test
    public void workWithVariables() {
		
        assertFalse(  "Can not use undefined variables",
                      calc.isSyntactical("A2+A1") );
        assertEquals( "Uses the given variables values",
                      45340, calcWithVariables.valueOf("A2+A1") );
    }
    
    @Test
    public void addNewVariable() {
    	
    	calcWithVariables.addVariable("newVariable", 45.78);
    	assertEquals( "Should use a new variable value",
    	              45.68, calcWithVariables.valueOf("newVariable") );
    }
    
    @Test
    public void removeVariable() {
        calcWithVariables.addVariable("newVariable", 45.78);
        assertEquals( "Should use a new variable value",
                      45.68, calcWithVariables.valueOf("newVariable") );
        calcWithVariables.removeVariable("newVariable");
        assertFalse( calcWithVariables.isSyntactical("newVariable") );
    }
    
    @Test
    public void workWithSuppressedVariables() {
    	
        List<String> suppressedVariables
            = Arrays.asList( new String[] {"A2", "A3"} );
        calcWithVariables.addAllSuppressedVariables(suppressedVariables);
        try {
            calcWithVariables.valueOf("A1 + A2");
            fail("should throw Exception since A2 is suppressed");
        }
        catch (CalculatorException e) {
            // this is what we want
        }
        calcWithVariables.addSuppressedVariable("A1");
        try {
            calcWithVariables.valueOf("A1 * B1");
            fail("should throw Exception since A2 is suppressed");
        }
        catch (Exception e) {
            // this is what we want
        }
    }
}