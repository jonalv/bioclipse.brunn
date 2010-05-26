package net.bioclipse.brunn.ui.editors.plateLayoutEditor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.ui.editors.plateLayoutEditor.PlateLayoutEditor;
import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.CalculatorException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;
import de.kupzog.ktable.editors.KTableCellEditorText2;
import de.kupzog.ktable.renderers.FixedCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;

public class PlateFunctionsModel extends KTableDefaultModel {

    /*
     * a representation of the matrix consisting of a double array with a List of layoutmarker names 
     */
    private String[][] matrix;
    private int rows;
    private int cols;
    
    private TextCellRenderer   renderer      = new TextCellRenderer(  TextCellRenderer.INDICATION_FOCUS_ROW);
    private KTableCellRenderer fixedRenderer = new FixedCellRenderer( FixedCellRenderer.STYLE_PUSH);
    
    /**
     * key is String rownumber of table
     */
    private Hashtable<String, PlateFunction> plateFunctions = new Hashtable<String, PlateFunction>();
    private PlateLayoutEditor editor;
	private PlateLayout plateLayout;
	private Calculator calculator;
    
	public PlateFunctionsModel(PlateLayout plateLayout, PlateLayoutEditor editor, Calculator calculator) {
		
		cols             = 4;
		rows             = plateLayout.getPlateFunctions().size();
		this.editor      = editor;
		this.plateLayout = plateLayout;
		setupMatrix(plateLayout);
		this.calculator = calculator;
		
		initialize();
	}

	private void setupMatrix(PlateLayout plateLayout) {

		String[][] matrix = new String[cols][rows];
		
		for(int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = "";
			}
		}

		int i = 0;
		List<PlateFunction> plateLayoutPlateFunctions 
			= new ArrayList<PlateFunction>( plateLayout.getPlateFunctions() );
		Comparator<PlateFunction> c = new Comparator<PlateFunction>() {
			public int compare(PlateFunction o1, PlateFunction o2) {
				return o1.getName().toLowerCase().compareTo( 
							o2.getName().toLowerCase() );
			}
		};
		Collections.sort(plateLayoutPlateFunctions, c);
		for(PlateFunction function : plateLayoutPlateFunctions) {
			
			plateFunctions.put(i + "", function);
			matrix[0][i] = function.getName();
			matrix[1][i] = function.getExpression();
			matrix[2][i] = function.getHasSpecifiedFromValue() ? function.getGoodFrom() + "" : "";
			matrix[3][i] = function.getHasSpecifiedToValue() ? function.getGoodTo()   + "" : "";
			i++;
		}
		
		this.matrix = matrix;
	}
	
	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		
		if (isFixedCell(col, row)) 
            return null; // no editor
        return new KTableCellEditorText2() {
        	@Override
        	public void close(boolean save) {

        		if(save) {
        			try {
        				calculator.safeValueOf( m_Text.getText() );
        				super.close(true);
        			}
        			catch(CalculatorException e) {
        				super.close(false);
        				MessageDialog.openInformation( 
        						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
        						"Cold not parse function",
        						e.getMessage() );
        			}
        		}
        		else {
        			super.close(save);
        		}
        	}
        };
	}
	
	@Override
	public KTableCellRenderer doGetCellRenderer(int col, int row) {

		if (isFixedCell(col, row))
            return fixedRenderer;
        else
            return renderer;
	}

	@Override
	public int doGetColumnCount() {
		return cols;
	}

	@Override
	public Object doGetContentAt(int col, int row) {
		if( row >= 1 ) {
			return matrix[col][row-1];
		}
		if( row == 0 ) {
			if ( col == 0) {
				return "plate function name";
			}
			if ( col == 1 ) {
				return "expression";
			}
			if ( col == 2 ) {
				return "good from";
			}
			if ( col == 3) {
				return "good to";
			}
		}
		throw new IllegalArgumentException(col +" or " + row + " not a legal argument");
	}
	
	@Override
	public int doGetRowCount() {
		return rows+1;
	}

	@Override
	public void doSetContentAt(int col, int row, Object value) {

		PlateFunction plateFunction = plateFunctions.get( (row-1) + "" );
		switch (col) {
		case 0:
			plateFunction.setName( (String)value );
			break;
		
		case 1:
			plateFunction.setExpression( (String)value );
			break;
		
		case 2:
			try {
				if(!"".equals(value))
					plateFunction.setGoodFrom( Double.parseDouble( (String)value ) );
			}
			catch(NumberFormatException e) {
				MessageDialog.openInformation( 
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					    "Info",
					    "Good from must be a double" );
				return;
			}
			break;
		
		case 3:
			try {
				if(!"".equals(value))
					plateFunction.setGoodTo( Double.parseDouble( (String)value ) );
			}
			catch(NumberFormatException e) {
				MessageDialog.openInformation( 
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					    "Info",
					    "Good to must be a double" );
				return;
			}
			break;
		
		default:
			break;
		}
		
		setupMatrix(plateLayout);
		editor.dirtyCheck();
	}

	@Override
	public int getInitialColumnWidth(int column) {
		return 150;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 40;
	}

	public int getFixedHeaderColumnCount() {
		return 0;
	}

	public int getFixedHeaderRowCount() {
		return 1;
	}

	public int getFixedSelectableColumnCount() {
		return 0;
	}

	public int getFixedSelectableRowCount() {
		return 0;
	}

	public int getRowHeightMinimum() {
		return 30;
	}

	public boolean isColumnResizable(int col) {
		return true;
	}

	public boolean isRowResizable(int row) {
		return true;
	}
	
	public String doGetTooltipAt(int col, int row) {
        return "";
    }

	public Hashtable<String, PlateFunction> getPlateFunctions() {
		return plateFunctions;
	}

	public void setPlateFunctions(Hashtable<String, PlateFunction> plateFunctions) {
		this.plateFunctions = plateFunctions;
	}
}
