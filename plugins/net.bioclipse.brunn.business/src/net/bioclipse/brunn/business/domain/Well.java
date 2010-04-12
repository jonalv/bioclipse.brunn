package net.bioclipse.brunn.business.domain;

import java.util.HashSet;
import java.util.Set;


public class Well {
	
	private String name;
	private String cellType;
	private String cellName;
	private Set<WellFunction> wellFunctions;
	private Set<Substance> substances;
	private boolean outlier;

	public Well() {
		name = "";
		cellType = "";
		cellName = "";
		wellFunctions = new HashSet<WellFunction>();
		substances = new HashSet<Substance>();
	}
	
	public Well(String name, String cellType, String cellName) {
		this.name = name;
		this.cellType = cellType;
		this.cellName = cellName;
		wellFunctions = new HashSet<WellFunction>();
		substances = new HashSet<Substance>();
	}
	
	public Well(String name, String cellType, String cellName, Set<WellFunction> wellFunctions, Set<Substance> substances) {
		this.name = name;
		this.cellType = cellType;
		this.cellName = cellName;
		this.wellFunctions = wellFunctions;
		this.substances = substances;
	}
	
	
	public void addWellFunctions(Set<WellFunction> wellFunctions) {
		this.wellFunctions= wellFunctions;
	}
	
	public String getName() {
		return name;
	}

	public String getCellType() {
		return cellType;
	}
	
	public String getCellName() {
		return cellName;
	}
	
	public Set<WellFunction> getWellFunctions() {
		return wellFunctions;
	}
	
	public Set<Substance> getSubstances() {
		return substances;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCellType(String cellType) {
		this.cellType = cellType;
	}
	
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	
	public void setWellFunctions(Set<WellFunction> wellFunctions) {
		this.wellFunctions = wellFunctions;
	}
	
	public void setSubstances(Set<Substance> substances) {
		this.substances = substances;
	}

	public void print() {
		System.out.println("\tWell: "+name+", celltype: "+cellType+", cellname: "+cellName);
		for(WellFunction wf : wellFunctions) {
			wf.print();
		}
		for(Substance s : substances) {
			s.print();
		}
	}

	public String toString() {
		String result = "";
		result+="\tWell: "+name+", celltype: "+cellType+", cellname: "+cellName+"\n";
		for(WellFunction wf : wellFunctions) {
			result+=wf.toString()+"\n";
		}
		for(Substance s : substances) {
			result+=s.toString()+"\n";
		}
		return result;
	}

	public void addSubstance(Substance substance) {
		// TODO Auto-generated method stub
		substances.add(substance);
		
	}

	public void addWellFunction(WellFunction wellFunction) {
		// TODO Auto-generated method stub
		wellFunctions.add(wellFunction);
	}

	public void setOutlier(boolean outlier) {
		this.outlier = outlier;
	}

	public boolean isOutlier() {
		// TODO Auto-generated method stub
		return this.outlier;
	}

}
