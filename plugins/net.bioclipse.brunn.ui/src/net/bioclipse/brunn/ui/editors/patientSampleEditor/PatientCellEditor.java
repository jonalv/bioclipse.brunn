package net.bioclipse.brunn.ui.editors.patientSampleEditor;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.dialogs.ConsistencyFailure;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class PatientCellEditor extends EditorPart {

	private Text name;
	private Text lid;
	
	private PatientOrigin patientOrigin; 
	private IOriginManager om;
	private net.bioclipse.brunn
	           .ui.explorer.model.nonFolders.PatientSample patientsample;
	
	public final static String ID 
		= "net.bioclipse.brunn.ui.editors.plateTypeEditor.PlateTypeEditor"; 
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		monitor.beginTask("Saving patient sample", 2);
		
		PatientOrigin currentPatientOrigin 
			= om.getPatientOrigin( patientOrigin.getId() );
		monitor.worked(1);
		if( !currentPatientOrigin.equals(patientOrigin) ) {
		
			final ConsistencyFailure dialog 
				= new ConsistencyFailure( PlatformUI
						                  .getWorkbench()
						                  .getActiveWorkbenchWindow()
						                  .getShell() );
			dialog.open();
			
			if(dialog.getReturnCode() == ConsistencyFailure.OVERRIDE) {
			
				performEdit(currentPatientOrigin);
			}
			else{
				//TODO FIXME
				//DO THE RELOADING 
			}
		}
		else {
			performEdit(currentPatientOrigin);
		}
		monitor.worked(1);
		monitor.done();
	}

	private void performEdit(PatientOrigin currentPatientOrigin) {
		
		currentPatientOrigin.setName( name.getText() );
		currentPatientOrigin.setName( lid.getText()  );
		
		om.edit( Activator.getDefault().getCurrentUser(), 
				 currentPatientOrigin );
		
		patientOrigin = currentPatientOrigin;
		
		firePropertyChange(PROP_DIRTY);	
		patientsample.getParent().fireUpdate();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		patientOrigin = (PatientOrigin) 
		                ( (net.bioclipse.brunn
		                      .ui.explorer.model.nonFolders.PlateType) input )
		                .getPOJO();
		setPartName( patientOrigin.getName() );
		patientsample = (net.bioclipse.brunn
				            .ui.explorer.model.nonFolders.PatientSample) input;
		om = (IOriginManager) Springcontact.getBean("originManager");
		patientsample.getParent().fireUpdate();
	}

	@Override
	public boolean isDirty() {
		if( !lid.getText().equals( patientOrigin.getLid() ) )
			return true;
		if( !name.getText().equals(patientOrigin.getName() ) )
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
		parent.setLayout(new FormLayout());
		final Label numberOfColumnsLabel = new Label(parent, SWT.NONE);
		final FormData fd_numberOfColumnsLabel = new FormData();
		fd_numberOfColumnsLabel.top = new FormAttachment(0, 80);
		fd_numberOfColumnsLabel.right = new FormAttachment(0, 129);
		fd_numberOfColumnsLabel.left = new FormAttachment(0, 86);
		numberOfColumnsLabel.setLayoutData(fd_numberOfColumnsLabel);
		numberOfColumnsLabel.setText("Name:");

		final Label numberOfRowsLabel = new Label(parent, SWT.NONE);
		final FormData fd_numberOfRowsLabel = new FormData();
		fd_numberOfRowsLabel.top = new FormAttachment(0, 124);
		fd_numberOfRowsLabel.right = new FormAttachment(0, 129);
		fd_numberOfRowsLabel.left = new FormAttachment(0, 111);
		numberOfRowsLabel.setLayoutData(fd_numberOfRowsLabel);
		numberOfRowsLabel.setText("lid:");

		lid = new Text(parent, SWT.BORDER);
		fd_numberOfRowsLabel.bottom = new FormAttachment(lid, -2, SWT.BOTTOM);
		final FormData fd_lid = new FormData();
		fd_lid.right = new FormAttachment(100, -156);
		fd_lid.top = new FormAttachment(0, 119);
		fd_lid.left = new FormAttachment(0, 135);
		lid.setLayoutData(fd_lid);
		lid.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});

		name = new Text(parent, SWT.BORDER);
		fd_numberOfColumnsLabel.bottom = new FormAttachment(name, -2, SWT.BOTTOM);
		final FormData fd_name = new FormData();
		fd_name.right = new FormAttachment(100, -156);
		fd_name.top = new FormAttachment(0, 75);
		fd_name.left = new FormAttachment(0, 135);
		name.setLayoutData(fd_name);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}
}
