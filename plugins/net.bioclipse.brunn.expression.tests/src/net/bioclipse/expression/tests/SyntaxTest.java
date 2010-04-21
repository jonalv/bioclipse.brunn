/* *****************************************************************************
 * Copyright (c) 2007 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.bioclipse.expression.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author jonalv, carl_masak
 *
 */
public class SyntaxTest extends CalculatorBaseTest {

    @Test
    public void noSemicolonAtEnd() {

        assertTrue( "no semicolon at the end",
                    calc.isSyntactical( "1" ) );
    }

    @Test
    public void addition() {
        assertTrue( "addition is infix and takes two operands",
                    calc.isSyntactical( "1 + 1" ) );
        assertTrue( "croaks if only one operand is given",
                    !calc.isSyntactical( "1 +" ) );
        assertTrue( "croaks if only one operand is given;",
                    !calc.isSyntactical( "+ 1" ) );
    }

    @Test
    public void multiplicationDivision() {
        assertTrue( "multiplication is infix and takes two operands",
                    calc.isSyntactical( "1 * 1" ) );
        assertTrue( "division is infix and takes two operands",
                    calc.isSyntactical( "1 / 1" ) );
    }

    @Test
    public void minus() {
        assertTrue( "subtraction can be infix and take two operands",
        		    calc.isSyntactical( "1 - 1" ) );
        assertTrue( "prefix negation is also assertTrue",
        		    calc.isSyntactical( "- 1" ) );
        assertTrue( "postix negation not assertTrue",
        		    !calc.isSyntactical( "1 -" ) );
    }

    @Test
    public void parentheses() {
        assertTrue( "parentheses work", calc.isSyntactical( "(1)" ) );
        assertTrue( "operators inside parentheses work", 
        		    calc.isSyntactical( "-(1+1)" ) );
        assertTrue( "mismatched parentheses I",    !calc.isSyntactical( "(1" ) );
        assertTrue( "mismatched parentheses II",   !calc.isSyntactical( "1)" ) );
        assertTrue( "mismatched parentheses III",  !calc.isSyntactical( "(1)+1)" ) );
    }
}
