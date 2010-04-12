package net.bioclipse.brunn.business.domain;

public class WellFunction {

	private String name;
	private String expression;
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	private double value;
	
	public WellFunction() {
		name = "";
		value = 0;
	}
	
	public WellFunction(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public WellFunction(String name, double value, String expression) {
		this.name = name;
		this.value = value;
		this.expression = expression;
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
