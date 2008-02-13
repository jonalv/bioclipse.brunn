package net.bioclipse.brunn.genericDAO;

import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.pojos.Plate;

/**
 * Definition of the PlateDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IPlateDAO extends IGenericDAO<Plate> {
    public List<Plate> findAll();
	public List<String> findAllPlateBarcodes();
	public List<Plate> findByBarcode(String barcode);
	public List<Plate> findAllNotDeleted();
}