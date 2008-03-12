package net.bioclipse.brunn.ui.editors.masterPlateEditor.model;

import org.eclipse.swt.graphics.Point;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;
import de.kupzog.ktable.renderers.FixedCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;
import net.bioclipse.brunn.pojos.AbstractSample;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.MasterPlateEditor;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.masterPlateWellEditor.MasterPlateWellEditorDialog;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.model.MarkersTableCell;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.masterPlateWellEditor.MasterPlateWellEditor;

public class MarkersModel extends KTableDefaultModel {
	
	private MarkersTableCell[][] matrix;
	private int rows;
    private int cols;
    private TextCellRenderer   renderer      = new TextCellRenderer(  TextCellRenderer.INDICATION_FOCUS_ROW );
    private KTableCellRenderer fixedRenderer = new FixedCellRenderer( FixedCellRenderer.STYLE_PUSH       |
                                                                      FixedCellRenderer.INDICATION_FOCUS );
    private net.bioclipse.brunn.pojos.MasterPlate masterPlate; 
    private KTable                              markerstable;
    private MasterPlateEditor                   masterPlateEditor;
    
	public MarkersModel(MasterPlate masterPlate, KTable markersTable, MasterPlateEditor masterPlateEditor) {
		
		this.masterPlate       = masterPlate;
		this.markerstable      = markersTable;
		this.masterPlateEditor = masterPlateEditor;
		
		refresh();
	}

	public void refresh() {
		
		/*
		 * set up the matrix from the platelayout 
		 */
		rows = masterPlate.getRows();
		cols = masterPlate.getCols();
		matrix = new MarkersTableCell [ cols ] [ rows ];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				matrix[i][j] = new MarkersTableCell();
			}
		}
		for(Well well : masterPlate.getWells() ) {
			for(SampleMarker sm : well.getSampleMarkers()){
				matrix[ well.getCol()-1 ][ well.getRow()-'a' ].addMarker( sm.getName() ); 
			}
			for(AbstractSample as : well.getSampleContainer().getSamples()) {
				if(as instanceof DrugSample) {
					DrugSample ds = (DrugSample)as;
					if(ds.getSampleMarker() != null) {
						matrix[ well.getCol()-1 ][ well.getRow()-'a' ].setConcentration(
								ds.getSampleMarker().getName(), 
								ds.getConcentration(), 
								ds.getConcUnit() );
					}
				}
			}
		}
		initialize();
	}
	
	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		if( !isFixedCell(col, row) ) {
			return new MasterPlateWellEditor(masterPlate.getWell( new Point(col, row) ), masterPlateEditor);
		}
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
		return cols+1;
	}

	@Override
	public Object doGetContentAt(int col, int row) {
		if( col >= 1 && row >= 1 ) {
			return matrix[col-1][row-1];	
		}
		if( col == 0 ) {
			if ( row == 0) {
				return "";
			}
			StringBuilder label = new StringBuilder();
			do {
				label.append( (char)( (row>26 ? row % 26 : row) + 'a' -1 ) );
		   		row = (int)(row/26);
			}
			while(col > 26);
				
			return label.reverse().toString();
		}
		if( row == 0 ) {
			return col;
		}
		throw new IllegalArgumentException(col +" or " + row + " not a legal argument");
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
		if(column==0) return 40;
		return 150;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 50;
	}

	public int getFixedHeaderColumnCount() {
		return 1;
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
	
	public MarkersTableCell[][] getMatrix() {
		return matrix;
	}
}
