package net.bioclipse.brunn.ui.editors.compoundEditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.ui.Activator;

import org.eclipse.core.runtime.IProgressMonitor;
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
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.compoundEditor.CompoundEditor"; 
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		monitor.beginTask("save compound", 2);
		
		DrugOrigin drugOrigin = (DrugOrigin)( (net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound)
		                       this.getEditorInput() ).getPOJO();
		drugOrigin.setName(name.getText());
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
		
		monitor.done();
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		
		drugOrigin = (DrugOrigin) ((net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound) input).getPOJO(); 
		setPartName(drugOrigin.getName());
	}

	@Override
	public boolean isDirty() {
		if( !("".equals( structure.getText() )) )
			return true;
		if(!name.getText().equals(drugOrigin.getName()))
			return true;
		if(!molecularWeight.getText().equals(drugOrigin.getMolecularWeight()+""))
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

		final Label label = new Label(parent, SWT.NONE);
		label.setText("Molecular weight");
		label.setBounds(52, 132, 106, 20);

		final Button button = new Button(parent, SWT.NONE);
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
