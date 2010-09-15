package net.bioclipse.brunn.ui.editors.plateEditor;


import java.util.List;

import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.results.PlateResults;
import net.bioclipse.brunn.ui.editors.plateEditor.model.SummaryTableModel;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellSelectionAdapter;
import de.kupzog.ktable.KTableCellSelectionListener;
import de.kupzog.ktable.SWTX;

public class Summary extends EditorPart implements OutlierChangedListener {

	private KTable table;
	private PlateResults plateResults;
	private Replicates replicates; 
	private boolean outlierSelected;
	private PlateMultiPageEditor plateMultiPageEditor;
	
	private final Clipboard cb = new Clipboard(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay() );
	private Plate toBeSaved;
    private List<IPlateExportAction> plateExportActions;
	private Button sortByCvButton;
	
		
	public Summary( PlateResults plateResults, 
			        PlateMultiPageEditor plateMultiPageEditor, 
			        Plate toBeSaved, Replicates replicates, 
			        List<IPlateExportAction> plateExportActions ) {
		super();
		this.plateResults = plateResults;
		plateMultiPageEditor.addListener(this);
		this.plateMultiPageEditor = plateMultiPageEditor;
		this.replicates = replicates;
		this.toBeSaved = toBeSaved;
		this.plateExportActions = plateExportActions;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new FillLayout());

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());

		table = new KTable( composite, SWTX.MARK_FOCUS_HEADERS |
                                       SWTX.AUTO_SCROLL        |
                                       SWT.MULTI               |
                                       SWT.FULL_SELECTION );
		final FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, -5);
		formData.top = new FormAttachment(0, 0);
		table.setLayoutData(formData);
		
		

		Button copyTableToButton;
		copyTableToButton = new Button(composite, SWT.NONE);
		copyTableToButton.addSelectionListener(new SelectionAdapter() {
			/*
			 * COPY TABLE BUTTON
			 */
			public void widgetSelected(SelectionEvent e) {
				
				SummaryTableModel model = (SummaryTableModel)table.getModel();
				StringBuilder stringBuilder = new StringBuilder();
				for( int row = 0 ; row < model.getRowCount() ; row++) {
					for( int col = 0 ; col < model.getColumnCount() ; col++) {
						stringBuilder.append( model.doGetContentAt(col, row) ); 
						stringBuilder.append('\t');
					}
					stringBuilder.append('\n');
				}
				
				TextTransfer textTransfer = TextTransfer.getInstance();
				Transfer[] types = new Transfer[] {textTransfer};
				cb.setContents( new Object[]{ stringBuilder.toString() }, types );
			}
		});
		formData.bottom = new FormAttachment(copyTableToButton, -5, SWT.TOP);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(100, -8);
		formData_1.left = new FormAttachment(0, 5);
		copyTableToButton.setLayoutData(formData_1);
		copyTableToButton.setText("Copy Table to Clipboard");

		final Button markAsOutlierButton = new Button(composite, SWT.NONE);
		markAsOutlierButton.addSelectionListener(new SelectionAdapter() {

			/*
             * TOGGLE CELL MARKED AS OUTLIER
			 */
			public void widgetSelected(final SelectionEvent e) {
				for( int selectedRow : table.getRowSelection() ) {
					
					Well well = ( (SummaryTableModel)table.getModel() )
                                .getWellFromSelectedRowNumber(selectedRow);

					well.setOutlier(!outlierSelected);
					plateResults.setOutlier( well.getName(), 
							                 !outlierSelected );
				}
				plateMultiPageEditor.fireOutliersChanged();
				firePropertyChange(PROP_DIRTY);
			}
		});
		final FormData fd_markAsOutlierButton = new FormData();
		fd_markAsOutlierButton.top = new FormAttachment(copyTableToButton, 0, SWT.TOP);
		fd_markAsOutlierButton.left = new FormAttachment(copyTableToButton, 5, SWT.RIGHT);
		markAsOutlierButton.setLayoutData(fd_markAsOutlierButton);
		markAsOutlierButton.setText("Mark as outlier");
		
		table.addCellSelectionListener( new KTableCellSelectionListener() {

			public void cellSelected(int col, int row, int statemask) {
				outlierSelected = true;
				markAsOutlierButton.setText("Unmark as outlier");
				for( int selectedRow : table.getRowSelection() ) {
					if ( ! ( (SummaryTableModel)table.getModel() )
							.getWellFromSelectedRowNumber(selectedRow).isOutlier() ) {
						markAsOutlierButton.setText("Mark as outlier");
						outlierSelected = false;
					}
				}
				
				
			}

			public void fixedCellSelected(int col, int row, int statemask) {
				// TODO Auto-generated method stub
				
			}
		});

		sortByCvButton = new Button(composite, SWT.NONE | 
		                                                    SWT.TOGGLE);
		
		
		sortByCvButton.addSelectionListener(new SelectionAdapter() {
		    public void widgetSelected(final SelectionEvent e) {
		    	updateModel();
		    }
		});
		final FormData fd_sortByCvButton = new FormData();
		fd_sortByCvButton.bottom = new FormAttachment(markAsOutlierButton, 0, SWT.BOTTOM);
		fd_sortByCvButton.right = new FormAttachment(table, 0, SWT.RIGHT);
		sortByCvButton.setLayoutData(fd_sortByCvButton);
		sortByCvButton.setText("Sort by CV%");
		
		updateModel();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
	public void dispose() {
		super.dispose();
		cb.dispose();
	}

	private void updateModel() {
		table.setModel( new SummaryTableModel( toBeSaved, 
                table, 
                this, 
                plateResults, 
                replicates.getCVMap(), sortByCvButton.getSelection() ) );
	}
	
	public void onOutLierChange() {
		updateModel();
	}

	public KTable getTable() {
		return table;
	}
}
