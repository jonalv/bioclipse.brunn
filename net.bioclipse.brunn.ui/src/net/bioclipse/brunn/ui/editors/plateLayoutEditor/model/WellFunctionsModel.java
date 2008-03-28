package net.bioclipse.brunn.ui.editors.plateLayoutEditor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.WellFunction;
import net.bioclipse.brunn.ui.editors.plateLayoutEditor.PlateLayoutEditor;
import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.CalculatorException;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;
import de.kupzog.ktable.editors.KTableCellEditorText;
import de.kupzog.ktable.editors.KTableCellEditorText2;
import de.kupzog.ktable.renderers.FixedCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;

public class WellFunctionsModel extends KTableDefaultModel {

    /*
     * a representation of the matrix consisting of a double array with a List of layoutmarker names 
     */
    private String[][] matrix;
    private int rows;
    private int cols;
    private List<LayoutWell> layoutWells;
    private List<WellFunction> commonFunctions;
    private boolean multipleWellsCelected;
    
    private TextCellRenderer   renderer      = new TextCellRenderer(  TextCellRenderer.INDICATION_FOCUS_ROW);
    private KTableCellRenderer fixedRenderer = new FixedCellRenderer( FixedCellRenderer.STYLE_PUSH);
    
    private Hashtable<String, WellFunction> wellFunctions = new Hashtable<String, WellFunction>();
    private PlateLayoutEditor editor;
	private Calculator calculator;
    
	public WellFunctionsModel( List<LayoutWell> layoutWells, 
			                   PlateLayoutEditor editor, 
			                   Calculator calculator) {

		cols             = 2;
		this.calculator  = calculator;
		this.layoutWells = layoutWells;
		this.editor      = editor;
		extractCommonFunctions(layoutWells);
		rows             = commonFunctions.size();		
		setupMatrix(commonFunctions);
		
		initialize();
	}
	
	private void extractCommonFunctions(
			List<LayoutWell> layoutWells) {

		Map<String, WellFunction> commonWellFunctions = new HashMap<String, WellFunction>();
		for ( WellFunction wf : layoutWells.get(0).getWellFunctions() ) {
			commonWellFunctions.put(wf.getName(), wf);
		}
		
		for ( LayoutWell lw : layoutWells ) {
			Map<String, WellFunction> newCommonWellFunctions = new HashMap<String, WellFunction>();
			
			for ( WellFunction lwf : lw.getWellFunctions() ) 
				if ( commonWellFunctions.keySet().contains(lwf.getName() ) )
						newCommonWellFunctions.put(lwf.getName(), lwf);
			
			commonWellFunctions = newCommonWellFunctions;
		}
		
		multipleWellsCelected = layoutWells.size() != 1;
		commonFunctions =  new ArrayList<WellFunction>(commonWellFunctions.values());
	}

	public WellFunctionsModel(String[][] matrix, PlateLayoutEditor editor) {
			
		cols        = matrix.length;
		rows        = matrix[0].length;
		this.matrix = matrix;
		this.editor = editor;
		
		initialize();
	}

	private void setupMatrix(List<WellFunction> commonWellFunctions) {

		String[][] matrix = new String[cols][rows];
		
		for(int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = "";
			}
		}
		
		int i = 0;
		for(WellFunction function : commonWellFunctions) {
			
			wellFunctions.put(i + "", function);
			matrix[0][i]   = function.getName();
			if(!multipleWellsCelected) {
				matrix[1][i] = function.getExpression();
			}
			i++;
		}
		
		this.matrix = matrix;
	}

	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		
		if (isFixedCell(col, row)) 
            return null; // no editor
		if ( matrix[0][row-1].equals("raw") ) 
			return null;
		if ( col == 1 && multipleWellsCelected ) {
			return null;
		}
        return new KTableCellEditorText2() {
        	@Override
        	public void close(boolean save) {

        		if(save) {
        			try {
        				calculator.valueOf( m_Text.getText() );
        				super.close(true);
        			}
        			catch(CalculatorException e) {
        				MessageDialog.openInformation( 
        						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
        						"Cold not parse function", 
        						e.getMessage() );
        				super.close(false);
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
				return "well function name";
			}
			if ( col == 1 ) {
				return "expression";
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
		
		switch (col) {
		case 0:
			wellFunctions.get( (row-1) + "" ).setName( (String)value );
			break;
		
		case 1:
			wellFunctions.get( (row-1) + "" ).setExpression( (String)value );
			break;
			
		default:
			break;
		}
		
		setupMatrix(commonFunctions);
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

	public Hashtable<String, WellFunction> getWellFunctions() {
		return wellFunctions;
	}
}
