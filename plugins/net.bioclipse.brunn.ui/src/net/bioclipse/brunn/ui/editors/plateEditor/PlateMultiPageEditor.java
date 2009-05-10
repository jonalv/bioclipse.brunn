package net.bioclipse.brunn.ui.editors.plateEditor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.results.PlateResults;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class PlateMultiPageEditor extends MultiPageEditorPart {

	private PlateEditor plateEditor;
	private Summary summary;
	private Replicates replicates;
	private IC50 ic50;
	private PlateReport plateReport;
	private List<OutlierChangedListener> outLierListeners = new ArrayList<OutlierChangedListener>();
	private Plate toBeSaved;
	
	public final static String ID = "net.bioclipse.brunn.ui.editors.plateEditor.PlateMultiPageEditor"; 
	
	public void fireOutliersChanged() {
		for( OutlierChangedListener l : outLierListeners ) {
			l.onOutLierChange();
		}
	}
	
	public void addListener(OutlierChangedListener listener) {
		outLierListeners.add(listener);
	}
	
	public void removeListener( OutlierChangedListener listener ) {
		outLierListeners.remove(listener);
	}
	
	@Override
	protected void createPages() {
		
		net.bioclipse.brunn.ui.explorer.model.nonFolders.Plate plate =
			(net.bioclipse.brunn.ui.explorer.model.nonFolders.Plate)getEditorInput();
		PlateResults plateResults = plate.getPlateResults();
		this.setPartName(plate.getName()); 
		
		toBeSaved = ( (Plate)plate.getPOJO() ).deepCopy(); 
		
		plateEditor = new PlateEditor( plateResults, this, toBeSaved             );
		replicates  = new Replicates(  plateResults, this, toBeSaved             );
		summary     = new Summary(     plateResults, this, toBeSaved, replicates );
		ic50        = new IC50(        this, replicates );
		plateReport = new PlateReport( replicates );
		
		try {
			int index = this.addPage((IEditorPart) plateEditor, getEditorInput());
			setPageText(index, "Overview");
			this.setActivePage(index);
		} 
		catch (PartInitException e) {
			e.printStackTrace();
		}

		try {
			int index = this.addPage((IEditorPart) summary, getEditorInput());
			setPageText(index, "Summary");
		} 
		catch (PartInitException e) {
			e.printStackTrace();			
		}

		try {
			int index = this.addPage((IEditorPart) replicates, getEditorInput());
			setPageText(index, "Average");
		} 
		catch (PartInitException e) {
			e.printStackTrace();			
		}

		try {
			int index = this.addPage((IEditorPart) ic50, getEditorInput());
			setPageText(index, "IC50");
		} 
		catch (PartInitException e) {
			e.printStackTrace();			
		}
		
		try {
			int index = this.addPage((IEditorPart) plateReport, getEditorInput());
			setPageText(index, "Report");
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
	protected void pageChange(int newPageIndex){
		if(newPageIndex == 4) {
			plateReport.onPageChange();
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
}
