package net.bioclipse.brunn.ui.editors.plateEditor;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.results.PlateResults;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class PlateMultiPageEditor extends MultiPageEditorPart {

	private PlateEditor plateEditor;
	private Summary summary;

	public final static String ID = "net.bioclipse.brunn.ui.editors.plateEditor.PlateMultiPageEditor"; 
	
	@Override
	protected void createPages() {
		
		net.bioclipse.brunn.ui.explorer.model.nonFolders.Plate plate =
			(net.bioclipse.brunn.ui.explorer.model.nonFolders.Plate)getEditorInput();
		PlateResults plateResults = plate.getPlateResults();
		this.setPartName(plate.getName()); 
		try {
			plateEditor = new PlateEditor(plateResults);
			int index = this.addPage((IEditorPart) plateEditor, getEditorInput());
			setPageText(index, "Overview");
			this.setActivePage(index);
		} 
		catch (PartInitException e) {
			e.printStackTrace();
		}

		try {
			summary = new Summary(plateResults);
			int index = this.addPage((IEditorPart) summary, getEditorInput());
			setPageText(index, "Summary");
		} 
		catch (PartInitException e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		plateEditor.doSave(monitor);
		summary.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
}
