package net.bioclipse.brunn.ui.editors.cellTypeEditor;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.editors.plateEditor.PlateEditorContentProvider;
import net.bioclipse.brunn.ui.editors.plateEditor.PlateEditorNumberLabelProvider;
import net.bioclipse.editors.TextEditor;
import net.bioclipse.model.BioResourceChangeListener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import net.bioclipse.brunn.ui.explorer.model.nonFolders.CellType;
import net.bioclipse.brunn.ui.dialogs.ConsistencyFailure;

public class CellTypeEditor extends EditorPart {
	
	private Text name;
	
	private CellOrigin cellOrigin;
	private CellType cellType;
	private IOriginManager orm;
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.cellTypeEditor.CellTypeEditor"; 
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		monitor.beginTask("save cell type", 2);
		
		CellOrigin currentCellOrigin = orm.getCellOrigin(cellOrigin.getId());
		monitor.worked(1);
		if(!currentCellOrigin.equals(cellOrigin)) {
		
			final ConsistencyFailure dialog = new ConsistencyFailure( PlatformUI.
                                                                      getWorkbench().
                                                                      getActiveWorkbenchWindow().
                                                                      getShell() );
			dialog.open();
			
			if(dialog.getReturnCode() == ConsistencyFailure.OVERRIDE) {
			
				performEdit(currentCellOrigin);
			}
			else {
				//TODO FIXME
				//DO THE RELOADING of the platetype
			}
		}
		else {
			performEdit(currentCellOrigin);
		}
		monitor.worked(1);
		monitor.done();
	}

	private void performEdit(CellOrigin currentCellOrigin) {
		
		currentCellOrigin.setName(name.getText());
		
		orm.edit(Activator.getDefault().getCurrentUser(), currentCellOrigin);
		
		cellOrigin = currentCellOrigin;
		
		firePropertyChange(PROP_DIRTY);	
		cellType.getParent().fireUpdate();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		orm = (IOriginManager) Springcontact.getBean("originManager");
		cellType = (CellType)input;
		cellOrigin = (CellOrigin) ((CellType) input).getPOJO(); 
		setPartName(cellOrigin.getName());
	}

	@Override
	public boolean isDirty() {
		if(!name.getText().equals(cellOrigin.getName()))
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
		
		name.setText(cellOrigin.getName());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
