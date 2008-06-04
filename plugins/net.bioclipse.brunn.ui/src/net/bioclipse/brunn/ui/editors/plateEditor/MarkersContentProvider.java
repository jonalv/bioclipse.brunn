package net.bioclipse.brunn.ui.editors.plateEditor;

import java.util.HashMap;

import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.ui.editors.plateEditor.model.MarkersTableRow;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class MarkersContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		
		Plate plate = (Plate)inputElement;
		HashMap<String, DrugOrigin> markers = new HashMap<String, DrugOrigin>();
		for( Well well : plate.getWells() ) {
			if( well.getSampleMarkers().size() > 0 ) {
				for( SampleMarker marker : well.getSampleMarkers() ) {
					DrugOrigin origin = marker.getSample() == null ? null : ( (DrugSample)marker.getSample() ).getDrugOrigin();
					if(marker.getName().matches("M\\d+")) {
						markers.put( marker.getName(),  origin);
					}
				}	
			}
		}
		MarkersTableRow[] rows = new MarkersTableRow[markers.size()];
		int i = 0;
		for ( String marker : markers.keySet() ) {
			rows[i++] = new MarkersTableRow( marker, markers.get(marker) == null ? "" : markers.get(marker).getName() );
		}
		return rows;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
}
