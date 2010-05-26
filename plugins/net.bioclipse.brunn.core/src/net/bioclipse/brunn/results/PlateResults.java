package net.bioclipse.brunn.results;

import java.util.HashMap;

import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.pojos.WellFunction;
import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.FunctionBody;
import net.bioclipse.expression.FunctionBodyAdaptor;
import net.bioclipse.expression.FunctionBody.ParamType;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class PlateResults implements IEditorInput {

	private Calculator calculator;
	
	//key = function.name + well.row + well.col 
	private HashMap<String, WellFunctionEvaluator>  wellFunctionEvaluators;
	
	//key = function.name
	private HashMap<String, PlateFunctionEvaluator> plateFunctionEvaluators;
	
	//key = "" + well.row + well.col
	private HashMap<String, Double> rawValues;
	
	private Plate plate;
	
	public PlateResults( Plate plate, IProgressMonitor monitor ) {
		
		wellFunctionEvaluators  = new HashMap<String, WellFunctionEvaluator>();
		plateFunctionEvaluators = new HashMap<String, PlateFunctionEvaluator>();
		rawValues               = new HashMap<String, Double>();
		calculator              = new Calculator();
		
		createFunctionBodies(calculator);
		
		this.plate = plate;
		int createEvaluatorTicks = (int) (plate.getWells().size() * 0.1);
		if(monitor != null) {
			monitor.beginTask("Loading plate", plate.getWells().size() + createEvaluatorTicks);
		}
		try {
			// create and place the PlateFunctionEvaluator in the hashmap
			for(PlateFunction function : plate.getPlateFunctions()) {
				
				PlateFunctionEvaluator plateFunctionEvaluator =
					new PlateFunctionEvaluator( function.getExpression(),
							                    function.getGoodFrom(), 
							                    function.getGoodTo(), 
							                    function.getHasSpecifiedFromValue(),
							                    function.getHasSpecifiedToValue(),
							                    function.getName() );
				
				PlateFunctionBody pfb = new PlateFunctionBody( function.getExpression() ); 
				calculator.addFunction( function.getName(), pfb );
				plateFunctionEvaluators.put(plateFunctionEvaluator.name, plateFunctionEvaluator);
			}
			if(monitor != null) {
				monitor.worked(createEvaluatorTicks);
			}
			for( Well well : plate.getWells() ) {
				
				//Find the rawValue measurement for the well with the latest version number
				double rawValue = Double.NaN;
				int version = Integer.MIN_VALUE;
				
				if ( well.getSampleContainer().
					      getWorkList().
					      getAbstractOperations().toArray().length > 0 ) {
	
					for( Result result : ((Measurement)well.getSampleContainer().
							                                getWorkList().
							                                getAbstractOperations().toArray()[0]).
							                                getResults() ) {
						
						if( result.getVersion() > version ) {
							version  = result.getVersion();
							rawValue = result.getResultValue()[0];
						}
					}
				}
				else {
					version  = 0;
					rawValue = -1;
				}
				
				// create and place the wellfunctionevaluator in the hashmap
				for(WellFunction function : well.getWellFunctions()) {
	
					WellFunctionEvaluator wellFunctionEvaluator = 
						new WellFunctionEvaluator( well.isOutlier(), 
								                   function.getExpression(),
								                   well.getCol(), 
								                   well.getRow(),
								                   function.getName() );
					
					wellFunctionEvaluators.put( function.getName() + well.getRow() + well.getCol(),
							                    wellFunctionEvaluator);
					WellFunctionBody wfb = new WellFunctionBody( function.getExpression(), 
							                                      well.getName() );
					calculator.addFunction( well.getName() + "_" + function.getName(), wfb );
				}

				String variable = "" + well.getRow() + well.getCol(); 
				//place the raw Value in the hashmap
				rawValues.put(variable, rawValue);
				
				//place the raw Value in the parser
				calculator.addVariable(variable, rawValue );
				if( well.isOutlier() ) {
					calculator.addSuppressedVariable(variable);
				}
				if(monitor != null) {
					monitor.worked(1);
				}
			}
		}
		finally{
			if(monitor != null) {
				monitor.done();
			}
		}
	}
	
	public static void createFunctionBodies(Calculator calculator) {

		/*
		 * SUM FUNCTION
		 */
	    FunctionBody sum = new FunctionBodyAdaptor( ParamType.WHATEVER ) {
            public double eval(Calculator calc, Double[] args) {
				double result = 0;
				for(double d : args) {
					result += d;
				}
	            return result;
            }
	    };
	    calculator.addFunction("sum", sum);
	    
	    /*
	     * AVG FUNCTION
	     */
	    FunctionBody avg = new FunctionBodyAdaptor( ParamType.WHATEVER ) {
            public double eval(Calculator calc, Double[] args) {
				double sum = 0;
				for(double d : args) {
					sum += d;
				}
				
	            return sum/args.length;
            }
	    };
	    calculator.addFunction("avg", avg);
	    
	    /*
	     * STDDEV FUNCTION
	     */
	    FunctionBody stddev = new FunctionBodyAdaptor( ParamType.WHATEVER ) {
            public double eval(Calculator calc, Double[] args) {
				double sum = 0;
				for(double d : args) {
					sum += d;
				}
	            double avg =  sum/args.length;
	            
	            double sumOfDiffs = 0;
	            for(double d : args) {
	            	sumOfDiffs += (d-avg)*(d-avg);
	            }
	            return Math.sqrt( 1.0/(args.length-1) * sumOfDiffs );
            }
	    };
	    calculator.addFunction("stddev", stddev);
    }

	public double getValue(int col, char row, String wellFunctionName){
		
		if( wellFunctionEvaluators.get(wellFunctionName + row + col) == null ) {
			return Double.NaN;
		}
		return calculator.safeValueOf( (row + "") + col + "_" + wellFunctionName + "()" );
	}
	
	public double getValue(int col, int row, String wellFunctionName) {
		
		return getValue(col, (char)('a' + row), wellFunctionName);
	}
	
	public double getRawValue(int col, char row){
		
		return rawValues.get("" + row + col);
	}
	
	/**
	 * @param plateFunctionName
	 * @return
	 */
	public double getValue(String plateFunctionName) {
		
		return calculator.safeValueOf( plateFunctionEvaluators.get(plateFunctionName).expression );
	}

	public boolean exists() {
	    // TODO Auto-generated method stub
	    return false;
    }

	public ImageDescriptor getImageDescriptor() {
	    return ImageDescriptor.getMissingImageDescriptor();
    }

	public String getName() {
		return plate.getName();
    }

	public IPersistableElement getPersistable() {
	    // TODO Auto-generated method stub
	    return null;
    }

	public String getToolTipText() {
	    return plate.getName();
    }

	public Object getAdapter(Class adapter) {
	    // TODO Auto-generated method stub
	    return null;
    }

	public Plate getPlate() {
    	return plate;
    }

	public void setOutlier(String name, boolean outlier) {
		if(outlier) {
			calculator.addSuppressedVariable(name);
		}
		else {
			calculator.removeSuppressedVariable(name);
		}
    }
}
