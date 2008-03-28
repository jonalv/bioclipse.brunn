package net.bioclipse.brunn.ui.explorer.model;

public class TreeEvent {
	protected Object actedUpon;
	
	public TreeEvent(Object actedUpon) {
		this.actedUpon = actedUpon;
	}
	
	public Object receiver() {
		return actedUpon;
	}
}
