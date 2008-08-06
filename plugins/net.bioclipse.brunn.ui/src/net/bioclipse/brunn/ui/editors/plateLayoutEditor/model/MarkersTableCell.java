package net.bioclipse.brunn.ui.editors.plateLayoutEditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sun.org.apache.xml.internal.utils.StringComparable;

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
		Collections.sort(markers, new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				if ( arg0.charAt(0) == 'M' &&
					 arg0.charAt(0) == 'M'	) {
					return Integer.parseInt( arg0.substring(1) ) - 
					       Integer.parseInt( arg1.substring(1) );
				}
				return arg0.compareTo(arg1);
			}
		} );
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
