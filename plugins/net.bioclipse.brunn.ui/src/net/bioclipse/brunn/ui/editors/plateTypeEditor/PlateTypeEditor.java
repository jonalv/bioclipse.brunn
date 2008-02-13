package net.bioclipse.brunn.ui.editors.plateTypeEditor;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.dialogs.ConsistencyFailure;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class PlateTypeEditor extends EditorPart {

//	private Table table_1;
	
	private Text rows;
	private Text cols;
	
	private PlateType plateType; 
	private IPlateLayoutManager pm;
	private net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType guiPlateType;
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.plateTypeEditor.PlateTypeEditor"; 
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		monitor.beginTask("save plateType", 2);
		
		PlateType currentPlateType = pm.getPlateType(plateType.getId());
		monitor.worked(1);
		if(!currentPlateType.equals(plateType)) {
		
			final ConsistencyFailure dialog = new ConsistencyFailure( PlatformUI.
                                                                      getWorkbench().
                                                                      getActiveWorkbenchWindow().
                                                                      getShell() );
			dialog.open();
			
			if(dialog.getReturnCode() == ConsistencyFailure.OVERRIDE) {
			
				performEdit(currentPlateType);
			}
			else{
				//TODO FIXME
				//DO THE RELOADING of the platetype
			}
		}
		else {
			performEdit(currentPlateType);
		}
		monitor.worked(1);
		monitor.done();
	}

	private void performEdit(PlateType currentPlateType) {
		
		currentPlateType.setRows(Integer.parseInt(rows.getText()));
		currentPlateType.setCols(Integer.parseInt(cols.getText()));
		
		pm.edit(Activator.getDefault().getCurrentUser(), currentPlateType);
		
		plateType = currentPlateType;
		
		firePropertyChange(PROP_DIRTY);	
		guiPlateType.getParent().fireUpdate();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		plateType = (PlateType) ((net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType) input).getPOJO();
		setPartName(plateType.getName());
		guiPlateType = (net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType)input;
		pm = (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager");
	}

	@Override
	public boolean isDirty() {
		if(!rows.getText().equals(plateType.getRows()+""))
			return true;
		if(!cols.getText().equals(plateType.getCols()+""))
			return true;
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		parent.setLayout(null);
		final Label numberOfColumnsLabel = new Label(parent, SWT.NONE);
		numberOfColumnsLabel.setBounds(86, 80, 130, 20);
		numberOfColumnsLabel.setText("Number of columns");

		cols = new Text(parent, SWT.BORDER);
		cols.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});
		cols.setBounds(222, 75, 80, 25);

		final Label numberOfRowsLabel = new Label(parent, SWT.NONE);
		numberOfRowsLabel.setText("Number of rows");
		numberOfRowsLabel.setBounds(111, 124, 105, 20);

		rows = new Text(parent, SWT.BORDER);
		rows.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});
		rows.setBounds(222, 119, 80, 25);
		
		rows.setText(plateType.getRows() + "");
		cols.setText(plateType.getCols() + "");

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
