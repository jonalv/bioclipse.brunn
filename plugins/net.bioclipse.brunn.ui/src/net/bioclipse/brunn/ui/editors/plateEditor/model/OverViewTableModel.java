package net.bioclipse.brunn.ui.editors.plateEditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.Point;

import net.bioclipse.brunn.pojos.AbstractSample;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.results.PlateResults;
import net.bioclipse.brunn.ui.editors.plateEditor.Summary;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound;
import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;
import de.kupzog.ktable.renderers.FixedCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;

public class OverViewTableModel extends KTableDefaultModel {

	/*
     * a representation of the matrix
     */
    private String[][] matrix;
    private int rows;
    private int cols;
    private TextCellRenderer   renderer      = new TextCellRenderer(  TextCellRenderer.INDICATION_FOCUS_ROW );
    private KTableCellRenderer fixedRenderer = new FixedCellRenderer( FixedCellRenderer.STYLE_PUSH       |
                                                                      FixedCellRenderer.INDICATION_FOCUS );
    private net.bioclipse.brunn.pojos.Plate plate; 
    private KTable table;
    private Summary editor;
    private ArrayList<String> columnNames;
    
	public OverViewTableModel( net.bioclipse.brunn.pojos.Plate plate,
			                   KTable table,
			                   Summary editor,
			                   PlateResults plateResults ) {
		
		columnNames = new ArrayList<String>();
		Collections.addAll( columnNames, new String[] {"Cell Type", "Compound Names", "Concentration"} );
		columnNames.addAll( plate.getWellFunctionNames() ); 
		
		/*
		 * set up the matrix from the plate 
		 */
		rows = plate.getWells().size();
		cols = columnNames.size();
		Well[] wells = plate.getWells().toArray(new Well[0]);
		matrix = new String [ cols ] [ rows ];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				
				//Cell Type
				if(col == 0) {
					matrix[col][row] = getCellType(wells[row]);
					continue;
				}
				
				DrugSample[] drugSamples = getDrugSamples(wells[row]);
				
				//Compound Names
				if(col == 1) {
					matrix[col][row] = getCompoundNames(drugSamples);
					continue;
				}
				
				//Concentration
				if(col == 2) {
					matrix[col][row] = getConcentrations(drugSamples);
					continue;
				}
				
				//Wellfunctions
				for( String wellFunction : columnNames.subList(3, columnNames.size()) ) {
					try {
						Well well = wells[row];
						matrix[col][row] = plateResults.getValue( well.getCol(), well.getRow(), wellFunction) + "";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					col++;
				}
				break;
			}
		}

		this.plate  = plate;
		this.table  = table;
		this.editor = editor;
		initialize();
	}

	private String getConcentrations(DrugSample[] drugSamples) {

		String result = "";
		for( DrugSample drugSample : drugSamples ) {
			result += drugSample.getConcentration() + " ";
		}
		return result;
	}

	private DrugSample[] getDrugSamples(Well well) {
		ArrayList<DrugSample> result = new ArrayList<DrugSample>();
		for( AbstractSample s : well.getSampleContainer().getSamples() ) {
			if(s instanceof DrugSample) {
				result.add(( (DrugSample)s ));
			}
		}
		return result.toArray( new DrugSample[0] );
	}

	private String getCellType(Well well) {
		
		for( AbstractSample s : well.getSampleContainer().getSamples() ) {
			if(s instanceof CellSample) {
				return ( (CellSample)s ).getCellOrigin().getName();
			}
		}
		return "";
	}

	private String getCompoundNames(DrugSample[] drugSamples) {
		
		String result = "";
		for( DrugSample drugSample : drugSamples ) {
			result += drugSample.getName() + " ";
		}
		return result;
	}

	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
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
		if( col >= 0 && row >= 1 ) {
			return matrix[col][row-1];
		}
		if( row == 0 ) {
			return columnNames.get(col);
		}
		throw new IllegalArgumentException(col +" or " + row + " not a legal argument");
	}
	
	@Override
	public int doGetRowCount() {
		return rows+1;
	}

	@Override
	public void doSetContentAt(int col, int row, Object value) {
	
	}

	@Override
	public int getInitialColumnWidth(int column) {
		return 150;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 20;
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
        return null;
    }	
}
