package net.bioclipse.brunn.business.domain;

public class Substance {

	private String name;
	private double concentration;
	private String unit;
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Substance() {
		name = "";
		concentration = 0;
	}
	
	public Substance(String name, double conc, String unit) {
		this.name = name;
		concentration = conc;
		this.unit = unit;
	}
	
	public String getName() {
		return name;
	}
	
	public double getConcentration() {
		return concentration;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setConcentration(double concentration) {
		this.concentration = concentration;
	}
	
	public void print() {
		System.out.println("\t\tSubstance: "+name+", concentration "+concentration);
	}
	
	public String toString() {
		return "\t\tSubstance: "+name+", concentration "+concentration;
	}

}
