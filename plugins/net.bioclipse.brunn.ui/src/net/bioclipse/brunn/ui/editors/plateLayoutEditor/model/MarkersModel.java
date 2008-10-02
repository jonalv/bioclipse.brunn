package net.bioclipse.brunn.ui.editors.plateLayoutEditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.graphics.Point;

import net.bioclipse.brunn.pojos.LayoutMarker;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.editors.plateLayoutEditor.PlateLayoutEditor.MarkerComparator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateLayout;
import net.bioclipse.brunn.ui.editors.plateLayoutEditor.CellEditorActionsCombo;
import net.bioclipse.brunn.ui.editors.plateLayoutEditor.model.MarkersTableCell;
import net.bioclipse.brunn.ui.editors.plateLayoutEditor.PlateLayoutEditor;
import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableDefaultModel;
import de.kupzog.ktable.editors.KTableCellEditorCombo;
import de.kupzog.ktable.renderers.FixedCellRenderer;
import de.kupzog.ktable.renderers.TextCellRenderer;

public class MarkersModel extends KTableDefaultModel {

    /*
     * a representation of the matrix
     */
    private MarkersTableCell[][] matrix;
    private int rows;
    private int cols;
    private TextCellRenderer   renderer      = new TextCellRenderer(  TextCellRenderer.INDICATION_FOCUS_ROW );
    private KTableCellRenderer fixedRenderer = new FixedCellRenderer( FixedCellRenderer.STYLE_PUSH       |
                                                                      FixedCellRenderer.INDICATION_FOCUS );
    private net.bioclipse.brunn.pojos.PlateLayout plateLayout; 
    private KTable markerstable;
    private PlateLayoutEditor plateLayoutEditor;
    
