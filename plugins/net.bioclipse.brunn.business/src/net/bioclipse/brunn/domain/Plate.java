package net.bioclipse.brunn.domain;

import java.util.HashSet;
import java.util.Set;

public class Plate {
	
	private String name;
	private String barcode;
	private Set<PlateFunction> plateFunctions;
	private Set<Well> wells;

	public Plate() {
		name = "";
		barcode = "";
		plateFunctions = new HashSet<PlateFunction>();
		wells = new HashSet<Well>();
	}
	
	public Plate(String name, String barcode) {
		this.name = name;
		this.barcode = barcode;
		plateFunctions = new HashSet<PlateFunction>();
		wells = new HashSet<Well>();
	}
	
	public Plate(String name, String barcode, Set<PlateFunction> plateFunctions, Set<Well> wells) {
		this.name = name;
		this.barcode = barcode;
		this.plateFunctions = plateFunctions;
		this.wells = wells;
	}
	
	public void addFunction(PlateFunction plateFunction) {
		plateFunctions.add(plateFunction);
	}
	
	public void addWells(Well well) {
		wells.add(well);
	}
	
	public String getName() {
		return name;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public Set<PlateFunction> getPlateFunctions() {
		return plateFunctions;
	}
	
	public Set<Well> getWells() {
		return wells;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public void setPlateFunctions(Set<PlateFunction> plateFunctions) {
		this.plateFunctions = plateFunctions;
	}
	
	public void setWells(Set<Well> wells) {
		this.wells = wells;
	}
	
	public void print() {
		System.out.println("\nPlate: "+name+" barcode: "+barcode);
		for(PlateFunction pf : plateFunctions) {
			pf.print();
		}
		for(Well w : wells) {
			w.print();
		}
	}
	
	public String toString() {
		String result = "";
		result+="\nPlate: "+name+" barcode: "+barcode+"\n";
		for(PlateFunction pf : plateFunctions) {
			result+=pf.toString()+"\n";
		}
		for(Well w : wells) {
			result+=w.toString();
		}
		return result;
	}

}
