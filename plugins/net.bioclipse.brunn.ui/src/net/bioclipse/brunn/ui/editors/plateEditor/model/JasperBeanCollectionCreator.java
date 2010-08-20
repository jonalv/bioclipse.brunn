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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author jonalv
 *
 */
public class JasperBeanCollectionCreator {

    public static Collection<JasperRootBean> createSmallBeanCollection() {
       final JasperRootBean bean = new JasperRootBean();
        
        List<JasperFunction> functions = new ArrayList<JasperFunction>();
        for ( int i = 0 ; i < 12 ; i++ ) {
            JasperFunction function = new JasperFunction();
            function.setName( "Example" + i );
            function.setValue( Math.random() + "" );
            functions.add( function );
        }
        bean.setFunctions( functions );
        
        List<JasperDiagram> diagrams = new ArrayList<JasperDiagram>();
        for ( int i = 0 ; i < 10 ; i++ ) {
            JasperDiagram diagram = new JasperDiagram();
            diagram.setName( "Test" + i );
            
            int numOfValues = i % 2 == 0 ? 10 
                                         : 13;
            
            List<JasperPoint> points    = new ArrayList<JasperPoint>();
            for ( int j = 0 ; j < numOfValues ; j++ ) {
                JasperPoint point = new JasperPoint();
                point.setValue( Math.random() * 100 );
                point.setConcentration( Math.pow(10, j) );
                points.add( point );
            }
            diagram.setPoints(points);
            diagram.setUnit( "\u03bcg/ml" );
            diagram.setIc50( Math.random() + "" );
            diagrams.add( diagram );
            
        }
        bean.setDiagrams( diagrams );
        
        return new ArrayList<JasperRootBean>() {
            private static final long serialVersionUID = 1L;
            { add( bean ); }
        };
    } 
    
    public static Collection<JasperRootBean> createBeanCollection() {
        final JasperRootBean bean = new JasperRootBean();
        
        List<JasperFunction> functions = new ArrayList<JasperFunction>();
        for ( int i = 0 ; i < 20 ; i++ ) {
            JasperFunction function = new JasperFunction();
            function.setName( "Example" + i );
            function.setValue( Math.random() + "" );
            functions.add( function );
        }
        bean.setFunctions( functions );
        
        List<JasperDiagram> diagrams = new ArrayList<JasperDiagram>();
        for ( int i = 0 ; i < 24 ; i++ ) {
            JasperDiagram diagram = new JasperDiagram();
            diagram.setName( "Test" + i );
            
            int numOfValues = i % 2 == 0 ? 3 
                                         : 5;
            
            List<JasperPoint> points    = new ArrayList<JasperPoint>();
            for ( int j = 0 ; j < numOfValues ; j++ ) {
                JasperPoint point = new JasperPoint();
                point.setValue( Math.random() * 100 );
                point.setConcentration( Math.pow(10, j) );
                points.add( point );
            }
            diagram.setPoints(points);
            diagram.setUnit( "\u03bcg/ml" );
            diagram.setIc50( Math.random() + "" );
            diagrams.add( diagram );
            
        }
        bean.setDiagrams( diagrams );
        
        return new ArrayList<JasperRootBean>() {
            private static final long serialVersionUID = 1L;
            { add( bean ); }
        };
    }
}
