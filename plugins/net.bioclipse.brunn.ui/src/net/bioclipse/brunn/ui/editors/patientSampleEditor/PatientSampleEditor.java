package net.bioclipse.brunn.ui.editors.patientSampleEditor;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.business.sample.ISampleManager;
import net.bioclipse.brunn.pojos.PatientSample;
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

public class PatientSampleEditor extends EditorPart {

	private Text lid;
	
	private PatientSample patientSample; 
	private ISampleManager sm;
	//TODO: FIXME
	private net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType guiPlateType;
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.plateTypeEditor.PlateTypeEditor"; 
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		monitor.beginTask("save plateType", 2);
		
		PlateType currentPlateType = sm.getPlateType(patientSample.getId());
		monitor.worked(1);
		if(!currentPlateType.equals(patientSample)) {
		
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
		
		currentPlateType.setRows(Integer.parseInt(lid.getText()));
		currentPlateType.setCols(Integer.parseInt(cols.getText()));
		
		sm.edit(Activator.getDefault().getCurrentUser(), currentPlateType);
		
		patientSample = currentPlateType;
		
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
		patientSample = (PlateType) ((net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType) input).getPOJO();
		setPartName(patientSample.getName());
		guiPlateType = (net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType)input;
		sm = (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager");
		guiPlateType.getParent().fireUpdate();
	}

	@Override
	public boolean isDirty() {
		if(!lid.getText().equals(patientSample.getRows()+""))
			return true;
		if(!cols.getText().equals(patientSample.getCols()+""))
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

		lid = new Text(parent, SWT.BORDER);
		lid.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});
		lid.setBounds(222, 119, 80, 25);
		
		lid.setText(patientSample.getRows() + "");
		cols.setText(patientSample.getCols() + "");

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
