package net.bioclipse.brunn.ui.editors.plateLayoutEditor.model;

public class Function {

	String name;
	String expression;
	double goodFrom;
	double goodTo;
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public double getGoodFrom() {
		return goodFrom;
	}
	
	public void setGoodFrom(double goodFrom) {
		this.goodFrom = goodFrom;
	}
	
	public double getGoodTo() {
		return goodTo;
	}
	
	public void setGoodTo(double goodTo) {
		this.goodTo = goodTo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
