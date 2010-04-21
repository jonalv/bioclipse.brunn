/* *****************************************************************************
 * Copyright (c) 2007 Bioclipse Project
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.bioclipse.expression;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bioclipse.expression.FunctionBody;
import net.bioclipse.expression.FunctionBody.ParamType;

/**
 * @author jonalv, carl_masak
 *
 */
public class Calculator implements Cloneable {
    public static enum Symbol {
        CONSTANT            ( StreamTokenizer.TT_NUMBER ),
        IDENTIFIER          ( StreamTokenizer.TT_WORD ),
        EOF                 ( StreamTokenizer.TT_EOF ),
        PLUS                ( '+' ),
        MINUS               ( '-' ),
        STAR                ( '*' ),
        SLASH               ( '/' ),
        OPENING_PARENTHESIS ( '(' ),
        CLOSING_PARENTHESIS ( ')' ),
        COMMA               ( ',' );

        int correspondingToken;

        Symbol( int correspondingToken ) {
            this.correspondingToken = correspondingToken;
        }
    };

    private Symbol currentSymbol;

    private Map<String, Double> variables = new HashMap<String, Double>();
    private Set<String> suppressedVariables = new HashSet<String>();
    
    private Map<String, FunctionBody> functions
        = new HashMap<String, FunctionBody>();

    private StreamTokenizer st;

    public Calculator() {
    }

    public Calculator(Map<String, Double> variables) {
    	for (String variable : variables.keySet())
    	    addVariable(variable, variables.get(variable));
    }

    public Calculator( Map<String, Double> variables,
                       Map<String, FunctionBody> functions ) {
        this(variables);
        for (String function : functions.keySet())
            addFunction(function, functions.get(function));
    }

    public void addVariable(String name, Double value) {
        checkIdentifierName(name);
	    
        this.variables.put( name.toLowerCase(), value );
    }

    public void removeVariable(String name) {
        variables.remove( name.toLowerCase() );
    }

    public void addAllSuppressedVariables(List<String> variables) {
        for ( String variable : variables )
            addSuppressedVariable( variable );
    }

    public void addSuppressedVariable(String name) {
        checkIdentifierName(name);
        
        suppressedVariables.add( name.toLowerCase() );
    }

    public void addFunction(String name, FunctionBody body) {
        checkIdentifierName(name);
        
        this.functions.put( name.toLowerCase(), body );
    }

    public void checkIdentifierName(String name) {
        if ( !name.matches( "^[\\w%]*$" ) )
            throw new IllegalArgumentException( "Identifier \"" + name
                    + "\" contains non-alphanumeric characters" );
    }
    
    private static String quote( int c ) {
        return "'" + (char)c + "'";
    }

    private void getNextSymbol() {

        int token;

        try {
            token = st.nextToken();
        }
        catch ( IOException e ) {
            currentSymbol = Symbol.EOF;
            return;
        }

        for ( Symbol symbol : Symbol.values() ) {
            if ( token == symbol.correspondingToken ) {
                currentSymbol = symbol;
                return;
            }
        }

        throw new CalculatorException( "Unrecognized input: "
                                       + quote( token ) );
    }

    private String currentName() {
        return st.sval;
    }

    private double currentNumber() {
        return st.nval;
    }

    private boolean accepts( Symbol s ) {
        if ( currentSymbol == s ) {
            getNextSymbol();
            return true;
        }

        return false;
    }

    private void expect( Symbol s ) {
        if ( ! accepts(s) )
            throw new CalculatorException( "Unexpected symbol: " + currentSymbol
                                           + ", expected " + s );
    }

    private Double[] parameterList() {
        List<Double> params = new ArrayList<Double>();
        
        expect( Symbol.OPENING_PARENTHESIS );
        
        boolean firstParam = true;
        while ( !accepts( Symbol.CLOSING_PARENTHESIS ) ) {

            if (!firstParam)
                expect(Symbol.COMMA);
            
            try {
                params.add( expr() );
            }
            catch (SuppressedVariableException e) {
                // This is ok, we can handle e.g. variables being suppressed
            }
            firstParam = false;
        }

        return params.toArray(new Double[0]);
    }
    
    private double factor() {

        if ( accepts( Symbol.OPENING_PARENTHESIS ) ) {
            double value = expr();
            expect( Symbol.CLOSING_PARENTHESIS );
            return value;
        }
        else if ( accepts( Symbol.MINUS ) ) {
            return -factor();
        }
        else if ( currentSymbol == Symbol.CONSTANT ) {
            double value = currentNumber();
            getNextSymbol();
            return value;
        }
        else if ( currentSymbol == Symbol.IDENTIFIER ) {
            String name = currentName().toLowerCase();

            getNextSymbol();
            if ( currentSymbol == Symbol.OPENING_PARENTHESIS ) {
                Double[] parameters = parameterList();

                if ( functions.containsKey( name ) ) {
                    FunctionBody fn = functions.get( name );
                    int numberOfParameters = parameters.length;
                    
                    if ( fn.getParamType() == ParamType.ZERO
                            && numberOfParameters > 0 )
                        throw new CalculatorException(
                            name + " must take no parameters" );
                    
                    if ( fn.getParamType() == ParamType.ONE
                            && numberOfParameters != 1 )
                        throw new CalculatorException(
                            "There must be one parameter in " + name );
                    
                    try {
                        return fn.eval( (Calculator)this.clone(), parameters );
                    }
                    catch (Exception e) {
                        throw new CalculatorException( "Function " + name
                                + " didn't complete successfully.", e );
                    }
                }
                else
                    throw new CalculatorException(
                        "No such function: " + name );
            
            }
            else {
                if ( !variables.containsKey(name) )
                    throw new CalculatorException(
                        "No such variable: " + name );

                if ( suppressedVariables.contains(name) )
                    throw new SuppressedVariableException(
                        name + " is suppressed" );
                
                return variables.get(name);
            }
        }
        else {
            throw new CalculatorException( "Unexpected symbol: "
                                           + currentSymbol
                                           + ", expected "
                                           + Symbol.OPENING_PARENTHESIS + ", "
                                           + Symbol.MINUS + ", "
                                           + Symbol.CONSTANT + " or "
                                           + Symbol.IDENTIFIER );
        }
    }