	public MarkersModel( net.bioclipse.brunn.pojos.PlateLayout plateLayout,
			             KTable markerstable,
			             PlateLayoutEditor plateLayoutEditor ) {
				
		/*
		 * set up the matrix from the platelayout 
		 */
		rows = plateLayout.getRows();
		cols = plateLayout.getCols();
		matrix = new MarkersTableCell [ cols ] [ rows ];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				matrix[i][j] = new MarkersTableCell();
			}
		}
		for(LayoutWell well : plateLayout.getLayoutWells() ) {
			for(LayoutMarker lm : well.getLayoutMarkers()){
				matrix[ well.getCol()-1 ][ well.getRow()-'a' ].addMarker(lm.getName()); 
			}
		}
		this.plateLayout = plateLayout;
		this.markerstable = markerstable;
		this.plateLayoutEditor = plateLayoutEditor;
		initialize();
	}

	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		if (isFixedCell(col, row)) {
            return null; // no editor
		}
		else {
			CellEditorActionsCombo editor = new CellEditorActionsCombo();
			editor.setItems(createMarkerEditactions(col, row, plateLayoutEditor, markerstable));
			return editor;
		}
	}

	@Override
	public KTableCellRenderer doGetCellRenderer(int col, int row) {
//		return KTableCellRenderer.defaultRenderer;
//		return renderer;
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
	
	}

	@Override
	public int getInitialColumnWidth(int column) {
		return 60;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 40;
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
		return 30;
	}

	public boolean isColumnResizable(int col) {
		return true;
	}

	public boolean isRowResizable(int row) {
		return true;
	}
	
	public String doGetTooltipAt(int col, int row) {
        return " c: control\n"          +
        		" p: positive control\n" +
        		" b: blank\n"            +
        		" s: solvent\n"          +
        		"Mx: drug marker number x";
    }

	public MarkersTableCell[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Create actions for well editor
	 * @param col
	 * @param row
	 * @param editor can be null
	 * @param markersTable
	 * @return
	 */
	public Action[] createMarkerEditactions(int col, int row, final PlateLayoutEditor editor, final KTable markersTable) {

		LayoutWell selectedWell = plateLayout.getLayoutWell(new Point(col, row));
		
		/*
		 * Create Actions for removing markers of a well
		 */
		ArrayList<Action> actions = new ArrayList<Action>();
		ArrayList<String> markerNamesOnWell = new ArrayList<String>();
		for( final LayoutMarker lm : selectedWell.getLayoutMarkers() ) {
						
			markerNamesOnWell.add(lm.getName());
			actions.add( 
				new Action("Remove " + lm.getName()) {
					public void run() {
						
						lm.getLayoutWell().getLayoutMarkers().remove(lm);
						lm.delete();
								
						markersTable.setModel(new MarkersModel(plateLayout, markersTable, plateLayoutEditor));
						markersTable.redraw();
						if(editor != null) 
							editor.dirtyCheck();
					}
				} 
			);
		}

		/*
		 * Create a list of all potential markers to be added for this plate
		 */
		Set<String> toBeAdded  = new HashSet<String>();
		for(LayoutWell lw : plateLayout.getLayoutWells()) {
			for(LayoutMarker lm : lw.getLayoutMarkers()) {
				toBeAdded.add(lm.getName());
			}
		}

		/*
		 * add next unused M-marker and next unused C-marker to the set
		 */
		ArrayList<String> mNames = new ArrayList<String>(toBeAdded);
		ArrayList<String> cNames = new ArrayList<String>(toBeAdded);
		
		for ( Iterator<String> i = mNames.iterator() ; i.hasNext() ; ) {
		    if ( !i.next().contains( "M" ) ) {
		        i.remove();
		    }
		}
		for ( Iterator<String> i = cNames.iterator() ; i.hasNext() ; ) {
            if ( !i.next().contains( "C" ) ) {
                i.remove();
            }
        }
		
		Collections.sort(mNames, MarkerComparator.INSTANCE);
		Collections.sort(cNames, MarkerComparator.INSTANCE);
		
		if (mNames.size() > 0 && !"".equals(mNames.get(0))) {
			String last = mNames.get(mNames.size()-1);
			toBeAdded.add("M" + (1 + Integer.parseInt(last.substring(1))));
		}
		if (cNames.size() > 0 && !"".equals(cNames.get(0))) {
            String last = cNames.get(cNames.size()-1);
            toBeAdded.add("C" + (1 + Integer.parseInt(last.substring(1))));
        }
		
		/*
		 * Make sure there is no missing marker in the serie
		 */
		for ( char c : new char[] {'M', 'C'}) {
		    int lastElement=0;
		    for(String s : toBeAdded) {
		        if(s.charAt(0) == c) {
		            if( lastElement < 
		                    Integer.parseInt( s.substring(1)) ) {
		                lastElement = Integer.parseInt(s.substring(1) );
		            }
		        }
		    }
		    for( int i = 1 ; i <= lastElement ; i++) {
		        toBeAdded.add( (c + "") + i);
		    }
		}

		
		/*
		 * make sure P, B, S and at least M1 and C1 is on the list
		 */
		toBeAdded.add("P");
		toBeAdded.add("B");
		toBeAdded.add("C1");
		toBeAdded.add("M1");
		toBeAdded.add("S");
		/*
		 * remove markers already on the this well from the list of 
		 * possible markers to add
		 */
		toBeAdded.removeAll(markerNamesOnWell);
		
		/*
		 * Create actions for adding markers
		 */
		ArrayList<String> toBeAddedList = new ArrayList<String>(toBeAdded);
		Collections.sort(toBeAddedList, MarkerComparator.INSTANCE);
		final Point point = new Point(col, row);
		for(final String name : toBeAddedList) {
			actions.add(
					new Action("Add " + name) {
						public void run() {
							plateLayout.getLayoutWell( 
									point )
									.getLayoutMarkers().add(
									new LayoutMarker( Activator.getDefault().getCurrentUser(), 
											          name,
											          plateLayout.getLayoutWell(
											        		  point)));
							markersTable.setModel(new MarkersModel(plateLayout, markersTable, plateLayoutEditor));
							markersTable.redraw();
							if(editor != null)
								editor.dirtyCheck();
						}
					} 
			);
		}
		
		return actions.toArray(new Action[0]);
	}

	public void setMarkerstable(KTable markerstable) {
		this.markerstable = markerstable;
	}

	public void setPlateLayoutEditor(PlateLayoutEditor plateLayoutEditor) {
		this.plateLayoutEditor = plateLayoutEditor;
	}
}
