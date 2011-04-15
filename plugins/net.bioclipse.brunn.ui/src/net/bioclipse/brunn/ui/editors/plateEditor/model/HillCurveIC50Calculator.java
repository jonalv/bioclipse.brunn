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

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.optimization.fitting.CurveFitter;
import org.apache.commons.math.optimization.fitting.ParametricRealFunction;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;
import org.apache.log4j.Logger;


/**
 * @author jonalv
 *
 */
public class HillCurveIC50Calculator {
    
    public double calculateIC50(double[] conc, double[] si) {
        
        LevenbergMarquardtOptimizer optimizer 
            = new LevenbergMarquardtOptimizer();
        
        CurveFitter fitter = new CurveFitter( optimizer );
        
        for ( int i = 0; i < si.length; i++ ) {
            fitter.addObservedPoint( conc[i], si[i] / 100 );
            System.out.println("Added point: " + conc[i] + "; " + si[i] / 100);
        }
        
        ParametricRealFunction function = new ParametricRealFunction() {
    
            @Override
            public double value( double c, double[] paramaters ) 
                          throws FunctionEvaluationException {
                
                double d       = paramaters[0];
                double n       = paramaters[1];
                double c_pow_n = Math.pow( c, n );
                
                return c_pow_n / (c_pow_n + Math.pow(d, n) );
            }
            
            @Override
            public double[] gradient( double c, double[] paramaters ) 
                            throws FunctionEvaluationException {
        
                double d       = paramaters[0];
                double n       = paramaters[1];
                double c_pow_n = Math.pow( c, n );
                double d_pow_n = Math.pow( d, n );
                
                double ddd = -n * c_pow_n * Math.pow( d, n-1 )
                             /
                             Math.pow( c_pow_n + d_pow_n, 2 );
                
                double ddn = (c_pow_n * d_pow_n * (Math.log(c) - Math.log(d))) 
                             /
                             Math.pow( c_pow_n + d_pow_n, 2);
                
                return new double[] {ddd, ddn};
            }
            
        };
        
        double[] params = null;
        try {
            params = fitter.fit(function, new double[] {1,1} );
        }
        catch (Exception e) {
            Logger.getLogger( HillCurveIC50Calculator.class ).debug( 
                "Caught Exception while fitting dose response curve", e );
            return Double.NaN;
        }
        
        double d = params[0]; 
        double n = params[1];
        
        System.out.println("d=" + d);
        System.out.println("n=" + n);
        
        return Math.pow( -(0.5 - 1) * Math.pow( d, -n ) 
                             /
                             0.5, 
                         -1/n );
    }
}
