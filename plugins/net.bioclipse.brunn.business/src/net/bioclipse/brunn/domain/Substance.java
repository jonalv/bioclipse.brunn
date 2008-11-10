package net.bioclipse.brunn.domain;

public class Substance {

	private String name;
	private double concentration;
	
	public Substance() {
		name = "";
		concentration = 0;
	}
	
	public Substance(String name, double conc) {
		this.name = name;
		concentration = conc;
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
		System.out.println("\t\tSubstance: "+name+" concentration "+concentration);
	}
	
	public String toString() {
		return "\t\tSubstance: "+name+" concentration "+concentration;
	}

}
