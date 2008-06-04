package net.bioclipse.brunn.results;

public interface PlateRead {

	public double[][] getValues();
	public String getBarCode();
	public void setBarCode(String barcode);
	public String getError();
	public void setError(String error);
	public void setValues(double[][] values);
	public String getName();
}
