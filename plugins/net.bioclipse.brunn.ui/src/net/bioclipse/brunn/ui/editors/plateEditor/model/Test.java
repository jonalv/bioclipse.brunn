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
public class Test {

    /**
     * @param args
     */
    public static void main( String[] args ) {

        System.out.println(
            new HillCurveIC50Calculator().calculateIC50( 
               new double[] {  10, 100, 1000, 10000, 100000},
               new double[] {100, 75,  50,   25,    20} )  );
        System.out.println("\n");
        System.out.println(
            new HillCurveIC50Calculator().calculateIC50( 
               new double[] { 10000, 1000, 100, 10, 1},
               new double[] { 20, 25, 50, 75, 100}) );
    }
}
