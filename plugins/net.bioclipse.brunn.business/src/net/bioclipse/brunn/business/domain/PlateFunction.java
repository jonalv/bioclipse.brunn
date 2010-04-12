package net.bioclipse.brunn.business.domain;

public class PlateFunction {
	
	private String name;
	private String expression;
	private double goodFrom, goodTo;
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	private double value;
	
	public PlateFunction(String name, double value, String expression, double goodFrom,  double goodTo) {
		this.name = name;
		this.value = value;
		this.expression = expression;
		this.goodFrom = goodFrom;
		this.goodTo = goodTo;
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

	public void setGoodFrom(Double goodFrom) {
		this.goodFrom = goodFrom;
	}

	public Double getGoodFrom() {
		return goodFrom;
	}

	public void setGoodTo(Double goodTo) {
		this.goodTo = goodTo;
	}

	public Double getGoodTo() {
		return goodTo;
	}

}
