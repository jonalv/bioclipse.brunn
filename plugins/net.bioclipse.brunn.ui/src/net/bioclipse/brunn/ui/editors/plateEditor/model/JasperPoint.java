/* 
 * Copyright (c) 2010  Jonathan Alvarsson <jonalv@users.sourceforge.net>
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package net.bioclipse.brunn.ui.editors.plateEditor.model;


/**
 * @author jonalv
 *
 */
public class JasperPoint {

    private double concentration;
    private double value;
    
    public void setConcentration( double concentration ) {
        this.concentration = concentration;
    }
    
    public double getConcentration() {
        return concentration;
    }
    
    public void setValue( double value ) {
        this.value = value;
    }
    
    public double getValue() {
        return value;
    }
}
