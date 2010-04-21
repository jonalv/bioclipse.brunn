/* *****************************************************************************
 * Copyright (c) 2007 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.bioclipse.expression.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author jonalv, carl_masak
 *
 */
public class EvaluatorTest extends CalculatorBaseTest {

    @Test
    public void arithmetic() {
        assertEquals( "addition works", calc.safeValueOf( "1 + 1" ), 2 );
        assertEquals( "subtraction works", calc.safeValueOf( "1 - 2" ), -1 );

        assertEquals( "multiplication works", calc.safeValueOf( "1 * 1" ), 1 );
        assertEquals( "division works", calc.safeValueOf( "1 / 1" ), 1 );

        assertEquals( "unary negation works", calc.safeValueOf( "- 1" ), -1 );
    }

    @Test
    public void parentheses() {
        assertEquals( "parentheses work", calc.safeValueOf( "(1)" ), 1 );
        assertEquals( "operators inside parentheses work", calc.safeValueOf( "-(1+1)" ), -2 );
    }
}
