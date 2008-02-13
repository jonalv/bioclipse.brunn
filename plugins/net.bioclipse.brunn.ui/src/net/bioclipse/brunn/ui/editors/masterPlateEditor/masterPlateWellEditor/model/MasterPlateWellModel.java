package net.bioclipse.brunn.ui.editors.masterPlateEditor.masterPlateWellEditor.model;

import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;
import de.kupzog.ktable.renderers.FixedCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;

public class MasterPlateWellModel extends KTableDefaultModel {

	private String[][] matrix;
	private Well well;
	private int rows;
    private int cols;
    private TextCellRenderer   renderer      = new TextCellRenderer(  TextCellRenderer.INDICATION_FOCUS_ROW );
    private KTableCellRenderer fixedRenderer = new FixedCellRenderer( FixedCellRenderer.STYLE_PUSH       |
                                                                      FixedCellRenderer.INDICATION_FOCUS );
	
	public MasterPlateWellModel( Well well ) {
		
		this.well = well;
		
		/*
		 * set up the matrix from the well 
		 */
		rows = well.getSampleMarkers() == null ? 0
				                               : well.getSampleMarkers().size();
		cols = 2;
		matrix = new String [ cols ] [ rows ];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				matrix[i][j] = "";
			}
		}
		int i = 0;
		for( SampleMarker sm : well.getSampleMarkers() ) {
			matrix[0][i] = sm.getName();
			if(sm.getSample() instanceof DrugSample) {
				matrix[1][i] = ( (DrugSample)sm.getSample() ).getConcentration() + "";
			}
			i++;
		}
		initialize();
	}
	
	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		// TODO Auto-generated method stub
		return null;
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
		if( col == 0 )
			return "Marker";
		if( col == 1 )
			return "Concentration";
		throw new IllegalArgumentException("No well, col:" + col + " row:" + row);
	}

	@Override
	public int doGetRowCount() {
		return rows+1;
	}

	@Override
	public void doSetContentAt(int col, int row, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInitialColumnWidth(int column) {
		return 60;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 30;
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
		return 0;
	}

	public boolean isColumnResizable(int col) {
		return true;
	}

	public boolean isRowResizable(int row) {
		return true;
	}
}
