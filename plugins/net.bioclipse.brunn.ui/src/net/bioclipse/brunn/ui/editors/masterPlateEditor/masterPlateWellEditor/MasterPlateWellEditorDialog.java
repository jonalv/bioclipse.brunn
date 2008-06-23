package net.bioclipse.brunn.ui.editors.masterPlateEditor.masterPlateWellEditor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.MasterPlateEditor;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.model.MarkersModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MasterPlateWellEditorDialog extends Dialog {

	private Well well;
	private MasterPlateEditor masterPlateEditor;
	
	public static final String[] COLUMN_NAMES = new String[] {"Marker", "Concentration"};
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public MasterPlateWellEditorDialog(Shell parentShell, Well well, MasterPlateEditor masterPlateEditor) {
		super(parentShell);
		this.well = well;
		this.masterPlateEditor = masterPlateEditor; 
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout());

		Table table = new Table(container, SWT.SINGLE         | 
				                           SWT.BORDER         | 
				                           SWT.H_SCROLL       | 
				                           SWT.V_SCROLL       | 
				                           SWT.FULL_SELECTION | 
				                           SWT.HIDE_SELECTION );
		TableColumn column1 = new TableColumn(table, SWT.CENTER, 0);
		column1.setText("Marker");
		column1.setWidth(100);
		TableColumn column2 = new TableColumn(table, SWT.LEFT, 1);
		column2.setText("Concentration");
		column2.setWidth(50);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		final TableViewer tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(COLUMN_NAMES);
		
		CellEditor[] editors = new CellEditor[COLUMN_NAMES.length];
		editors[1] = new TextCellEditor(table);
		tableViewer.setCellEditors(editors);
		tableViewer.setContentProvider( new TableContentProvider() );
		tableViewer.setLabelProvider(   new TableLabelProvider()   );
		tableViewer.setCellModifier(    new CellModifier()         );
		tableViewer.setInput(well);
		
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(519, 392);
	}
	
	class TableContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof Well) {
				List<SampleMarker> markers = new LinkedList<SampleMarker>(((Well)inputElement).getSampleMarkers());
				Comparator<SampleMarker> c = new Comparator<SampleMarker>() {
					public int compare(SampleMarker o1, SampleMarker o2) {
						return o1.getName().compareTo( o2.getName() ); 
					}
				};
				Collections.sort(markers, c);
				return markers.toArray();
			}
			throw new IllegalArgumentException("unknown inputElement");
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class TableLabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			SampleMarker sm = (SampleMarker)element;
			switch (columnIndex) {
			case 0:
				return sm.getName();
			case 1:
				if(sm.getSample() instanceof DrugSample) {
					return ( (DrugSample)sm.getSample() ).getConcentration() + "";
				}
				return "";
			default:
				throw new IllegalArgumentException("unknown columnindex");
			}
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return true;
		}

		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class CellModifier implements ICellModifier {

		public boolean canModify(Object element, String property) {
			
			int columnIndex = Arrays.asList(COLUMN_NAMES).indexOf(property);
			if( columnIndex == 1 ) {
				return true;
			}
			return false;
		}

		public Object getValue(Object element, String property) {
			
			int columnIndex = Arrays.asList(COLUMN_NAMES).indexOf(property);
			SampleMarker marker = (SampleMarker)element;
			String result = "";
			switch (columnIndex) {
			case 0:
				result = marker.getName();
				break;
			case 1:
				if(marker.getSample() instanceof DrugSample) {
					result = ( (DrugSample)marker.getSample() ).getConcentration() + "";
				}
				break;
			default:
				break;
			}
			return result;
		}

		public void modify(Object element, String property, Object value) {
			
			int columnIndex = Arrays.asList(COLUMN_NAMES).indexOf(property);
			SampleMarker marker = (SampleMarker)( (TableItem)element).getData();
			
			switch (columnIndex) {
			case 0:
				break;
	
			case 1:
				if(marker.getSample() instanceof DrugSample) {
					( (DrugSample)marker.getSample() ).setConcentration( Double.parseDouble( (String)value ) );
					masterPlateEditor.refresh();
				}
				break;
			default:
				break;
			}
		}
	}
}
