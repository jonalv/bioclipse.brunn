package net.bioclipse.brunn.ui.editors.plateLayoutEditor.model;

import java.util.ArrayList;
import java.util.List;

public class MarkersTableCell {

	private List<String>   markers;
	private List<Function> functions;
	
	public MarkersTableCell() {
		markers   = new ArrayList<String>();
		functions = new ArrayList<Function>();
	}
	
	public String toString(){
		
		StringBuilder result = new StringBuilder();
		
		for(String lm : markers){
			if(result.length() != 0) {
				result.append(',');
				result.append(' ');
			}
			result.append(lm);
		}
		
		return result.toString();
	}
	
	public void addMarker(String name) {
		markers.add(name);
	}
	
	public int numberOfFunctions() {
		return functions.size();
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public List<String> getMarkers() {
		return markers;
	}

	public void setMarkers(List<String> markers) {
		this.markers = markers;
	}
}
