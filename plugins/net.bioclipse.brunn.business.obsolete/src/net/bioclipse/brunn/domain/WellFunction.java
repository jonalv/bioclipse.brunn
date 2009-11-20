package net.bioclipse.brunn.domain;

public class WellFunction {

	private String name;
	private double value;
	
	public WellFunction() {
		name = "";
		value = 0;
	}
	
	public WellFunction(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public double getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public void print() {
		System.out.println("\t\tWellFunction: "+name+", value: "+value);
	}

	public String toString() {
		return "\t\tWellFunction: "+name+", value: "+value;
	}

}
