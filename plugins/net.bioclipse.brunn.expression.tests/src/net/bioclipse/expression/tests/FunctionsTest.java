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

import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.CalculatorException;
import net.bioclipse.expression.FunctionBody;
import net.bioclipse.expression.FunctionBodyAdaptor;
import net.bioclipse.expression.FunctionBody.ParamType;

import org.junit.Test;

/**
 * @author jonalv,  carl_masak
 */
public class FunctionsTest extends CalculatorBaseTest {
	
    /**
     * 
     */
    private static final double EPSILON = 1E-5;

    @Test
    public void zeroParamFunctions() {
        // Some functions have zero params
        assertTrue(  calcWithFunctions.isSyntactical("test()")     );
        assertFalse( calcWithFunctions.isSyntactical("test(4)")    );
        assertFalse( calcWithFunctions.isSyntactical("test(4, 4)") );
    }
	
    @Test
    public void singleParamFunctions() {
        // Some functions are "inherently unary" and only take one parameter
        assertFalse( calcWithFunctions.isSyntactical("sin()"    ) ); 
        assertTrue(  calcWithFunctions.isSyntactical("sin(4)"   ) );
        assertFalse( calcWithFunctions.isSyntactical("sin(4, 4)") );
        assertEquals( "sin(0)==0", 0, calcWithFunctions.valueOf("sin(0)") );
    }

    @Test
    public void multiParamFunctions() {
        // Other functions can take as many parameters as you like
        assertTrue( calcWithFunctions.isSyntactical("avg()"     ) );
        assertTrue( calcWithFunctions.isSyntactical("avg(4)"    ) );
        assertTrue( calcWithFunctions.isSyntactical("avg(3,4,5)") );
        assertEquals( "avg(3,4,5)==4",
                      4, calcWithFunctions.valueOf("avg(3,4,5)") );
    }
    
    @Test
    public void workWithFunctions() {

        assertFalse(  "Can not use undefined functions",
                      calc.isSyntactical("test()") );
        assertEquals( "Can work with functions",
                      45340, calcWithFunctions.valueOf("test()") );
    }
    
