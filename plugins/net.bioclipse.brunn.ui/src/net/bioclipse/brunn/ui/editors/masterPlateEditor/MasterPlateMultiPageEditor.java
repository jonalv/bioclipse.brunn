package net.bioclipse.brunn.ui.editors.masterPlateEditor;

import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.jasper.editor.ReportEditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class MasterPlateMultiPageEditor extends MultiPageEditorPart {

	private MasterPlateEditor masterPlateEditor;
	private ReportEditor masterPlateReport;
	private MasterPlate toBeSaved;
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.masterPlateEditor.MasterPlateMultiPageEditor"; 
	
	@Override
	protected void createPages() {
		
		net.bioclipse.brunn.ui.explorer.model.nonFolders.MasterPlate masterPlate =
			(net.bioclipse.brunn.ui.explorer.model.nonFolders.MasterPlate)getEditorInput();
		this.setPartName(masterPlate.getName()); 
		
		toBeSaved = ( (MasterPlate)masterPlate.getPOJO() ).deepCopy(); 
		
		masterPlateEditor = new MasterPlateEditor(toBeSaved);
		
		try {
			int index = this.addPage((IEditorPart) masterPlateEditor, getEditorInput());
			setPageText(index, "Overview");
			this.setActivePage(index);
		} 
		catch (PartInitException e) {
			e.printStackTrace();
		}

		masterPlateReport = new ReportEditor();

		try {
			int index = this.addPage((IEditorPart) masterPlateReport, getEditorInput());
			setPageText(index, "Report");
		} 
		catch (PartInitException e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		masterPlateEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void pageChange(int newPageIndex){
		if(newPageIndex == 1) {
		    //TODO: Do something here to update the report?
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String[][] getSubstanceNames() {
		return masterPlateEditor.getSubstanceNames();
	}
	
	public String[][] getMasterPlateLayout() {
		return masterPlateEditor.getMasterPlateLayout();
	}
}
