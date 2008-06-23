package net.bioclipse.brunn.ui.editors.plateEditor;

import net.bioclipse.brunn.ui.editors.plateEditor.model.MarkersTableRow;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class MarkersLabelProvider extends LabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		
		MarkersTableRow row = (MarkersTableRow)element;
		switch (columnIndex) {
		case 0:
			return row.getMarker();
		case 1:
			return row.getCompoundName();
		case 2:
			return "";
		default:
			throw new IllegalArgumentException("Unknown columnindex: " + columnIndex);
		}
	}
}