    @Test
    public void addNewFunction() {
    	
        assertFalse( "Can not use undefined function",  
                     calcWithFunctions.isSyntactical("aFunction(A1, A2)") );
        calcWithFunctions.addFunction( "aFunction", 
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval( Calculator calc,
                                    Double[] args) {
                    return 0;
                }
        });
        assertTrue( "Use of newly defined function is syntactical",
                    calcWithFunctions.isSyntactical("aFunction(A1, A2)") );
        assertEquals( "Can use newly defined function", 0, 
                      calcWithFunctions.valueOf("aFunction(A1, A2)") );
    }
    
    @Test
    public void colonNotationIsSyntactical() {
    	
        calcWithFunctions.addFunction( "colonTest",
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval(Calculator calc, Double[] args) {
                    return 0;
                }
        });
    	
        assertTrue( "horisontal colon notation is syntactical", 
                    calcWithFunctions.isSyntactical("colonTest(A1:A3)") );
    	
        assertTrue( "vertical colon notation is syntactical", 
                    calcWithFunctions.isSyntactical("colonTest(A1:C1)") );
    	
        assertFalse( "colon notation horizontally spanning undefined " 
                     + "variables is not syntactical", 
                     calcWithFunctions.isSyntactical("colonTest(A1:A5)") );
    	
        assertFalse( "colon notation vertically spanning undefined variables " 
                     + "is not syntactical", 
                     calcWithFunctions.isSyntactical("colonTest(A1:F1)") );
    	
        assertTrue( "box colon notation is syntactical", 
                    calcWithFunctions.isSyntactical("colonTest(A1:B2)") );
    	
        assertFalse( "box colon notation including undefined variables " +
        		     "is not syntactical",
                     calcWithFunctions.isSyntactical("colonTest(A1:C3)") );
    }
    
    @Test
    public void testHorizontalColonNotation() {
        calcWithFunctions.addFunction( "colonTest",
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval(Calculator calc, Double[] args) {
                    assertEquals( "The number of given arguments should be 3",  
                                  3,
                                  args.length );
                    List<Double> argsList = Arrays.asList(args);

                    for(String s : new String[] {"A1", "A2", "A3"}) {
                        assertTrue( "args should contain: " + s,
                                    argsList.contains(calc.valueOf( s ) ) );
                    }
                    return 0;
                }
        });
        calcWithFunctions.valueOf("colonTest(A1:A3)");
    }
    
    @Test
    public void testVerticalColonNotation() {
        calcWithFunctions.addFunction( "colonTest",
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval(Calculator calc, Double[] args) {
                    assertEquals( "The number of given arguments should be 3",  
                                  3,
                                  args.length );
                    List<Double> argsList = Arrays.asList(args);

                    for(String s : new String[] {"A1", "B1", "C1"}) {
                        assertTrue( "args should contain: " + s,
                                    argsList.contains( calc.valueOf(s) ) );
                    }
                    return 0;
                }
        });
        calcWithFunctions.valueOf("colonTest(A1:C1)");
    }
    
    @Test
    public void testBoxColonNotation() {
    	calcWithFunctions.addFunction( "colonTest",
    	    new FunctionBodyAdaptor(ParamType.WHATEVER) {
    	        public double eval(Calculator calc, Double[] args) {
    	            assertEquals( "The number of given arguments should be 4",  
    	                          4,
						      args.length );
    	            List<Double> argsList = Arrays.asList(args);

    	            for(String s : new String[] {"A1", "A2", "B1", "B2"}) {
    	                assertTrue( "args should contain: " + s,
    	                            argsList.contains( calc.valueOf( s ) ) );
    	            }
    	            return 0;
    	        }
    	});
    	calcWithFunctions.valueOf("colonTest(A1:B2)");
    }
    
    @Test
    public void pointNotationIsSyntactical() {
    	
        calcWithFunctions.addFunction( "pointOneParamTest", 
                                       new FunctionBodyAdaptor(ParamType.ONE) {
            public double eval(Calculator calc, Double[] args) {
                return 0;
            }
        });
        
        calcWithFunctions.addFunction( "pointMultipleParamTest", 
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval(Calculator calc, Double[] args) {
                    return 0;
                }
            }
        );
    	
        assertTrue( "point notation is syntactical", 
                    calcWithFunctions.isSyntactical(
                        "A1.pointOneParamTest()") );
        
        assertFalse( "point notation on undefined variable is not syntactical", 
                     calcWithFunctions.isSyntactical(
                         "Undefined.pointOneParamTest()") );
        
        assertTrue( "point notation with multiple params is syntactical", 
                    calcWithFunctions.isSyntactical(
                        "A1.pointMultipleParamTest(A2, A3)") );
    }
    
    @Test
    public void testOneParamPointNotation() {
        calcWithFunctions.addFunction( "pointOneParamTest", 
                                       new FunctionBodyAdaptor(ParamType.ONE) {
            public double eval(Calculator calc, Double[] args) {
                assertEquals( "args should contain one value",
                              1, args.length );
                assertEquals( "args[0] should be \"A1\"",
                              (double)calc.valueOf("A1"), 
                              (double)args[0], 
                              EPSILON );
                return 0;
            }
        });
    	
        calcWithFunctions.valueOf("A1.pointOneParamTest()");
    }
    
    @Test
    public void testMultiParamPointNotation() {
        calcWithFunctions.addFunction( "pointMultiParamTest", 
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval(Calculator calc, Double[] args) {
                    assertEquals( "args should contain four values",
                                  4, args.length );
    				
                    String[] wantedVariables
                        = new String[] {"B1", "A1", "A2", "A3"};
    				
                    for ( int i = 0; i < wantedVariables.length; i++ ) {
                        assertEquals("arguments should be: "
                                     + wantedVariables[i],
                                     (double)calc.valueOf( wantedVariables[i] ),
                                     (double)args[i],
                                     EPSILON );
                    }
    				
                    return 0;
                }
            }
        );
    	
        calcWithFunctions.valueOf("B1.pointMultiParamTest(A1, A2, A3)");
        calcWithFunctions.valueOf("B1.pointMultiParamTest(A1:A3)");
    }
    
    @Test
    public void testSuppressedVariables() {
        List<String> suppressedVariables
            = Arrays.asList( new String[] {"A2", "A3"} );
        calcWithFunctions.addAllSuppressedVariables(suppressedVariables);
    	
        calcWithFunctions.addFunction( "whatever",
            new FunctionBodyAdaptor(ParamType.WHATEVER) {
                public double eval(Calculator calc, Double[] args) {
                    assertEquals(1, args.length);
                    return 0;
                }
            }
        );
    	
        calcWithFunctions.addFunction( "one",
                                       new FunctionBodyAdaptor(ParamType.ONE) {
            public double eval(Calculator calc, Double[] args) {
                fail("should not call this eval");
                return 0;
            }
        });
    	
        calcWithFunctions.valueOf( "whatever(A1:A3)" );
        try {
            calcWithFunctions.valueOf( "one(A2)" );
            fail("should throw exception since A2 is supressed");
        }
        catch (CalculatorException e) {
            // this is what we want
        }
    }
    
    @Test
    public void testCalculatingWithFunctions() {
        calcWithFunctions.addFunction(  "oneParam", 
                                        new FunctionBodyAdaptor(ParamType.ONE) {

            public double eval(Calculator calc, Double[] args) {
                return 1+args[0];
            }
        });
    	
        assertEquals( 0, calcWithFunctions.valueOf("A3-oneParam(0)"), EPSILON );
        assertEquals( 1, calcWithFunctions.valueOf("A3/oneParam(0)"), EPSILON );
        assertEquals( 1, calcWithFunctions.valueOf("A3*oneParam(0)"), EPSILON );
        assertEquals( 2, calcWithFunctions.valueOf("A3+oneParam(0)"), EPSILON );
    }
    
    @Test
    public void forbiddenFunctionNamesCharacters() {
        FunctionBody functionBody = new FunctionBodyAdaptor(ParamType.ZERO) {
            public double eval(Calculator calc, Double[] args) {
                return 0;
            }
        };

        for (char c : new char[] {'-', '/', '#'}) {
            try {
                calcWithFunctions.addFunction( "my" + c + "function",
                                               functionBody);
                fail("\'" + c + "\' not allowed in functionNames");
            }
            catch (IllegalArgumentException e) {
                // this is what we want
            }
        }
    }
    
    @Test
    public void allowedFunctionNamesCharacters() {
        FunctionBody functionBody = new FunctionBodyAdaptor(ParamType.ZERO) {
            public double eval(Calculator calc, Double[] args) {
                return 0;
            }
        };

        for (char c : new char[] {'%'}) {
            try {
                calcWithFunctions.addFunction( "my" + c + "function", functionBody);
            }
            catch (IllegalArgumentException e) {
                fail("\'" + c + "\' should be allowed in functionNames");
            }
        }
    }

    @Test
    public void functionCallSyntaxTest() {
    	
        FunctionBody f1 = new FunctionBodyAdaptor(ParamType.ZERO) {
            public double eval(Calculator calc, Double[] args) {
                return calc.valueOf("1");
            }
        };
    	
        calcWithFunctions.addFunction("f1", f1);
    	
        String expression = "(f1())";
        assertTrue( calcWithFunctions.isSyntactical( expression ) );
        assertEquals( 1, calcWithFunctions.valueOf(expression), EPSILON );
    }
}
