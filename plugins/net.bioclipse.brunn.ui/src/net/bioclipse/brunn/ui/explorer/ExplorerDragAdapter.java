package net.bioclipse.brunn.ui.explorer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.transferTypes.BrunnTransfer;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

public class ExplorerDragAdapter extends DragSourceAdapter {

	private TreeViewer treeViewer;
	private Set<ITreeObject> toBeRefreshed = new HashSet<ITreeObject>(); 

	ExplorerDragAdapter(TreeViewer treeViewer) {
		super();
		this.treeViewer = treeViewer;
	}
			
	public void dragFinished(DragSourceEvent event) {
		
		for(ITreeObject t : toBeRefreshed) {
			t.fireUpdate();
		}
		toBeRefreshed.clear();
	}

	@SuppressWarnings("unchecked")
	public void dragSetData(DragSourceEvent event) {
		
		IStructuredSelection selection 
			= (IStructuredSelection)treeViewer.getSelection();
		ITreeObject[] treeObjects 
			= (ITreeObject[])selection
			              .toList()
			              .toArray( new ITreeObject[selection.size()] );
		List<ILISObject> domainObjects = new LinkedList<ILISObject>();
		for(ITreeObject o : treeObjects) {
			domainObjects.add( (ILISObject)o.getPOJO() );
		}
		if (BrunnTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = domainObjects.toArray();
		} 
	}

	public void dragStart(DragSourceEvent event) {
		IStructuredSelection selection 
			= (IStructuredSelection)treeViewer.getSelection();
		ITreeObject[] treeObjects 
			= (ITreeObject[])selection
	                     .toList()
	                     .toArray( new ITreeObject[selection.size()] );
		for(ITreeObject t : treeObjects) {
			toBeRefreshed.add(t.getParent());
		}
	}
}
