package net.bioclipse.brunn.ui.editors.plateEditor.model;

public class MarkersTableRow {

	private String marker;
	private String compoundName;
	
	public MarkersTableRow(String marker, String compoundName) {
		super();
		this.marker = marker;
		this.compoundName = compoundName;
	}
	
	public String getCompoundName() {
		return compoundName;
	}
	
	public void setCompoundName(String compoundName) {
		this.compoundName = compoundName;
	}
	
	public String getMarker() {
		return marker;
	}
	
	public void setMarker(String marker) {
		this.marker = marker;
	}
}
