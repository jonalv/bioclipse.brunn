package net.bioclipse.brunn.ui.editors.masterPlateEditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.ui.editors.masterPlateEditor.MasterPlateEditor;

public class MarkersTableCell {

	List<String> markers;
	HashMap<String, Double> concentrations;
	HashMap<String, ConcUnit> concUnits;
	
	public MarkersTableCell() {
		super();
		this.markers        = new ArrayList<String>();
		this.concentrations = new HashMap<String, Double>();
		this.concUnits      = new HashMap<String, ConcUnit>();
	}

	public Double getConcentration(String marker) {
		return concentrations.get(marker);
	}

	public void setConcentration(String marker, Double concentration, ConcUnit concUnit) {
		this.concentrations.put(marker, concentration);
		this.concUnits.put(marker, concUnit);
	}

	public List<String> getMarkers() {
		return markers;
	}

	public void setMarkers(List<String> markers) {
		this.markers = markers;
		Collections.sort(markers);
	}

	public void addMarker(String name) {
		markers.add(name);
	}
	
	public String toString() {
		
		StringBuilder markersString = new StringBuilder();
		Collections.sort(markers);
		for (Iterator iterator = markers.iterator(); iterator.hasNext();) {
			String lm = (String) iterator.next();
			markersString.append(lm);
			
			Double concentration = concentrations.get(lm);
			if( concentration != null ) {
				if( concentration == MasterPlateEditor.UNDEFINED_CONCENTRATION ) {
					markersString.append(' ');
					markersString.append("[undefined]");
				}
				else {
					markersString.append(": ");
					if(concentration < 1) {
						markersString.append( round( concentration, 2) );
					}
					else {
						markersString.append( String.format("%.2f", concentration) );
					}
					
					markersString.append(" ");
					markersString.append(concUnits.get(lm));
				}
			}
			if(iterator.hasNext()) {
				markersString.append('\n');
			}
		}
		return markersString.toString();
	}
	
	public static double round( double value, int significantDigits ) {
		int decimals = significantDigits - (int)Math.log10(value);
		double factor = Math.pow(10, decimals);
	    return (int)Math.floor((value * factor) + 0.5) / factor;
	}
}