    private double term() {

        double product = factor();

        while ( currentSymbol == Symbol.STAR
                || currentSymbol == Symbol.SLASH ) {

            if ( accepts( Symbol.STAR ) )
                product = product * factor();
            else if ( accepts( Symbol.SLASH ) )
                product = product / factor();
        }

        return product;
    }

    private double expr() {

        double sum = term();

        while ( currentSymbol == Symbol.PLUS
                || currentSymbol == Symbol.MINUS ) {

            if ( accepts( Symbol.PLUS ) )
                sum = sum + term();
            else if ( accepts( Symbol.MINUS ) )
                sum = sum - term();
        }

        return sum;
    }

    private double statement() {
        double expression = expr();
        if ( currentSymbol != Symbol.EOF )
            throw new CalculatorException( "Unexpected symbol: " +
                                           currentSymbol + ", expected " +
                                           Symbol.EOF );
        return expression;
    }

    private void prepareTokenizer() {
        
        st.wordChars('_', '_');
        st.wordChars('%', '%');

        st.ordinaryChar('/');
        st.ordinaryChar('\'');
        st.ordinaryChar( '-' );

        getNextSymbol();
    }
    
    private static String commaSeparatedList( String[] list ) {
        StringBuilder sb = new StringBuilder();
        
        boolean first = true;
        for ( String s : list ) {

            if (!first)
                sb.append(", ");

            sb.append(s);
            
            first = false;
        }
        
        return sb.toString();
    }

    private static String[] span(String start, String end) {
        List<String> list = new ArrayList<String>();
        
        char startRow = Character.toUpperCase( start.charAt( 0 ) ),
             endRow   = Character.toUpperCase( end.charAt(   0 ) );
        
        int startCol = Integer.parseInt( start.substring( 1 ) ),
            endCol   = Integer.parseInt( end.substring(   1 ) );
        
        for ( char row = startRow; row <= endRow; row++ )
            for ( int col = startCol; col <= endCol; col++ )
                list.add( "" + row + col );
        
        return list.toArray(new String[0]);
    }
    
    private String preprocessDots(String s) {
        
        // Verbose regular expressions are missing from Java, so here goes...
        //
        // (\\w+)     # a variable name
        // \\.        # a literal dot
        // (\\w+)     # a function name
        // \\(\\s*\\) # and a pair of empty parentheses
        //            # ...to be replaced by...
        // $2         # the function name
        // ($1)       # the variable name, enclosed in parentheses
        
        // (\\w+)     # a variable name
        // \\.        # a literal dot
        // (\\w+)     # a function name
        // \\(        # and an opening parenthesis
        //            # ...to be replaced by...
        // $2(        # the function name, a parenthesis
        // $1,<SP>    # the variable name, a comma and a space
        
        return s.replaceAll( "(\\w+)\\.(\\w+)\\(\\s*\\)", "$2($1)" )
                .replaceAll( "(\\w+)\\.(\\w+)\\(", "$2($1, " );
    }
    
    private String preprocessColons(String s) {
        
        String clone = new String(s);
        Pattern colonNotation
            = Pattern.compile("([a-zA-Z])(\\d+)\\s*:\\s*([a-zA-Z])(\\d+)");
        
        Matcher m = colonNotation.matcher( clone );
        while ( m.find() ) {
            
            String start = m.group( 1 ) + m.group( 2 ),
                   end   = m.group( 3 ) + m.group( 4 );
            
            clone = m.replaceFirst(
                    commaSeparatedList( span( start, end ) ) );
            m = colonNotation.matcher( clone );
        }
        
        return clone;
    }
    
    private double parse(String input) {

        String preprocessedInput = preprocessColons(preprocessDots(input));
        st = new StreamTokenizer( new StringReader( preprocessedInput ) );

        prepareTokenizer();

        double lastValue = 0;
        while ( currentSymbol != Symbol.EOF ) {

            lastValue = statement();

            getNextSymbol();
        }
        return lastValue;
    }

    public boolean isSyntactical( String input ) {
        try {
            parse(input);
        }
        catch ( CalculatorException e ) {
            return false;
        }

        return true;
    }

    public double safeValueOf( String input ) {
    	try {
    		return valueOf(input);
    	}
    	catch ( SuppressedVariableException e ) {
    		return Double.NaN;
    	}
    }
    
	public double valueOf( String input ) {
		return parse(input);
	}
    
    public Object clone() throws CloneNotSupportedException {
        Calculator result = (Calculator) super.clone();
        
        result.functions = new HashMap<String, FunctionBody>(functions);
        result.variables = new HashMap<String, Double>(variables);
        result.suppressedVariables = new HashSet<String>(suppressedVariables);
        
        return result;
    }

	public void removeSuppressedVariable(String name) {
        checkIdentifierName(name);
        
        suppressedVariables.remove( name.toLowerCase() );
	}
}
