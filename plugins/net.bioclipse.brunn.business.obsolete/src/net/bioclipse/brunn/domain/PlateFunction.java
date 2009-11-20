package net.bioclipse.brunn.domain;

public class PlateFunction {
	
	private String name;
	private double value;
	
	public PlateFunction(String name, double value) {
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
		System.out.println("\tPlateFunction: "+name+", value: "+value);
	}
	
	public String toString() {
		return "\tPlateFunction: "+name+", value: "+value;
	}

}
