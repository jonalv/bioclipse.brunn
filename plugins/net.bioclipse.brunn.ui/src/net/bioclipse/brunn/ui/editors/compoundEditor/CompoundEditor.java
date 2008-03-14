package net.bioclipse.brunn.ui.editors.compoundEditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class CompoundEditor extends EditorPart {

//	private Table table_1;
	
	private Text molecularWeight;
	private Text structure;
	private Text name;
	
	private DrugOrigin drugOrigin;
	private Compound compound; 
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.compoundEditor.CompoundEditor"; 
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		monitor.beginTask("save compound", 3);
		
//		DrugOrigin drugOrigin = (DrugOrigin)( (net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound)
//		                       this.getEditorInput() ).getPOJO();
		drugOrigin.setName(name.getText());
		try {
			drugOrigin.setMolecularWeight( Double
					                       .parseDouble( 
					                    		   molecularWeight.getText()) );
		}
		catch (NumberFormatException e) {
			MessageDialog.openInformation( PlatformUI
					                       .getWorkbench()
					                       .getActiveWorkbenchWindow()
					                       .getShell(), 
					                       "Could not save compound",
					                       "molecular weight must be a " +
					                       "decimal number (e.g. 12.0 or 12)" );
		}
		if(!"".equals(structure.getText())) {
			try {
				drugOrigin.setStructureInputStream(new FileInputStream(structure.getText()));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		IOriginManager om = (IOriginManager) Springcontact.getBean("originManager");
		monitor.worked(1);
		
		om.edit(Activator.getDefault().getCurrentUser(), drugOrigin);
		structure.setText("");
		monitor.worked(2);
		
		firePropertyChange(PROP_DIRTY);
		compound.getParent().fireUpdate(new SubProgressMonitor(monitor,1));
		monitor.done();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		compound = (net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound) input;
		drugOrigin = (DrugOrigin) compound.getPOJO(); 
		setPartName(drugOrigin.getName());
		compound.getParent().fireUpdate();
	}

	@Override
	public boolean isDirty() {
//		if( !("".equals( structure.getText() )) )
//			return true;
		if(!name.getText().equals(drugOrigin.getName()))
			return true;
		try {
			Double.parseDouble( molecularWeight.getText() );
		}
		catch (NumberFormatException e) {
			return true;
		}
		
		return Double.parseDouble( molecularWeight.getText() ) 
			!= drugOrigin.getMolecularWeight();
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
		numberOfColumnsLabel.setBounds(118, 48, 40, 20);
		numberOfColumnsLabel.setText("Name");

		name = new Text(parent, SWT.BORDER);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});
		name.setBounds(164, 43, 119, 25);

		final Label numberOfRowsLabel = new Label(parent, SWT.NONE);
		numberOfRowsLabel.setText("Structure");
		numberOfRowsLabel.setBounds(98, 90, 60, 20);

		structure = new Text(parent, SWT.BORDER);
		structure.setEnabled(false);
		structure.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});
		structure.setBounds(164, 85, 119, 25);
		
		name.setText(drugOrigin.getName());
		
		molecularWeight = new Text(parent, SWT.BORDER);
		molecularWeight.setBounds(164, 127, 119, 25);
		
		molecularWeight.setText(drugOrigin.getMolecularWeight()+"");

		molecularWeight.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				firePropertyChange(PROP_DIRTY);
			}
		});
		
		final Label label = new Label(parent, SWT.NONE);
		label.setText("Molecular weight");
		label.setBounds(52, 132, 106, 20);

		final Button button = new Button(parent, SWT.NONE);
		button.setEnabled(false);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = 
					new FileDialog( PlatformUI.getWorkbench().getDisplay().getActiveShell() );
				String s = fd.open();
				if(s != null){
					structure.setText(s);
				}
			}
		});
		button.setText("Browse");
		button.setBounds(287, 83, 60, 30);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
