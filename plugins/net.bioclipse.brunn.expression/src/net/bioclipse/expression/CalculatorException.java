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
public class CalculatorException extends RuntimeException {

	private static final long serialVersionUID = -3694847567019249428L;

	public CalculatorException( String message ) {
        super( message );
    }

    public CalculatorException( String message, Throwable cause ) {
        super( message, cause );
    }

    public CalculatorException( Throwable cause ) {
        super( cause );
    }
}
